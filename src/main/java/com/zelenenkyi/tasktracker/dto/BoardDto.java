package com.zelenenkyi.tasktracker.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.Board}
 */

public record BoardDto(Long id, String title, Set<TaskListDto> taskLists) implements Serializable {
}