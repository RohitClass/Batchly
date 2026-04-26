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
@Table(name="modules")
public class Module {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
@CreationTimestamp
@Column(name = "created_at",updatable = false)
private LocalDateTime createdAt;
public Long getId(){
    return this.id;
}
public void setId(Long id){
    this.id=id;
}
public String getName(){
    return this.name;
}
public void setName(String name){
    this.name=name;
}
public LocalDateTime getcreatedAt(){
    return this.createdAt;
}
}
