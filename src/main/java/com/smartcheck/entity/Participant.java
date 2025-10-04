package com.smartcheck.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testid;
    private String pid;
    private Boolean testGiven;
    private int totalMarks;
    private LocalDateTime givenAt;
    private LocalDateTime registeredAt;
    private Boolean checked;

    public Participant() {
    }

    public Participant(Long id, String testid, String pid, Boolean testGiven, int totalMarks, LocalDateTime givenAt, LocalDateTime registeredAt, Boolean checked) {
        this.id = id;
        this.testid = testid;
        this.pid = pid;
        this.testGiven = testGiven;
        this.totalMarks = totalMarks;
        this.givenAt = givenAt;
        this.registeredAt = registeredAt;
        this.checked = checked;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Boolean getTestGiven() {
        return testGiven;
    }

    public void setTestGiven(Boolean testGiven) {
        this.testGiven = testGiven;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public LocalDateTime getGivenAt() {
        return givenAt;
    }

    public void setGivenAt(LocalDateTime givenAt) {
        this.givenAt = givenAt;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

}
