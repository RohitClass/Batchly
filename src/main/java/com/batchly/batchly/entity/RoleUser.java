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
@Table(name="role_user")
public class RoleUser {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private Long user_id;
private Long role_id;
@CreationTimestamp
@Column(name = "created_at",updatable = false)
private LocalDateTime createdAt;
public Long getId(){
    return this.id;
}
public void setId(Long id){
    this.id=id;
}
public Long getUserId(){
    return this.user_id;
}
public void setUserId(Long user_id){
    this.user_id=user_id;
}
public Long getRoleId(){
    return this.role_id;
}
public void setRoleId(Long role_id){
    this.role_id=role_id;
}
public LocalDateTime getcreatedAt(){
    return this.createdAt;
}
}
