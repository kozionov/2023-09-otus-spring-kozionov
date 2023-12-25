package hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findAll();

    List<Genre> findAllByIdIn(List<String> genresIds);

}
