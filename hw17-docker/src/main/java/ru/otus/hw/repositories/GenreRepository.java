package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.hw.models.Genre;

import java.util.List;

@RepositoryRestResource(path = "genre")
public interface GenreRepository extends CrudRepository<Genre, Long> {

    List<Genre> findAll();

    List<Genre> findAllByIdIn(List<Long> genresIds);

}
