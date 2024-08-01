package com.zelenenkyi.tasktracker.security.filter;

import com.zelenenkyi.tasktracker.model.ERole;
import com.zelenenkyi.tasktracker.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class BoardAccessFilter extends OncePerRequestFilter {
    @Autowired
    private  AccessService accessService;
    @Autowired
    private  UserServiceImpl userService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String[] uriParts = requestURI.split("/");
        if (uriParts.length > 2 && uriParts[1].equals("boards")) {
            Long boardId = Long.parseLong(uriParts[2]);
            ERole requiredRole = ERole.ROLE_USER;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
                String username = ((UserDetails) authentication.getPrincipal()).getUsername();
                Long userId = getUserIdByUsername(username);
                if (!accessService.hasAccess(userId, boardId, requiredRole)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Full authentication is required to access this resource");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private Long getUserIdByUsername(String username) {
        // Implement this method to get user ID by username
        return userService.findIdByUsername(username);
    }

    private ERole getRequiredRole(String[] uriParts) {
        if (uriParts.length > 3 && uriParts[3].equals("edit")) {
            return ERole.ROLE_MODERATOR;
        }
        return ERole.ROLE_USER;
    }

}
