package com.zelenenkyi.tasktracker.mapper;

import com.zelenenkyi.tasktracker.dto.TaskListMessageDto;
import com.zelenenkyi.tasktracker.model.TaskList;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskListMessageMapper {
    @Mapping(source = "boardId", target = "board.id")
    TaskList toEntity(TaskListMessageDto taskListMessageDto);

    @Mapping(source = "board.id", target = "boardId")
    TaskListMessageDto toDto(TaskList taskList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "boardId", target = "board.id")
    TaskList partialUpdate(TaskListMessageDto taskListMessageDto, @MappingTarget TaskList taskList);
}