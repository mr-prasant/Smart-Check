package com.smartcheck.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String testid;

    @NotNull
    private String createdBy;

    @NotNull
    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime startsAt;

    private LocalDateTime expiredAt;

    @NotNull
    private LocalDateTime registeredBy;

    private int duration;

    public Test() {
    }

    public Test(Long id, String testid, String createdBy, String title, String description, LocalDateTime createdAt, LocalDateTime startsAt, LocalDateTime expiredAt, LocalDateTime registeredBy, int duration) {
        this.id = id;
        this.testid = testid;
        this.createdBy = createdBy;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.startsAt = startsAt;
        this.expiredAt = expiredAt;
        this.registeredBy = registeredBy;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public LocalDateTime getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(LocalDateTime registeredBy) {
        this.registeredBy = registeredBy;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
