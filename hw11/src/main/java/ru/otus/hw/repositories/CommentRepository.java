package ru.otus.hw.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Comment;

public interface CommentRepository extends ReactiveCrudRepository<Comment, String> {

    Flux<Comment> findAllByBookId(String bookId);

    Mono<Comment> findById(String id);

    void deleteByBookId(String bookId);

}
