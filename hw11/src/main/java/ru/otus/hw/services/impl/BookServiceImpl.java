package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(String id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            throw new EntityNotFoundException("Book with id %s not found".formatted(id));
        }
        return modelMapper.map(book.get(), BookDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(b -> modelMapper.map(b, BookDto.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDto insert(String title, String authorId, List<String> genresIds) {
        BookDto book = save(null, title, authorId, genresIds);
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    @Override
    public BookDto update(String id, String title, String authorId, List<String> genresIds) {
        BookDto book = save(id, title, authorId, genresIds);
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    private BookDto save(String id, String title, String authorId, List<String> genresIds) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(authorId)));
        var genres = genreRepository.findAllByIdIn(genresIds);
        if (isEmpty(genres)) {
            throw new EntityNotFoundException("Genres with ids %s not found".formatted(genresIds));
        }
        var book = new Book(id, title, author, genres);
        book = bookRepository.save(book);
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    @Override
    public BookDto insert(BookCreateDto bookCreateDto) {
        var book = save(null, bookCreateDto.title(), bookCreateDto.authorId(), bookCreateDto.genreId());
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    @Override
    public BookDto update(BookUpdateDto updateDto) {
        var book = save(updateDto.id(), updateDto.title(), updateDto.authorId(), updateDto.genreId());
        return modelMapper.map(book, BookDto.class);
    }
}
