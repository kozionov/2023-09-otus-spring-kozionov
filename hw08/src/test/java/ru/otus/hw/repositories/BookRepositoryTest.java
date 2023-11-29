package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository should")
@DataMongoTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("correctly create and read book")
    @Test
    void shouldCreateReadBook() {
        Author author = mongoTemplate.findById("1", Author.class);
        Genre genre = mongoTemplate.findById("1", Genre.class);
        List<Genre> genres = new ArrayList<>();
        genres.add(genre);
        Book newBook = bookRepository.save(new Book(null, "newBookTitle", author, genres));

        Optional<Book> optBook = bookRepository.findById(newBook.getId());
        assertThat(optBook.isPresent()).isTrue();
        Book book = optBook.get();
        //Deep comparison
        assertThat(book.getId()).isEqualTo(newBook.getId());
        assertThat(book.getTitle()).isEqualTo(newBook.getTitle());
        Author bookAuthor = book.getAuthor();
        assertThat(bookAuthor.getId()).isEqualTo(author.getId());
        assertThat(bookAuthor.getFullName()).isEqualTo(author.getFullName());
        Genre bookGenre = book.getGenres().get(0);
        assertThat(bookGenre.getId()).isEqualTo(genre.getId());
        assertThat(bookGenre.getName()).isEqualTo(genre.getName());
    }
}
