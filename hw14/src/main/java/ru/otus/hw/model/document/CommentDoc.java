package ru.otus.hw.model.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class CommentDoc {

    @Id
    private String id;

    private String text;

    @DBRef
    private BookDoc book;

    public CommentDoc(String text, BookDoc book) {
        this.text = text;
        this.book = book;
    }
}
