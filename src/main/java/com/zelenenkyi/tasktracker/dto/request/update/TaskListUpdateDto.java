package com.zelenenkyi.tasktracker.dto.request.update;

import java.io.Serializable;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.TaskList}
 */
public record TaskListUpdateDto(String title, Integer position) implements Serializable {
}