package com.zelenenkyi.tasktracker.mapper;

import com.zelenenkyi.tasktracker.dto.websocket.TaskMessageDto;
import com.zelenenkyi.tasktracker.model.Task;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMessageMapper {
    Task toEntity(TaskMessageDto taskMessageDto);

    @Mapping(source = "taskList.id", target = "taskListId")
    TaskMessageDto toDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task partialUpdate(TaskMessageDto taskMessageDto, @MappingTarget Task task);
}