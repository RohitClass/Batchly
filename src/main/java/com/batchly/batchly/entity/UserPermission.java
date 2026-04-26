package com.batchly.batchly.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_permission")
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long permission_id;
    private Long user_id;
    @CreationTimestamp
    @Column(name = "created_at" , updatable = false)
    private LocalDateTime createdAt;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPermissionId() { return this.permission_id; }
    public void setPermissionId(Long permission_id) { this.permission_id = permission_id; }
     public Long getUserId() { return this.user_id; }
    public void setUserId(Long user_id) { this.user_id = user_id; }
     public LocalDateTime getCreatedAt() { return this.createdAt; }
}