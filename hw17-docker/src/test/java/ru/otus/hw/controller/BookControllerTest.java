package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("BookController should")
@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static List<BookDto> bookDtos;

    private static List<Author> authors;

    private static List<Genre> genres;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @BeforeAll
    public static void before() {
        authors = List.of(
                new Author(1L, "Forename1"),
                new Author(2L, "Forename2")
        );
        genres = List.of(
                new Genre(1L, "Genre1"),
                new Genre(2L, "Genre2")
        );

        bookDtos = List.of(
                new BookDto(1L, "Title1", authors.get(0), genres),
                new BookDto(2L, "Title2", authors.get(1), genres)
        );
    }

    @Test
    @DisplayName("correctly return books page")
    void shouldReturnBooksPage() throws Exception {
        given(bookService.findAll()).willReturn(bookDtos);

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(bookDtos.get(0).getId()))))
                .andExpect(content().string(containsString(bookDtos.get(0).getTitle())))
                .andExpect(content().string(containsString(String.valueOf(bookDtos.get(1).getId()))))
                .andExpect(content().string(containsString(bookDtos.get(1).getTitle())));
    }

    @Test
    @DisplayName("correctly create new book")
    void shouldCreateNewBook() throws Exception {
        BookCreateDto createdBookDto = new BookCreateDto("New book title", 1L, List.of(1L, 2L));
        BookDto bookDto = new BookDto(3L, "New book title", authors.get(0), genres);
        given(bookService.insert(createdBookDto)).willReturn(bookDto);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(createdBookDto);

        mvc.perform(post("/api/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(bookDto.getId()))))
                .andExpect(content().string(containsString(bookDto.getTitle())));
        verify(bookService, times(1)).insert(createdBookDto);
    }


    @Test
    @DisplayName("correctly edit book")
    void shouldEditBook() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto(1L, "Updated title 1", 1L, List.of(1L, 2L));
        BookDto bookDto = new BookDto(1L, "Updated title 1", authors.get(0), genres);
        given(bookService.update(bookUpdateDto)).willReturn(bookDto);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(bookUpdateDto);

        mvc.perform(put("/api/books/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(bookDto.getId()))))
                .andExpect(content().string(containsString(bookDto.getTitle())));
        verify(bookService, times(1)).update(bookUpdateDto);
    }

    @Test
    @DisplayName("correctly delete book")
    void shouldDeleteBook() throws Exception {
        mvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
        verify(bookService, times(1)).deleteById(1);
    }
}
