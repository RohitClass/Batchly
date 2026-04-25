package com.batchly.batchly.service;

import com.batchly.batchly.dto.LoginRequest;
import com.batchly.batchly.dto.LoginResponse;
import com.batchly.batchly.dto.RegisterRequest;
import com.batchly.batchly.repository.RoleRepository;
import com.batchly.batchly.repository.UserRepository;
import com.batchly.batchly.security.CustomUserDetailsService;
import com.batchly.batchly.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public LoginResponse login(LoginRequest req) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(req.getEmail());
        String token = jwtUtil.generateToken(user);

        List<String> perms = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        String role = perms.stream()
            .filter(p -> p.startsWith("ROLE_"))
            .findFirst().orElse("");

        return new LoginResponse(token, role, perms);
    }

    public void register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        roleRepo.findById(req.getRoleId())
            .orElseThrow(() -> new RuntimeException("Role not found"));

        userRepo.save(req.getEmail(),
            passwordEncoder.encode(req.getPassword()),
            req.getRoleId());
    }
}