package com.zelenenkyi.tasktracker.dto.request.create;

import java.io.Serializable;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.TaskList}
 */
public record TaskListCreateDto(String title, Long boardId) implements Serializable {
}