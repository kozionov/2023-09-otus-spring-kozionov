package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookUpdateDto(@NotNull String id,
                            @NotBlank String title,
                            @NotNull String authorId,
                            @NotNull List<String> genreId) {
}
