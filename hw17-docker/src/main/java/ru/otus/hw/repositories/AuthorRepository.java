package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "author")
public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
