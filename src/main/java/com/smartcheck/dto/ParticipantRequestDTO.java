package com.smartcheck.dto;

import jakarta.validation.constraints.NotNull;

public class ParticipantRequestDTO {
    @NotNull
    private String testid;

    private String pid;

    public ParticipantRequestDTO() {
    }

    public ParticipantRequestDTO(String testid, String pid) {
        this.testid = testid;
        this.pid = pid;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
