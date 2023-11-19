package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import({BookRepositoryJpa.class, GenreRepositoryJpa.class, AuthorRepositoryJpa.class})
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;


    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        Book expectedBook = em.find(Book.class, 1);
        em.detach(expectedBook);
        var actualBook = repositoryJpa.findById(1);
        assertThat(actualBook.get().getId()).isEqualTo(expectedBook.getId());
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = repositoryJpa.findAll();
        var expectedBooks = em.getEntityManager().createQuery("select b from Book b", Book.class).getResultList();

        assertThat(actualBooks).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBooks);
        actualBooks.forEach(System.out::println);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book(0, "BookTitle_10500", getDbAuthors().get(0), List.of(getDbGenres().get(0), getDbGenres().get(2)));
        var returnedBook = repositoryJpa.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(em.find(Book.class, returnedBook.getId())).isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book(1L, "BookTitle_10500", getDbAuthors().get(2), List.of(getDbGenres().get(4), getDbGenres().get(5)));

        assertThat(repositoryJpa.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var returnedBook = repositoryJpa.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(repositoryJpa.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        repositoryJpa.deleteById(1L);
        assertThat(repositoryJpa.findById(1L)).isEmpty();
    }

    private List<Author> getDbAuthors() {
        return em.getEntityManager().createQuery("select a from Author a", Author.class).getResultList();
    }

    private List<Genre> getDbGenres() {
        return em.getEntityManager().createQuery("select g from Genre g", Genre.class).getResultList();
    }

}