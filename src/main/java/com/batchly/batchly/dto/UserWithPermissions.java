package com.batchly.batchly.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
public class UserWithPermissions {

    private Long id;
    private String email;
    private String userName;
    private String phoneNo;
    private String password;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // No role, no permissions → empty authorities
    public Collection<? extends GrantedAuthority> toAuthorities() {
        return Collections.emptyList();
    }
}