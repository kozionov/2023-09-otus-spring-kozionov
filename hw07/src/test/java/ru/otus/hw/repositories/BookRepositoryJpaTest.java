package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DisplayName("Репозиторий на основе Spring Data для работы с книгами ")
@DataJpaTest
@Import({BookRepository.class, GenreRepository.class, AuthorRepository.class})
class BookRepositoryJpaTest {

    @Autowired
    private BookRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;


}