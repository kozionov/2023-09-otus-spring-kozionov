package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

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
    public Mono<BookDto> addBook(@RequestBody @Valid BookCreateDto bookCreateDto) {
        return insert(bookCreateDto);
    }

    @PutMapping("/api/books/{id}")
    public Mono<BookDto> editBook(@PathVariable("id") String id, @RequestBody @Valid BookUpdateDto bookUpdateDto) {
        return update(bookUpdateDto);
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }

    private Mono<BookDto> save(String id, String title, String authorId, List<String> genresIds) {
        var genresMono = genreRepository.findAllByIdIn(genresIds).collectList();
        var authorMono = authorRepository.findById(authorId);
        return Mono.zip(genresMono, authorMono, (genres, author) -> new Book(id, title, author, genres))
                .flatMap(bookRepository::save)
                .map(book -> modelMapper.map(book, BookDto.class));
    }

    private Mono<BookDto> insert(BookCreateDto bookCreateDto) {
        return save(null, bookCreateDto.title(), bookCreateDto.authorId(), bookCreateDto.genreId());
    }

    private Mono<BookDto> update(BookUpdateDto updateDto) {
        return save(updateDto.id(), updateDto.title(), updateDto.authorId(), updateDto.genreId());
    }
}
