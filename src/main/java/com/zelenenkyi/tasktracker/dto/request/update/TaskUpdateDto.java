package com.zelenenkyi.tasktracker.dto.request.update;

import java.io.Serializable;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.Task}
 */
public record TaskUpdateDto(String title, String description, Integer position) implements Serializable {
}