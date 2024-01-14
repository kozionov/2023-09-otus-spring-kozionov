package ru.otus.hw.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.Genre;

import java.util.List;

public interface GenreRepository extends ReactiveCrudRepository<Genre, String> {

    Flux<Genre> findAll();

    Flux<Genre> findAllByIdIn(List<String> genresIds);

}
