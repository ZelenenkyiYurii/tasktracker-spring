package com.zelenenkyi.tasktracker.service.impl;

import com.zelenenkyi.tasktracker.model.Task;
import com.zelenenkyi.tasktracker.model.TaskList;
import com.zelenenkyi.tasktracker.repository.TaskRepository;
import com.zelenenkyi.tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void create(Task item) {
        taskRepository.save(item);
    }

    @Override
    public boolean update(Task item, Long id) {
        taskRepository.save(item);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        taskRepository.deleteById(id);
        return !taskRepository.existsById(id);
    }

    @Override
    public List<Task> getAllByTaskList(TaskList taskList) {
        return taskRepository.findByTaskList(taskList);
    }

    @Override
    public Integer getNewPosition(Long id) {
        return taskRepository.countByTaskList_Id(id)+1;
    }
}
