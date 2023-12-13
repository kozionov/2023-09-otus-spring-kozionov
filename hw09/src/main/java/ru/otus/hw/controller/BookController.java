package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    private final GenreService genreService;

    private final AuthorService authorService;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<BookDto> books = service.findAll();
        model.addAttribute("books", books);
        return "main";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("authors", authorService.findAll());
        return "create";
    }

    @PostMapping("/create")
    public String createBook(@Valid BookCreateDto bookCreateDto) {
        service.insert(bookCreateDto);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        BookDto book = service.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("authors", authorService.findAll());
        return "edit";
    }

    @PostMapping("/edit")
    public String saveBook(@Valid BookUpdateDto bookUpdateDto) {
        service.update(bookUpdateDto);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        service.deleteById(id);
        return "redirect:/";
    }
}
