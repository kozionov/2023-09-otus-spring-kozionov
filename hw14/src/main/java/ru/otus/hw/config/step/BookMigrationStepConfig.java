package ru.otus.hw.config.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.model.document.BookDoc;
import ru.otus.hw.model.entity.Author;
import ru.otus.hw.model.entity.Book;
import ru.otus.hw.model.entity.Genre;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class BookMigrationStepConfig {

    private final DataSource dataSource;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

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
    public JdbcBatchItemWriter<BookDoc> bookJdbcBatchItemWriter() {
        JdbcBatchItemWriter<BookDoc> writer = new JdbcBatchItemWriter<>();
        writer.setItemPreparedStatementSetter((book, statement) -> {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getDescription());
            statement.setString(3, String.valueOf(book.getId()));
//            statement.setString(4, String.valueOf(book.getGenres().stream().findFirst().get().getId()));
            statement.setString(4, String.valueOf(book.getAuthor().getId()));
        });
        writer.setSql("INSERT INTO books(title, description, id, /*genre_id,*/ author_id) " +
                "VALUES (?, ?, " +
                "(SELECT id_postgres FROM temp_book_cross_ids WHERE id_mongo = ?), " +
//                "(SELECT id_postgres FROM temp_genre_cross_ids WHERE id_mongo = ?), " +
                "(SELECT id_postgres FROM temp_author_cross_ids WHERE id_mongo = ?))");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public CompositeItemWriter<BookDoc> bookCompositeItemWriter(
            JdbcBatchItemWriter<BookDoc> bookInsertTempTable,
            JdbcBatchItemWriter<BookDoc> bookJdbcBatchItemWriter) {
        CompositeItemWriter<BookDoc> writer = new CompositeItemWriter<>();
        writer.setDelegates(List.of(bookInsertTempTable, bookJdbcBatchItemWriter));
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

}
