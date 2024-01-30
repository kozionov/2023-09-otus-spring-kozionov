package ru.otus.hw.config.step;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.model.document.BookDoc;
import ru.otus.hw.model.document.GenreDoc;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BookMigrationStepConfig {

    private final DataSource dataSource;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public MongoItemReader<BookDoc> bookMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<BookDoc>()
                .name("mongoBookReader")
                .template(mongoTemplate)
                .targetType(BookDoc.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<BookDoc> bookInsertTempTable() {
        JdbcBatchItemWriter<BookDoc> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO temp_book_cross_ids(id_mongo, id_postgres) " +
                "VALUES (:id, nextval('books_id_seq'))");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public CompositeItemWriter<BookDoc> bookCompositeItemWriter(
            JdbcBatchItemWriter<BookDoc> bookInsertTempTable,
            ItemWriter<BookDoc> personWriter) {
        CompositeItemWriter<BookDoc> writer = new CompositeItemWriter<>();
        writer.setDelegates(List.of(bookInsertTempTable, personWriter));
        return writer;
    }

    @Bean
    public Step bookMigrationStep(MongoItemReader<BookDoc> bookMongoItemReader,
                                  CompositeItemWriter<BookDoc> bookCompositeItemWriter) {
        return new StepBuilder("bookMigrationStep", jobRepository)
                .<BookDoc, BookDoc>chunk(10, transactionManager)
                .reader(bookMongoItemReader)
//                .processor(new ItemProcessor<BookDoc, Book>() {
//                    @Override
//                    public Book process(BookDoc item) throws Exception {
//                        return new Book(item.getTitle(),item.getDescription(),
//                                new Author(Long.valueOf(item.getId()), item.getAuthor().getFirstName(), item.getAuthor().getLastName())
//                        , item.getGenres().stream().map(g->new Genre(Long.valueOf(g.getId()), g.getName())).collect(Collectors.toSet()));
//                    }
//                })
                .writer(bookCompositeItemWriter)
                .allowStartIfComplete(true)
                .build();
    }


    @Bean
    public PersonWriter personWriter() {
        return new PersonWriter(dataSource);
    }

    public class PersonWriter implements ItemWriter<BookDoc> {

        private JdbcTemplate jdbcTemplate;

        public PersonWriter(DataSource dataSource) {
            this.jdbcTemplate = new JdbcTemplate(dataSource);
        }

        @Override
        public void write(Chunk<? extends BookDoc> items) {
            for (BookDoc person : items) {
                jdbcTemplate.update("INSERT INTO books(title, description, id,  author_id) " +
                        "VALUES (?, ?, " +
                        "(SELECT id_postgres FROM temp_book_cross_ids WHERE id_mongo = ?), " +
                        "(SELECT id_postgres FROM temp_author_cross_ids WHERE id_mongo = ?))", person.getTitle(), person.getDescription(), person.getId(), person.getAuthor().getId());
                for (GenreDoc address : person.getGenres()) {
                    jdbcTemplate.update("INSERT INTO books_genres(book_id, genre_id) " +
                            "VALUES (" +
                            "(SELECT id_postgres FROM temp_book_cross_ids WHERE id_mongo = ?), " +
                            "(SELECT id_postgres FROM temp_genre_cross_ids WHERE id_mongo = ?)) ", person.getId(), address.getId());
                }
            }
        }

    }

}
