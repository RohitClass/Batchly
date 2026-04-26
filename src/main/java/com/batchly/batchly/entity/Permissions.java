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
@Table(name = "permissions")
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long module_id;
    private String name;
    private String permission_type;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Long getId(){
        return this.id;
    }

    public Long getModuleID() {
        return this.module_id;
    }

    public String getName() {
        return this.name;
    }

    public String getPermissionType() {
        return this.permission_type;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModuleId(Long module_id) {
        this.module_id = module_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermiossionType(String permission_type) {
        this.permission_type = permission_type;
    }

}