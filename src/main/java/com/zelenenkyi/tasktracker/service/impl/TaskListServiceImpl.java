package com.zelenenkyi.tasktracker.service.impl;

import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.TaskList;
import com.zelenenkyi.tasktracker.repository.TaskListRepository;
import com.zelenenkyi.tasktracker.service.TaskListService;
import com.zelenenkyi.tasktracker.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;


    @Override
    public List<TaskList> getAll() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList getById(Long id) {
        return taskListRepository.findById(id).orElseThrow();
    }

    @Override
    public void create(TaskList item) {
        taskListRepository.save(item);
    }

    @Override
    public boolean update(TaskList item, Long id) {
        taskListRepository.save(item);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        taskListRepository.deleteById(id);
        return !taskListRepository.existsById(id);
    }

    @Override
    public List<TaskList> getAllByBoard(Board board) {
        return taskListRepository.findByBoard(board);
    }

    @Override
    public Integer getNewPosition(Long aLong) {
        return taskListRepository.countByBoard_Id(aLong);
    }
}
