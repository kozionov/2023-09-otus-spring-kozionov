package ru.otus.hw.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "genres")
@SequenceGenerator(name = "genres_id_seq", sequenceName = "genres_id_seq", allocationSize = 1)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genres_id_seq")
    @Column(name = "genre_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 500)
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
