package com.zelenenkyi.tasktracker.service;

import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.TaskList;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskListService extends HelperService<TaskList>{
    List<TaskList> getAllByBoard(Board board);

    Integer getNewPosition(Long aLong);
}
