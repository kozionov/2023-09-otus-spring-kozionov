package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.models.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GenreRepository should")
@DataMongoTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("correctly create and read genre")
    @Test
    void shouldCreateReadGenre() {
        Genre newGenre = genreRepository.save(new Genre(null, "newGenreName"));

        Optional<Genre> optGenre = genreRepository.findById(newGenre.getId());
        assertThat(optGenre.isPresent()).isTrue();
        Genre genre = optGenre.get();
        assertThat(genre.getId()).isEqualTo(newGenre.getId());
        assertThat(genre.getName()).isEqualTo(newGenre.getName());
    }
}
