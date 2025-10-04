package com.smartcheck.dto;

public class TestCreationResponseDTO {
    private String testid, createdBy;

    public TestCreationResponseDTO() {
    }

    public TestCreationResponseDTO(String testid, String createdBy) {
        this.testid = testid;
        this.createdBy = createdBy;
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
}
