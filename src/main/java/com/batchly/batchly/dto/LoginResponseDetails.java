package com.batchly.batchly.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDetails {

    private Long id;
    private String email;
    private String userName;
    private String phoneNo;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ModulePermissionDTO> modules;
}