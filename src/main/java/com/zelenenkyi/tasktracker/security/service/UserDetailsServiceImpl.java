package com.zelenenkyi.tasktracker.security.service;

import com.zelenenkyi.tasktracker.model.User;
import com.zelenenkyi.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username).
                orElseThrow(()->new UsernameNotFoundException("User not found"));
        return UserDetailsImpl.build(user);
    }
}
