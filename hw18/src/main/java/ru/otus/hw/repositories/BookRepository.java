package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "book")
public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author"})
    Optional<Book> findById(Long id);

    @EntityGraph(attributePaths = {"author"})
    List<Book> findAll();
}
