package com.example.cafeinventory.data.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.Instant;

@Getter
@MappedSuperclass
public abstract class AuditableEntity {

    @Column(name="created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name="updated_at", nullable = false)
    private Instant updatedAt;

    //Gets the timestamp whenever there is a new entry for an entity
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    //Gets the timestamp whenever the entry is updated
    //Assists with restocking purpose
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
