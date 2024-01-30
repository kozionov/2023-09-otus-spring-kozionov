package ru.otus.hw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookDto {

    private String id;

    private String title;

    private String description;

    private String authorId;

    private Set<String> genresIds;

    public BookDto(String id, String title, String description, String authorId, Set<String> genresIds) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.genresIds = genresIds;
    }
}
