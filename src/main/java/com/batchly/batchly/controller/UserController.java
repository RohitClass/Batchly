package com.batchly.batchly.controller;

import com.batchly.batchly.dto.ApiResponse;
import com.batchly.batchly.repository.CoachingRepository;
import com.batchly.batchly.security.CoachingDetails;
import com.batchly.batchly.security.CustomUserDetails;

// import com.batchly.batchly.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
@Autowired
private CoachingRepository coachingRepository;
    @GetMapping("/details")
    public ResponseEntity<ApiResponse<Object>> getUserDetails() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object userDetails = authentication.getPrincipal();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User data fetched successfully", userDetails));
    }

    @GetMapping("/coaching")
public ResponseEntity<ApiResponse<CoachingDetails>> getCoaching() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    Long userId = userDetails.getId();

   CoachingDetails coachingDetails = coachingRepository.findByUserId(userId)
        .orElseThrow(() -> new RuntimeException("Coaching not found"));
    return ResponseEntity.ok(
            new ApiResponse<>(true, "Coaching data fetched successfully", coachingDetails)
    );
}
}