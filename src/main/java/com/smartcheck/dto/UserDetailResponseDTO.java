package com.smartcheck.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserDetailResponseDTO {
    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;
    private String phone;
    private Character gender;

    @NotNull
    private String role;

    public UserDetailResponseDTO() {
    }

    public UserDetailResponseDTO(String name, String email, String phone, Character gender, String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
