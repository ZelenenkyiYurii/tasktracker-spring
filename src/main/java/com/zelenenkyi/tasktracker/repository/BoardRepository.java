package com.zelenenkyi.tasktracker.repository;

import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "board_entity-graph")
    Optional<Board> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    List<Board> findAll();

    @Override
    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("update Board b set b.title = ?1 where b.id = ?2")
    void updateTitleById(@NonNull String title, Long id);

    List<Board> findByUserBoardRoles_User(User user);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "board_entity-graph")
    List<Board> findByUserBoardRoles_User_Username(String username);

    @Override
    <S extends Board> S save(S entity);

    Optional<Board> findFirstByTaskLists_Id(Long id);
}