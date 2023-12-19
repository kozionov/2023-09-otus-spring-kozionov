package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        return bookService.findById(id);
    }

    @PostMapping("/api/books")
    public BookDto addBook(@RequestBody @Valid BookCreateDto bookCreateDto) {
        BookDto book = bookService.insert(bookCreateDto);
        return book;
    }

    @PutMapping("/api/books/{id}")
    public BookDto editBook(@PathVariable("id") long id, @RequestBody @Valid BookUpdateDto bookUpdateDto) {
        BookDto book = bookService.update(bookUpdateDto);
        return book;
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }
}
