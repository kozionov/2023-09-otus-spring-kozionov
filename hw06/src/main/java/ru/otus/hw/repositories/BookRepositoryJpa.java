package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {


    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> properties = new HashMap<>();
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-graph");
        properties.put(FETCH.getKey(), entityGraph);
        return Optional.ofNullable(em.find(Book.class, id, properties));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b left join fetch b.author", Book.class);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Optional<Book> book = findById(id);
        if (book.isPresent()) {
            em.remove(book.get());
        }
    }
}
