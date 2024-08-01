package com.zelenenkyi.tasktracker.security.filter;

import com.zelenenkyi.tasktracker.model.ERole;
import com.zelenenkyi.tasktracker.model.UserBoardRole;
import com.zelenenkyi.tasktracker.repository.UserBoardRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccessService {


    private UserBoardRoleRepository userBoardRoleRepository;

    public boolean hasAccess(Long userId, Long boardId, ERole requiredRoleName) {
        Optional<UserBoardRole> userBoardRole = userBoardRoleRepository.findByUserIdAndBoardId(userId, boardId);
        if (userBoardRole.isPresent()) {
            ERole roleName = userBoardRole.get().getRole().getName();
            return roleName == requiredRoleName || (roleName == ERole.ROLE_ADMIN && requiredRoleName != ERole.ROLE_USER);
        }
        return false;
    }

    public ERole whatRole(Long userId, Long boardId){
        Optional<UserBoardRole> userBoardRole = userBoardRoleRepository.findByUserIdAndBoardId(userId, boardId);
        if (userBoardRole.isPresent()) {
            ERole roleName = userBoardRole.get().getRole().getName();
            return roleName;
        }
        return null;
    }
}
