package com.smartcheck.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class TestCreationRequestDTO {

    private String createdBy;

    @NotNull
    private String title;

    private String description;

    @NotNull
    private String startsAt; // eg "02-10-2025 10:30:00 AM"

    @NotNull
    private String registeredBy; // eg "02-10-2025 10:30:00 AM"

    private int expiredAfter; // in hours

    @NotNull
    private int duration; // in minutes

    public TestCreationRequestDTO() {
    }

    public TestCreationRequestDTO(String createdBy, String title, String description, String startsAt, String registeredBy, int expiredAfter, int duration) {
        this.createdBy = createdBy;
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
        this.registeredBy = registeredBy;
        this.expiredAfter = expiredAfter;
        this.duration = duration;
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

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public int getExpiredAfter() {
        return expiredAfter;
    }

    public void setExpiredAfter(int expiredAfter) {
        this.expiredAfter = expiredAfter;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
