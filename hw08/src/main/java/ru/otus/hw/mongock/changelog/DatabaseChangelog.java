package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.GenreRepository;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "kozionov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "kozionov")
    public void insertAuthors(AuthorRepository repository) {
        repository.save(new Author("Pushkin"));
        repository.save(new Author("Lermontov"));
        repository.save(new Author("Dostoevsky"));
    }
    @ChangeSet(order = "003", id = "insertGenres", author = "kozionov")
    public void insertGenres(GenreRepository repository) {
        repository.save(new Genre("Genre_1"));
        repository.save(new Genre("Genre_2"));
        repository.save(new Genre("Genre_3"));
    }


}
