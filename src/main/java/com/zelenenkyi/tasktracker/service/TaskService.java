package com.zelenenkyi.tasktracker.service;

import com.zelenenkyi.tasktracker.dto.request.update.TaskUpdateDto;
import com.zelenenkyi.tasktracker.model.Task;
import com.zelenenkyi.tasktracker.model.TaskList;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


public interface TaskService  {
    List<Task> getAll();

    Task getById(Long id);

    void create(Task item);

    boolean update(TaskUpdateDto item, Long id);

    boolean delete(Long id);
    List<Task> getAllByTaskList(TaskList taskList);

    Integer getNewPosition(Long id);

    Long getBoardIdByTaskId(Long taskId);

    HashMap<Long,Integer> getMapIdPosition(Long taskListId);
}
