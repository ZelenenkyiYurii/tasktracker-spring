package com.zelenenkyi.tasktracker.service.impl;

import com.zelenenkyi.tasktracker.dto.request.update.TaskListUpdateDto;
import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.TaskList;
import com.zelenenkyi.tasktracker.repository.TaskListRepository;
import com.zelenenkyi.tasktracker.service.TaskListService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    @Override
    public boolean update(TaskListUpdateDto item, Long id) {
        Optional<TaskList> taskListOptional=taskListRepository.findById(id);
        if(taskListOptional.isPresent()){
            TaskList taskList=taskListOptional.get();
            taskList.setTitle(item.title());
            taskList.setPosition(item.position());
            taskListRepository.save(taskList);
            return true;
        }

        return false;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        TaskList taskListToDelete = taskListRepository.findById(id)
                .orElseThrow();

        int deletedPosition = taskListToDelete.getPosition();
        taskListRepository.deleteById(id);

        List<TaskList> taskListsToUpdate = taskListRepository.findByBoardIdAndPositionGreaterThan(
                taskListToDelete.getBoard().getId(), deletedPosition);

        taskListsToUpdate.forEach(taskList -> taskList.setPosition(taskList.getPosition() - 1));

        taskListRepository.saveAll(taskListsToUpdate);
        return !taskListRepository.existsById(id);
//        taskListRepository.deleteById(id);
//        return !taskListRepository.existsById(id);
    }

    @Override
    public List<TaskList> getAllByBoard(Board board) {
        return taskListRepository.findByBoard(board);
    }

    @Override
    public Integer getNewPosition(Long aLong) {
        return taskListRepository.countByBoard_Id(aLong);
    }

    @Transactional
    @Override
    public boolean changePosition(Long id, Integer newPosition){
        Optional<TaskList> optionalTaskList=taskListRepository.findById(id);
        if(optionalTaskList.isPresent() && !optionalTaskList.get().getPosition().equals(newPosition)){
            TaskList taskListToMove=optionalTaskList.get();
            Board board = taskListToMove.getBoard();
            int currentPosition = taskListToMove.getPosition();

            if (newPosition > currentPosition) {
                List<TaskList> taskListsToUpdate = taskListRepository.findByBoardIdAndPositionBetween(
                        board.getId(), currentPosition + 1, newPosition);

                taskListsToUpdate.forEach(taskList -> taskList.setPosition(taskList.getPosition() - 1));
                taskListRepository.saveAll(taskListsToUpdate);
            } else if (newPosition < currentPosition) {
                List<TaskList> taskListsToUpdate = taskListRepository.findByBoardIdAndPositionBetween(
                        board.getId(), newPosition, currentPosition - 1);

                taskListsToUpdate.forEach(taskList -> taskList.setPosition(taskList.getPosition() + 1));
                taskListRepository.saveAll(taskListsToUpdate);
            }

            taskListToMove.setPosition(newPosition);
            taskListRepository.save(taskListToMove);
            return true;
        }

        return false;
    }

    @Override
    public Long getBoardIdByTaskListId(Long taskListId) {
        TaskList taskList=taskListRepository.findById(taskListId).orElseThrow();
        return taskList.getBoard().getId();
    }


    public HashMap<Long,Integer> getMapIdPosition(Long boardId){
        List list=taskListRepository.findByBoard_Id(boardId);
        return (HashMap<Long, Integer>) list.stream()
                .collect(Collectors.toMap(TaskList::getId, TaskList::getPosition));
    }
}
