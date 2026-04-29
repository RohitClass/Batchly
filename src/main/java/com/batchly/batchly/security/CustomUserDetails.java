package com.batchly.batchly.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String userName;
    private String phoneNo;
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(
            Long id,
            String email,
            String userName,
            String phoneNo,
            String password,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.phoneNo = phoneNo;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorities = authorities;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getUserName() { return userName; }
    public String getPhoneNo() { return phoneNo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}