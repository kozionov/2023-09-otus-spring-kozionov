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
public class Book {

    @Id
    private String id;

    private String title;

    private String description;

    @DBRef
    private Author author;

    @DBRef
    private List<Genre> genres;

    public Book(String title, String description, Author author, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.genres = genres;
    }
}
