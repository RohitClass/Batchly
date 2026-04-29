package com.batchly.batchly.security;

import com.batchly.batchly.dto.ModulePermissionDTO;
import com.batchly.batchly.entity.User;
import com.batchly.batchly.repository.UserRepository;

// import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
public CustomUserDetails loadUserByUsername(String input) throws UsernameNotFoundException {

    User user = userRepository.findByEmailIgnoreCase(input)
            .or(() -> userRepository.findByUserName(input))
            .or(() -> userRepository.findByPhoneNo(input))
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new CustomUserDetails(
            user.getId(),
            user.getEmail(),
            user.getUserName(),
            user.getPhoneNo(),
            user.getPassword(),
            user.getCreateddAt(),
            user.getUpdatedAt(),
            new ArrayList<>()
    );
}

    // optional token update
    public void updateToken(String email, String token) {
        userRepository.findByEmailIgnoreCase(email).ifPresent(user -> {
            user.setToken(token);
            userRepository.save(email, token);
        });
    }

    public List<ModulePermissionDTO> getRoles(String email) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        List<ModulePermissionDTO> modules = userRepository.roles(email);

        if (modules == null || modules.isEmpty()) {
            throw new RuntimeException("No modules/permissions found for user");
        }

        return modules;
    }
}