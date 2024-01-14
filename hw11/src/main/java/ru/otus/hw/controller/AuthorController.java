package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorRepository authorRepository;

    @GetMapping("/api/authors")
    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

}
