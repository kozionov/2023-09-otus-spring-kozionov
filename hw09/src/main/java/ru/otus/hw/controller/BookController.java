package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> books = service.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        BookDto book = service.findById(id);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/edit")
    public String savePerson(BookDto book) {
        service.update(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getGenres().stream().map(x->x.getId()).collect(Collectors.toList()));
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        service.deleteById(id);
        return "redirect:/";
    }
}
