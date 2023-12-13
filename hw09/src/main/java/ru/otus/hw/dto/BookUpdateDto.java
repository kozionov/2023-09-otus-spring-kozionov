package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookUpdateDto(@NotNull Long id, @NotBlank String title, @NotNull Long authorId, @NotNull Long genreId) {
}
