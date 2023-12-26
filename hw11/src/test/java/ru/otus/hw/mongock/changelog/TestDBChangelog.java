package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class TestDBChangelog {

    @ChangeSet(order = "001", id = "dropDB", author = "kozionov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertData", author = "kozionov")
    public void insertData(BookRepository bookRepository, SimpleAuthorRepository authorRepository,
                           GenreRepository genreRepository, CommentRepository commentRepository) {
        Author author1 = new Author("1", "AuthorForename1");
        Author author2 = new Author("2", "AuthorForename2");
        Author author3 = new Author("3", "AuthorForename3");
        Genre genre1 = new Genre("1", "Genre1");
        Genre genre2 = new Genre("2", "Genre2");
        Genre genre3 = new Genre("3", "Genre3");
        Genre genre4 = new Genre("4", "Genre4");
        Genre genre5 = new Genre("5", "Genre5");
        List<Genre> genres = new ArrayList<>();
        genres.add(genre1);
        genres.add(genre2);
        Book book1 = new Book("1", "Book1", author1, genres);
        genres = new ArrayList<>();
        genres.add(genre3);
        genres.add(genre4);
        Book book2 = new Book("2", "Book2", author2, genres);
        genres = new ArrayList<>();
        genres.add(genre2);
        genres.add(genre5);
        Book book3 = new Book("3", "Book3", author1, genres);
        Comment comment1 = new Comment("1", "Comment1", book1);
        Comment comment2 = new Comment("2", "Comment2", book2);
        Comment comment3 = new Comment("3", "Comment3", book3);
        Comment comment4 = new Comment("4", "Comment4", book1);
        Comment comment5 = new Comment("5", "Comment5", book1);

        authorRepository.saveAll(List.of(author1, author2, author3));
        genreRepository.saveAll(List.of(genre1, genre2, genre3, genre4, genre5));
        bookRepository.saveAll(List.of(book1, book2, book3));
        commentRepository.saveAll(List.of(comment1, comment2, comment3, comment4, comment5));
    }

}
