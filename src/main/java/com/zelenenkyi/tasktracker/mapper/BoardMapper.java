package com.zelenenkyi.tasktracker.mapper;

import com.zelenenkyi.tasktracker.dto.BoardDto;
import com.zelenenkyi.tasktracker.model.Board;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BoardMapper {
    Board toEntity(BoardDto boardDto);

    @AfterMapping
    default void linkTaskLists(@MappingTarget Board board) {
        board.getTaskLists().forEach(taskList -> taskList.setBoard(board));
    }

    BoardDto toDto(Board board);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Board partialUpdate(BoardDto boardDto, @MappingTarget Board board);

}