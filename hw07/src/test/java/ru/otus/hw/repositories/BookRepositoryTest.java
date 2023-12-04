package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data для работы с книгами ")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("создает и вычитывает книгу")
    @Test
    void shouldCreateReadBook() {
        Author author = em.find(Author.class, 1);
        Genre genre = em.find(Genre.class, 1);
        List<Genre> genres = new ArrayList<>();
        genres.add(genre);
        Book newBook = bookRepository.save(new Book(0, "newBookTitle", author, genres));

        em.detach(author);
        em.detach(genre);
        em.detach(newBook);

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

    @DisplayName("возвращает все книги")
    @Test
    void shouldGetBooks() {
        List<Book> book = bookRepository.findAll();
        assertThat(book).hasSize(3);
    }
}