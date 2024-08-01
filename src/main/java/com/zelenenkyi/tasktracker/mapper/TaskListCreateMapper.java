package com.zelenenkyi.tasktracker.mapper;

import com.zelenenkyi.tasktracker.dto.TaskListCreateDto;
import com.zelenenkyi.tasktracker.model.TaskList;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskListCreateMapper {
    @Mapping(source = "boardId", target = "board.id")
    TaskList toEntity(TaskListCreateDto taskListCreateDto);

    @Mapping(source = "board.id", target = "boardId")
    TaskListCreateDto toDto(TaskList taskList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "boardId", target = "board.id")
    TaskList partialUpdate(TaskListCreateDto taskListCreateDto, @MappingTarget TaskList taskList);
}