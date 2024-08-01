package com.zelenenkyi.tasktracker.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.zelenenkyi.tasktracker.model.TaskList}
 */

public record TaskListDto(Long id, String title, Integer position, Set<TaskDto> tasks) implements Serializable {


}