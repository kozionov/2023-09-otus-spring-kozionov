package ru.otus.hw.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.model.document.Author;
import ru.otus.hw.model.document.Book;
import ru.otus.hw.model.document.Comment;
import ru.otus.hw.model.document.Genre;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "kozionov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertData", author = "kozionov")
    public void insertData(MongockTemplate template) {
        Author author1 = new Author("Name1", "SerName1");
        Author author2 = new Author("Name2", "SerName2");
        Genre genre1 = new Genre("Genre_1");
        Genre genre2 = new Genre("Genre_2");
        List<Genre> genres = new ArrayList<>();
        genres.add(genre1);
        genres.add(genre2);
        Book book1 = new Book("1", "Book_1", author1, genres);
        genres = new ArrayList<>();
        genres.add(genre1);
        Book book2 = new Book("2", "Book_2", author2, genres);
        genres = new ArrayList<>();
        genres.add(genre2);
        Book book3 = new Book("3", "Book_3", author1, genres);
        Comment comment1 = new Comment("Comment_1", book1);
        Comment comment2 = new Comment("Comment_2", book2);
        Comment comment3 = new Comment("Comment_3", book1);

        template.insertAll(List.of(author1, author2));
        template.insertAll(List.of(genre1, genre2));
        template.insertAll(List.of(book1, book2, book3));
        template.insertAll(List.of(comment1, comment2, comment3));
    }

}
