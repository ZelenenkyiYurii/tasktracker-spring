package com.zelenenkyi.tasktracker.mapper;

import com.zelenenkyi.tasktracker.dto.request.create.BoardCreateDto;
import com.zelenenkyi.tasktracker.model.Board;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BoardCreateMapper {
    Board toEntity(BoardCreateDto boardCreateDto);

    BoardCreateDto toDto(Board board);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Board partialUpdate(BoardCreateDto boardCreateDto, @MappingTarget Board board);
}