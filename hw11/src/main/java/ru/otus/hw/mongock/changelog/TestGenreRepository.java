package ru.otus.hw.mongock.changelog;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Genre;

import java.util.List;

public interface TestGenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findAll();

    List<Genre> findAllByIdIn(List<String> genresIds);

}
