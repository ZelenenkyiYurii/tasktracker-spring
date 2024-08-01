package com.zelenenkyi.tasktracker.repository;

import com.zelenenkyi.tasktracker.model.UserBoardRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBoardRoleRepository extends JpaRepository<UserBoardRole, Long> {
    Optional<UserBoardRole> findByUserIdAndBoardId(Long userId, Long boardId);
}