package com.batchly.batchly.security;

import com.batchly.batchly.entity.User;
import com.batchly.batchly.repository.UserRepository;

// import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {

        User user = userRepository.findByEmailIgnoreCase(input)
                .or(() -> userRepository.findByUserName(input))
                .or(()-> userRepository.findByPhoneNo(input))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
 System.out.println("Entered Password (from request not available here)");
    System.out.println("Stored Password (DB): " + user.getPassword());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>() // ✅ no roles
        );
    }

    // optional token update
    public void updateToken(String email, String token) {
        userRepository.findByEmailIgnoreCase(email).ifPresent(user -> {
            user.setToken(token);
            userRepository.save(email,token);
        });
    }
}