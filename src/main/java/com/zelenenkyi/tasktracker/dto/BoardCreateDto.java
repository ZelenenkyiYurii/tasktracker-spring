package com.zelenenkyi.tasktracker.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.Board}
 */
public record BoardCreateDto(@NotNull @NotEmpty String title) implements Serializable {
}