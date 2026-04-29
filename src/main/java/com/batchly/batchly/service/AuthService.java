package com.batchly.batchly.service;

import com.batchly.batchly.dto.LoginRequest;
import com.batchly.batchly.dto.LoginResponse;
// import com.batchly.batchly.dto.LoginResponseDetails;
import com.batchly.batchly.dto.ModulePermissionDTO;
import com.batchly.batchly.security.CustomUserDetails;
// import com.batchly.batchly.repository.UserRepository;
import com.batchly.batchly.security.CustomUserDetailsService;
import com.batchly.batchly.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

        // private final UserRepository userRepo;
        private final AuthenticationManager authManager;
        private final JwtUtil jwtUtil;
        private final CustomUserDetailsService userDetailsService;

        public LoginResponse login(LoginRequest req) {

                // 🔥 ADD THIS BLOCK (password debug)
                UserDetails debugUser = userDetailsService.loadUserByUsername(req.getEmail());

                boolean match = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
                                .matches(req.getPassword(), debugUser.getPassword());

                System.out.println("Entered Password: " + req.getPassword());
                System.out.println("Stored Hash: " + debugUser.getPassword());
                System.out.println("Password Match: " + match);

                // 1. Authenticate
                Authentication authentication = authManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                req.getEmail(),
                                                req.getPassword()));

                // 2. Get authenticated user
                CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

                // 3. Generate token
                String token = jwtUtil.generateToken(user);

                // 4. (optional) store token
                userDetailsService.updateToken(req.getEmail(), token);
                List<ModulePermissionDTO> modules = userDetailsService.getRoles(req.getEmail());

                // 5. Return response
                return new LoginResponse(
                                user.getId(),
                                user.getEmail(),
                                user.getUserName(),
                                user.getPhoneNo(),
                                token,
                                user.getCreatedAt(),
                                user.getUpdatedAt(),
                                modules);
        }
}