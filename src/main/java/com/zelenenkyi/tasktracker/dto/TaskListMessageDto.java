package com.zelenenkyi.tasktracker.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.TaskList}
 */
public record TaskListMessageDto(Long id, String title, Integer position, Long boardId) implements Serializable {
}