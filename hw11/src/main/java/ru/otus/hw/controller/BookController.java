package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/api/books")
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/api/books/{id}")
    public Mono<ResponseEntity<BookDto>> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .map(e -> modelMapper.map(e, BookDto.class))
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping("/api/books")
    public BookDto addBook(@RequestBody @Valid BookCreateDto bookCreateDto) {
        BookDto book = insert(bookCreateDto);
        return book;
    }

    @PutMapping("/api/books/{id}")
    public BookDto editBook(@PathVariable("id") String id, @RequestBody @Valid BookUpdateDto bookUpdateDto) {
        BookDto book = update(bookUpdateDto);
        return book;
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") String id) {
        bookService.deleteById(id);
    }

    private BookDto save(String id, String title, String authorId, List<String> genresIds) {
        var author = authorRepository.findById(authorId).block();
        var genres = genreRepository.findAllByIdIn(genresIds).buffer().blockFirst();
        var book = new Book(id, title, author, genres);
        book = bookRepository.save(book).block();
        return modelMapper.map(book, BookDto.class);
    }


    private BookDto insert(BookCreateDto bookCreateDto) {
        var book = save(null, bookCreateDto.title(), bookCreateDto.authorId(), bookCreateDto.genreId());
        return modelMapper.map(book, BookDto.class);
    }


    private BookDto update(BookUpdateDto updateDto) {
        var book = save(updateDto.id(), updateDto.title(), updateDto.authorId(), updateDto.genreId());
        return modelMapper.map(book, BookDto.class);
    }
}
