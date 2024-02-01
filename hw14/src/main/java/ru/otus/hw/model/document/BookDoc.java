package ru.otus.hw.model.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class BookDoc {

    @Id
    private String id;

    private String title;

    private String description;

    @DBRef
    private AuthorDoc author;

    @DBRef
    private List<GenreDoc> genres;

    public BookDoc(String title, String description, AuthorDoc author, List<GenreDoc> genres) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.genres = genres;
    }
}
