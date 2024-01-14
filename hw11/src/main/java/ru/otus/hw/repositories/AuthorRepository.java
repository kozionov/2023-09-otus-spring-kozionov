package ru.otus.hw.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Author;

public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {
    Flux<Author> findAll();

    Mono<Author> findById(String id);
}
