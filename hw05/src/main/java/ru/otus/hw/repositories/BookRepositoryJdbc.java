package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {

    private final GenreRepository genreRepository;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        Book book = null;
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            book = namedParameterJdbcOperations
                    .queryForObject("select books.id, books.title, books.author_id, authors.full_name from books" +
                            " left join authors on books.author_id = authors.id" +
                            " where books.id = :id", params, new BookRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = Collections.singletonList(book);
        mergeBooksInfo(books, genres, relations);

        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from books where id = :id", params);
    }

    private List<Book> getAllBooksWithoutGenres() {
        return namedParameterJdbcOperations
                .query("select books.id, books.title, books.author_id, authors.full_name from books " +
                        "left join authors on books.author_id = authors.id", new BookRowMapper());
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return namedParameterJdbcOperations
                .query("select book_id, genre_id from books_genres", new BookGenreRelationRowMapper());
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        // Добавить книгам (booksWithoutGenres) жанры (genres) в соответствии со связями (relations)
        for (Book b : booksWithoutGenres) {
            for (BookGenreRelation relation : relations) {
                if (relation.bookId == b.getId()) {
                    Genre genre = genres.stream().filter(g -> g.getId() == relation.genreId).findFirst().get();
                    b.getGenres().add(genre);
                }
            }
        }
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId());
        namedParameterJdbcOperations.update("insert into books (title, author_id) values (:title, :author_id)",
                parameters, keyHolder, new String[]{"id"});

        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        namedParameterJdbcOperations.update("update books set title = :title, author_id=:author_id where id =:id",
                Map.of("id", book.getId(), "title", book.getTitle(), "author_id", book.getAuthor().getId()));

        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        List<Long> genreIds = book.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList());
        String values = genreIds.stream()
                .map(i -> "(" + book.getId() + "," + i + ")")
                .collect(Collectors.joining(","));
        namedParameterJdbcOperations
                .update("insert into books_genres(book_id, genre_id) values " + values, new HashMap<>());
    }

    private void removeGenresRelationsFor(Book book) {
        Map<String, Object> params = Collections.singletonMap("id", book.getId());
        namedParameterJdbcOperations.update("delete from books_genres where book_id = :id", params);
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("books.id");
            String title = rs.getString("books.title");
            long authorId = rs.getLong("books.author_id");
            String authorName = rs.getString("authors.full_name");
            return new Book(id, title, new Author(authorId, authorName), new ArrayList<>());
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {

        @Override
        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            return new ArrayList<>();
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }

    private static class BookGenreRelationRowMapper implements RowMapper<BookGenreRelation> {

        @Override
        public BookGenreRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
            long bookId = rs.getLong("book_id");
            long genreId = rs.getLong("genre_id");
            return new BookGenreRelation(bookId, genreId);
        }
    }
}
