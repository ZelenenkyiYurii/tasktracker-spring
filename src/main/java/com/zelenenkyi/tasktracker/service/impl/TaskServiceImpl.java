package com.zelenenkyi.tasktracker.service.impl;

import com.zelenenkyi.tasktracker.dto.request.update.TaskUpdateDto;
import com.zelenenkyi.tasktracker.mapper.TaskCreateMapper;
import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.Task;
import com.zelenenkyi.tasktracker.model.TaskList;
import com.zelenenkyi.tasktracker.repository.TaskListRepository;
import com.zelenenkyi.tasktracker.repository.TaskRepository;
import com.zelenenkyi.tasktracker.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskCreateMapper taskCreateMapper;
    private final TaskListRepository taskListRepository;

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    public void create(Task item) {
        taskRepository.save(item);
    }

    @Override
    public boolean update(TaskUpdateDto item, Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(item.title());
            task.setPosition(item.position());
            task.setDescription(item.description());
            taskRepository.save(task);
            return true;
        }

        return false;
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
        return taskRepository.countByTaskList_Id(id);
    }

    @Override
    public Long getBoardIdByTaskId(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        return task.getTaskList().getBoard().getId();
    }

    @Override
    public HashMap<Long, Integer> getMapIdPosition(Long taskListId) {
        List list =taskRepository.findByTaskListId(taskListId);
        return (HashMap<Long, Integer>) list.stream()
                .collect(Collectors.toMap(Task::getId, Task::getPosition));
    }

    @Transactional
    public boolean changePosition(Long taskId, Long sourceTaskListId, Long destinationTaskListId, Integer sourceIndex, Integer destinationIndex) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (sourceTaskListId.equals(destinationTaskListId) && !sourceIndex.equals(destinationIndex)) {

            //Optional<Task> taskOptional=taskRepository.findById(taskId);
            if (taskOptional.isPresent()) {
                Task taskToMove = taskOptional.get();
                TaskList taskList = taskToMove.getTaskList();
                //Board board = taskListToMove.getBoard();
                int currentPosition = taskToMove.getPosition();

                if (destinationIndex > currentPosition) {
                    List<Task> tasksToUpdate = taskRepository.findByTaskListIdAndPositionBetween(
                            taskList.getId(), currentPosition + 1, destinationIndex);

                    tasksToUpdate.forEach(task -> task.setPosition(task.getPosition() - 1));
                    taskRepository.saveAll(tasksToUpdate);
                } else if (destinationIndex < currentPosition) {
                    List<Task> tasksToUpdate = taskRepository.findByTaskListIdAndPositionBetween(
                            taskList.getId(), destinationIndex, currentPosition - 1);

                    tasksToUpdate.forEach(task -> task.setPosition(task.getPosition() + 1));
                    taskRepository.saveAll(tasksToUpdate);
                }

                taskToMove.setPosition(destinationIndex);
                taskRepository.save(taskToMove);
                return true;
            }


        } else {
            //take from old list
            if (taskOptional.isPresent()) {
                Task taskToMove = taskOptional.get();
                TaskList sourceTaskList = taskToMove.getTaskList();
                TaskList destinationTaskList = taskListRepository.findById(destinationTaskListId).orElseThrow();
                int currentPosition = taskToMove.getPosition();
                int newPosition = destinationIndex;

                List<Task> tasksSourceToUpdate = taskRepository.findByTaskList_IdAndPositionGreaterThan(taskToMove.getTaskList().getId(), taskToMove.getPosition());
                List<Task> tasksDestinationToUpdate = taskRepository.findByTaskList_IdAndPositionGreaterThanEqual(destinationTaskListId, destinationIndex);
                tasksSourceToUpdate.forEach(task -> task.setPosition(task.getPosition() - 1));
                tasksDestinationToUpdate.forEach(task -> task.setPosition(task.getPosition() + 1));
                taskToMove.setTaskList(destinationTaskList);
                taskToMove.setPosition(destinationIndex);
                taskRepository.saveAll(tasksSourceToUpdate);
                taskRepository.saveAll(tasksDestinationToUpdate);
                taskRepository.save(taskToMove);
                return true;
            }


        }

        return false;
    }
}
