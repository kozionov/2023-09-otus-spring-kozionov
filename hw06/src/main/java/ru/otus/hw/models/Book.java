package ru.otus.hw.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "books_genres-author-entity-graph",
        attributeNodes = {@NamedAttributeNode("genres"), @NamedAttributeNode("author")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @OneToOne(targetEntity = Author.class)
    @JoinColumn(name = "id")
    private Author author;

    @OneToMany(targetEntity = Genre.class, fetch = FetchType.LAZY)
    @JoinTable(name = "books_genres")
    private List<Genre> genres;

}
