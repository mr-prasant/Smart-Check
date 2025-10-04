package com.smartcheck.service;


import java.util.Date;
import java.util.UUID;

import com.smartcheck.entity.RefreshToken;
import com.smartcheck.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        super();
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createRefreshToken(String email) {
        refreshTokenRepository.deleteByEmail(email);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setEmail(email);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + (7L * 24L * 60L * 60L * 1000L)));

        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public RefreshToken validateToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new IllegalArgumentException("Invalid Refresh Token"));

        Date now = new Date();
        if (refreshToken.getExpiryDate().before(now)) {
            throw new IllegalArgumentException("Refresh Token is expired");
        }

        return refreshToken;
    }

}

