package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepository should")
@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("correctly create and read comment")
    @Test
    void shouldCreateReadComment() {
        Book book = mongoTemplate.findById("2", Book.class);

        Comment newComment = new Comment(null, "Comment6", book);
        commentRepository.save(newComment);

        Optional<Comment> optComment = commentRepository.findById(newComment.getId());
        assertThat(optComment.isPresent()).isTrue();
        Comment comment = optComment.get();
        assertThat(comment.getId()).isEqualTo(newComment.getId());
        assertThat(comment.getText()).isEqualTo(newComment.getText());
        assertThat(comment.getBook().getId()).isEqualTo(book.getId());
    }

    @DisplayName("return all comments for book by bookId")
    @Test
    void shouldGetCommentsForBookByBookId() {
        List<Comment> comments = commentRepository.findAllByBookId("1");
        assertThat(comments).hasSize(3);
    }
}
