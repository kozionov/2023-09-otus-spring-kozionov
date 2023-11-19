package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAll();

    @EntityGraph(attributePaths = "book")
    List<Comment> findAllByBookId(long bookId);

}
