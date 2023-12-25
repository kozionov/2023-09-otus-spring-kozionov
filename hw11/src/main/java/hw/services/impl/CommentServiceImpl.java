package hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByBookId(String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            throw new EntityNotFoundException("Book with id %s not found".formatted(bookId));
        }
        return commentRepository.findAllByBookId(bookId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findCommentById(String id) {
        return commentRepository.findById(id);
    }

    @Transactional
    @Override
    public Comment insert(String text, String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            throw new EntityNotFoundException("Book with id %s not found".formatted(bookId));
        }
        Comment comment = new Comment();
        comment.setText(text);
        comment.setBook(book.get());
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Comment update(String id, String text, String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            throw new EntityNotFoundException("Book with id %s not found".formatted(bookId));
        }
        Comment comment = commentRepository.findById(id).get();
        comment.setText(text);
        comment.setBook(book.get());
        return commentRepository.save(comment);
    }
}
