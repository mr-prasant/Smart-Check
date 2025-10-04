package com.smartcheck.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateTestTimeDTO {
    @NotNull
    private String createdBy;

    @NotNull
    private String testid;

    private String startsAt; // eg "02-10-2025 10:30:00 AM"

    private String registeredBy; // eg "02-10-2025 10:30:00 AM"

    private int expiredAfter; // in hours

    public UpdateTestTimeDTO() {
    }

    public UpdateTestTimeDTO(String createdBy, String testid, String startsAt, String registeredBy, int expiredAfter) {
        this.createdBy = createdBy;
        this.testid = testid;
        this.startsAt = startsAt;
        this.registeredBy = registeredBy;
        this.expiredAfter = expiredAfter;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public int getExpiredAfter() {
        return expiredAfter;
    }

    public void setExpiredAfter(int expiredAfter) {
        this.expiredAfter = expiredAfter;
    }
}
