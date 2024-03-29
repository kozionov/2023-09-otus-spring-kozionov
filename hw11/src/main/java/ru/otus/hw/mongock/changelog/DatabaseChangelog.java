package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "kozionov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertData", author = "kozionov")
    public void insertData(BookRepository bookRepository, AuthorRepository authorRepository,
                           GenreRepository genreRepository, CommentRepository commentRepository) {
        Author author1 = new Author("Author_1");
        Author author2 = new Author("Author_2");
        Author author3 = new Author("Author_3");
        Genre genre1 = new Genre("Genre_1");
        Genre genre2 = new Genre("Genre_2");
        Genre genre3 = new Genre("Genre_3");
        List<Genre> genres = new ArrayList<>();
        genres.add(genre1);
        genres.add(genre2);
        genres.add(genre3);
        Book book1 = new Book("1", "Book_1", author1, genres);
        genres = new ArrayList<>();
        genres.add(genre1);
        Book book2 = new Book("2","Book_2", author2, genres);
        genres = new ArrayList<>();
        genres.add(genre2);
        Book book3 = new Book("3", "Book_3", author1, genres);
        Comment comment1 = new Comment("Comment_1", book1);
        Comment comment2 = new Comment("Comment_2", book2);
        Comment comment3 = new Comment("Comment_3", book1);

        authorRepository.saveAll(List.of(author1, author2, author3)).collectList().block();
        genreRepository.saveAll(List.of(genre1, genre2, genre3)).collectList().block();
        bookRepository.saveAll(List.of(book1, book2, book3)).collectList().block();
        commentRepository.saveAll(List.of(comment1, comment2, comment3)).collectList().block();
    }
}
