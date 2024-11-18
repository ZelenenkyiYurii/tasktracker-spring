package com.zelenenkyi.tasktracker.dto.request.create;

import java.io.Serializable;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.Task}
 */
public record TaskCreateDto(String title, String description, Long taskListId) implements Serializable {
}