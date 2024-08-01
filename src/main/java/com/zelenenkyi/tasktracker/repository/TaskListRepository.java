package com.zelenenkyi.tasktracker.repository;

import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    @Override
    <S extends TaskList> S save(S entity);

    @Override
    Optional<TaskList> findById(Long aLong);

    List<TaskList> findByBoard(Board board);

    Integer  countByBoard_Id(Long id);
}