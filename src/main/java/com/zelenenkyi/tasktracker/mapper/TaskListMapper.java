package com.zelenenkyi.tasktracker.mapper;

import com.zelenenkyi.tasktracker.dto.TaskListDto;
import com.zelenenkyi.tasktracker.model.TaskList;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskListMapper {
    TaskList toEntity(TaskListDto taskListDto);

    @AfterMapping
    default void linkTasks(@MappingTarget TaskList taskList) {
        taskList.getTasks().forEach(task -> task.setTaskList(taskList));
    }

    TaskListDto toDto(TaskList taskList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaskList partialUpdate(TaskListDto taskListDto, @MappingTarget TaskList taskList);
}