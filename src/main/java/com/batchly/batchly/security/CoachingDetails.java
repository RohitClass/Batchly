package com.batchly.batchly.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

public class CoachingDetails implements UserDetails {

    private Long id;
    private Long user_id;
    private String email;
    private String user_name;
    private String phone_no;
    private String password;
    private String token;
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Collection<? extends GrantedAuthority> authorities;

    // ✅ REQUIRED: No-args constructor
    public CoachingDetails() {
    }

    // Existing constructor
    public CoachingDetails(
            Long id,
            Long user_id,
            String email,
            String user_name,
            String phone_no,
            String password,
            String token,
            String role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.user_id = user_id;
        this.email = email;
        this.user_name = user_name;
        this.phone_no = phone_no;
        this.password = password;
        this.token = token;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorities = authorities;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return user_name;
    }

    public String getPhoneNo() {
        return phone_no;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.user_id = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public void setPhoneNo(String phone_no) {
        this.phone_no = phone_no;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // UserDetails methods
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities != null ? authorities : java.util.Collections.emptyList();
    }

   public void setAuthorities(Collection<? extends GrantedAuthority> authorities){
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}