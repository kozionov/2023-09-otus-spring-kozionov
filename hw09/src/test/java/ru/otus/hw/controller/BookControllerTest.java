package ru.otus.hw.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

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

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(bookDtos.get(0).getTitle())))
                .andExpect(content().string(containsString(bookDtos.get(1).getTitle())));
    }

    @Test
    @DisplayName("correctly return book details page")
    void shouldReturnBookDetailsPage() throws Exception {
        given(bookService.findById(1L)).willReturn(bookDtos.get(0));

        mvc.perform(get("/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(bookDtos.get(0).getTitle())));
    }

    @Test
    @DisplayName("correctly return create book page")
    void shouldReturnCreateBookPage() throws Exception {
        given(authorService.findAll()).willReturn(authors);
        given(genreService.findAll()).willReturn(genres);

        mvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(authors.get(0).getFullName())))
                .andExpect(content().string(containsString(authors.get(1).getFullName())))
                .andExpect(content().string(containsString(genres.get(0).getName())))
                .andExpect(content().string(containsString(genres.get(1).getName())));
    }

    @Test
    @DisplayName("correctly create new book")
    void shouldCreateNewBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto("New book title", 1L, List.of(1L));

        mvc.perform(post("/create?title=New book title&authorId=1&genreId=1"))
                .andExpect(status().is3xxRedirection());
        verify(bookService, times(1)).insert(bookCreateDto);
    }


    @Test
    @DisplayName("correctly return edit book page")
    void shouldReturnEditBookPage() throws Exception {
        given(bookService.findById(1L)).willReturn(bookDtos.get(0));
        given(authorService.findAll()).willReturn(authors);
        given(genreService.findAll()).willReturn(genres);

        mvc.perform(get("/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(bookDtos.get(0).getTitle())))
                .andExpect(content().string(containsString(authors.get(0).getFullName())))
                .andExpect(content().string(containsString(authors.get(1).getFullName())))
                .andExpect(content().string(containsString(genres.get(0).getName())))
                .andExpect(content().string(containsString(genres.get(1).getName())));
    }

    @Test
    @DisplayName("correctly edit book")
    void shouldEditBook() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto(1L, "Updated title 1", 2L, List.of(2L));

        mvc.perform(post("/edit?id=1&title=Updated title 1&authorId=2&genreId=2"))
                .andExpect(status().is3xxRedirection());
        verify(bookService, times(1)).update(bookUpdateDto);
    }

    @Test
    @DisplayName("correctly delete book")
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/delete?id=1"))
                .andExpect(status().is3xxRedirection());
        verify(bookService, times(1)).deleteById(1);
    }
}
