package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "comment")
public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByBookId(long bookId);

    @EntityGraph(attributePaths = {"book"})
    Optional<Comment> findById(long id);

}
