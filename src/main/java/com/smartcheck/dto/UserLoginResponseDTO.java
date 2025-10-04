package com.smartcheck.dto;

public class UserLoginResponseDTO {
    private String jwtToken;
    private String refreshToken;

    public UserLoginResponseDTO() {
    }

    public UserLoginResponseDTO(String jwtToken, String refreshToken) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
