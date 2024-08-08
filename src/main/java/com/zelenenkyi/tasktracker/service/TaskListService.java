package com.zelenenkyi.tasktracker.service;

import com.zelenenkyi.tasktracker.dto.request.update.TaskListUpdateDto;
import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.TaskList;

import java.util.HashMap;
import java.util.List;


public interface TaskListService {
    List<TaskList> getAll();

    TaskList getById(Long id);

    void create(TaskList item);

    boolean update(TaskListUpdateDto item, Long id);

    boolean delete(Long id);
    List<TaskList> getAllByBoard(Board board);

    Integer getNewPosition(Long aLong);
    public boolean changePosition(Long id, Integer newPosition);
    Long getBoardIdByTaskListId(Long taskListId);
    HashMap<Long,Integer> getMapIdPosition(Long boardId);


}
