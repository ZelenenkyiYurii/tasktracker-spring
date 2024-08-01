package com.zelenenkyi.tasktracker.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.Task}
 */

public record TaskDto(Long id, String title, String description, Integer position) implements Serializable {
}