package com.zelenenkyi.tasktracker.repository;

import com.zelenenkyi.tasktracker.model.Task;
import com.zelenenkyi.tasktracker.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Override
    Optional<Task> findById(Long aLong);

    @Override
    List<Task> findAll();

    List<Task> findByTaskList(TaskList taskList);

    Integer countByTaskList_Id(Long id);

    List<Task> findByTaskListIdAndPositionBetween(Long id, int i, Integer destinationIndex);

    List<Task> findByTaskList_IdAndPositionGreaterThan(Long id, Integer position);

    List<Task> findByTaskList_IdAndPositionGreaterThanEqual(Long id, Integer position);

    List findByTaskListId(Long taskList_id);
}