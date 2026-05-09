package com.batchly.batchly.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String phone_no;
    private String password;
}