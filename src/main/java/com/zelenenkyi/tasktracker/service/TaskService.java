package com.zelenenkyi.tasktracker.service;

import com.zelenenkyi.tasktracker.model.Task;
import com.zelenenkyi.tasktracker.model.TaskList;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService extends HelperService<Task>{
    List<Task> getAllByTaskList(TaskList taskList);

    Integer getNewPosition(Long id);
}
