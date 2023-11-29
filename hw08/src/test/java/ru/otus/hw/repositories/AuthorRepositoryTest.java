package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.models.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorRepository should")
@DataMongoTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("correctly create and read author")
    @Test
    void shouldCreateReadAuthor() {
        Author newAuthor = authorRepository.save(new Author(null, "newAuthorForename"));

        Optional<Author> optAuthor = authorRepository.findById(newAuthor.getId());
        assertThat(optAuthor.isPresent()).isTrue();
        Author author = optAuthor.get();
        assertThat(author.getId()).isEqualTo(newAuthor.getId());
        assertThat(author.getFullName()).isEqualTo(newAuthor.getFullName());
        assertThat(author.getFullName()).isEqualTo(newAuthor.getFullName());
    }
}
