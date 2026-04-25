package com.batchly.batchly.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private Long roleId;
}