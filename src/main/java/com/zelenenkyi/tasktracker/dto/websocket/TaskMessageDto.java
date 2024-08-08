package com.zelenenkyi.tasktracker.dto.websocket;

import java.io.Serializable;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.Task}
 */
public record TaskMessageDto(Long id, String title, String description, Integer position,
                             Long taskListId) implements Serializable {
}