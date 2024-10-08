package com.zelenenkyi.tasktracker.repository;

import com.zelenenkyi.tasktracker.model.RefreshToken;
import com.zelenenkyi.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
}
