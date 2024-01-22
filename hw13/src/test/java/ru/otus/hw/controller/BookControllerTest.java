package ru.otus.hw.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("BookController should")
@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
public class BookControllerTest {

    private static List<BookDto> bookDtos;

    private static List<Author> authors;

    private static List<Genre> genres;

    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

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

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }


    @Test
    @DisplayName("have access to url with authenticated user")
    @WithMockUser(username = "admin", authorities = {"ROLE_USER"})
    void shouldHaveAccessWithRoleUser() throws Exception {
        given(bookService.findAll()).willReturn(bookDtos);
        given(authorService.findAll()).willReturn(authors);
        given(genreService.findAll()).willReturn(genres);
        given(bookService.findById(1L)).willReturn(bookDtos.get(0));

        mockMvc.perform(get("/")).andExpect(status().isOk());
        mockMvc.perform(get("/edit?id=1")).andExpect(status().is(403));
        mockMvc.perform(get("/create")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("have access to url with authenticated user")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void shouldHaveAccessWithRoleAdmin() throws Exception {
        given(bookService.findAll()).willReturn(bookDtos);
        given(authorService.findAll()).willReturn(authors);
        given(genreService.findAll()).willReturn(genres);
        given(bookService.findById(1L)).willReturn(bookDtos.get(0));

        mockMvc.perform(get("/")).andExpect(status().isOk());
        mockMvc.perform(get("/edit?id=1")).andExpect(status().isOk());
        mockMvc.perform(get("/create")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("not have access to url without authenticated user")
    void shouldNotHaveAccessWithoutUser() throws Exception {
        given(bookService.findAll()).willReturn(bookDtos);
        given(authorService.findAll()).willReturn(authors);
        given(genreService.findAll()).willReturn(genres);
        given(bookService.findById(1L)).willReturn(bookDtos.get(0));

        mockMvc.perform(get("/")).andExpect(status().isOk());
        mockMvc.perform(get("/edit?id=1")).andExpect(status().is(302));
        mockMvc.perform(get("/create")).andExpect(status().is(302));
    }

    @Test
    @DisplayName("correctly return book details page")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void shouldReturnBookDetailsPage() throws Exception {
        given(bookService.findById(1L)).willReturn(bookDtos.get(0));

        mockMvc.perform(get("/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(bookDtos.get(0).getTitle())));
    }

    @Test
    @DisplayName("correctly return create book page")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void shouldReturnCreateBookPage() throws Exception {
        given(authorService.findAll()).willReturn(authors);
        given(genreService.findAll()).willReturn(genres);

        mockMvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(authors.get(0).getFullName())))
                .andExpect(content().string(containsString(authors.get(1).getFullName())))
                .andExpect(content().string(containsString(genres.get(0).getName())))
                .andExpect(content().string(containsString(genres.get(1).getName())));
    }


}
