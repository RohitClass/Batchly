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
@Table(name = "coachings")
public class Coaching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", updatable = false)
    private LocalDateTime updatedAt;
    public Long getId() {
        return this.id;
    }
    public Long getUser_id() {
        return this.user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public LocalDateTime getCreatedAt()
    {
		return this.createdAt;
	}
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}
