package com.zelenenkyi.tasktracker.mapper;

import com.zelenenkyi.tasktracker.dto.TaskCreateDto;
import com.zelenenkyi.tasktracker.model.Task;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskCreateMapper {
    Task toEntity(TaskCreateDto taskCreateDto);


    TaskCreateDto toDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task partialUpdate(TaskCreateDto taskCreateDto, @MappingTarget Task task);
}