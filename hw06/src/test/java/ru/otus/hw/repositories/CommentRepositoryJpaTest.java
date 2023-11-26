package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями")
@DataJpaTest
@Import({CommentRepositoryJpa.class})
public class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать коммент по id")
    @Test
    void shouldReturnCorrectCommentById() {
        var expectedStudent = em.find(Comment.class, 1);
        var actualComment = commentRepositoryJpa.findById(1);
        assertThat(actualComment).isPresent()
                .get()
                .isSameAs(expectedStudent);
    }

}
