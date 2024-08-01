package com.zelenenkyi.tasktracker.repository;

import com.zelenenkyi.tasktracker.model.ERole;
import com.zelenenkyi.tasktracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(ERole name);
}
