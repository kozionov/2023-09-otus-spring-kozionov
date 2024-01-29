package ru.otus.hw.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.model.document.AuthorDoc;
import ru.otus.hw.model.document.BookDoc;
import ru.otus.hw.model.document.CommentDoc;
import ru.otus.hw.model.document.GenreDoc;

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
        AuthorDoc author1 = new AuthorDoc("Name1", "SerName1");
        AuthorDoc author2 = new AuthorDoc("Name2", "SerName2");
        GenreDoc genre1 = new GenreDoc("Genre_1");
        GenreDoc genre2 = new GenreDoc("Genre_2");
        List<GenreDoc> genres = new ArrayList<>();
        genres.add(genre1);
        genres.add(genre2);
        BookDoc book1 = new BookDoc("1", "Book_1", author1, genres);
        genres = new ArrayList<>();
        genres.add(genre1);
        BookDoc book2 = new BookDoc("2", "Book_2", author2, genres);
        genres = new ArrayList<>();
        genres.add(genre2);
        BookDoc book3 = new BookDoc("3", "Book_3", author1, genres);
        CommentDoc comment1 = new CommentDoc("Comment_1", book1);
        CommentDoc comment2 = new CommentDoc("Comment_2", book2);
        CommentDoc comment3 = new CommentDoc("Comment_3", book1);

        template.insertAll(List.of(author1, author2));
        template.insertAll(List.of(genre1, genre2));
        template.insertAll(List.of(book1, book2, book3));
        template.insertAll(List.of(comment1, comment2, comment3));
    }

}
