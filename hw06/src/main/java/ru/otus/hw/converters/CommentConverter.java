package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

@Component
public class CommentConverter {
    public String commentToString(Comment comment) {
        return "Id: %d, Name: %s, Book: %s".formatted(comment.getId(), comment.getText(), comment.getBook().getTitle());
    }
}
