package com.batchly.batchly.controller;

import com.batchly.batchly.dto.ApiResponse;
import com.batchly.batchly.dto.LoginRequest;
import com.batchly.batchly.dto.LoginResponse;
import com.batchly.batchly.dto.RegisterRequest;
// import com.batchly.batchly.dto.RegisterRequest;
import com.batchly.batchly.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest req) {
        LoginResponse response = authService.login(req);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Login successful", response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody RegisterRequest req) {
       boolean response= authService.register(req);
       if(response)
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User registered successfully", null));
        return ResponseEntity.badRequest().body(
            new ApiResponse<>(false, "User already exist.", null)
    );
    }
}