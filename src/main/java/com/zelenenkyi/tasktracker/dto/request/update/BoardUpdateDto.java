package com.zelenenkyi.tasktracker.dto.request.update;

import java.io.Serializable;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.Board}
 */
public record BoardUpdateDto(String title) implements Serializable {
}