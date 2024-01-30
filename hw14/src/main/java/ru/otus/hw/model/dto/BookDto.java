package ru.otus.hw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {

    private String id;

    private String title;

    private String description;

    private String authorId;

    private Set<String> genresIds;

}
