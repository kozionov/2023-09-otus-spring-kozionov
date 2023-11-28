package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, Long> {

    List<Comment> findAllByBookId(long bookId);

    Optional<Comment> findById(long id);

}
