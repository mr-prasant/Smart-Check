package com.smartcheck.service;

import com.smartcheck.dto.UserRegisterRequestDTO;
import com.smartcheck.dto.UserDetailResponseDTO;
import com.smartcheck.entity.RefreshToken;
import com.smartcheck.entity.User;
import com.smartcheck.exception.NoUniqueResourceException;
import com.smartcheck.exception.ResourceNotFoundException;
import com.smartcheck.repository.UserRepository;
import com.smartcheck.util.JWTUtil;
import com.smartcheck.util.UserRequestToUser;
import com.smartcheck.util.UserToUserDetailResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final JWTUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public UserService(JWTUtil jwtUtil, PasswordEncoder encoder, UserRepository userRepository, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public UserDetailResponseDTO registerUser(UserRegisterRequestDTO userDTO) {
        if (userDTO == null) {
            throw new ResourceNotFoundException("UserService.registerUser(): User Data not fetched!");
        }

        userDTO.setEmail(userDTO.getEmail().toLowerCase());
        if (this.userRepository.existsByEmail(userDTO.getEmail())) {
            throw new NoUniqueResourceException("UserService.registerUser(): Email already exist");
        }

        String role = userDTO.getRole();
        if (role == null || (!role.equalsIgnoreCase("ROLE_USER") && !role.equalsIgnoreCase("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("registerUser(): Invalid role!");
        }

        userDTO.setPassword(encoder.encode(userDTO.getPassword().trim()));
        User user = this.userRepository.save(UserRequestToUser.toUser(userDTO));
        return UserToUserDetailResponse.toUserDetailResponse(user);
    }

    public UserDetailResponseDTO getUser(String email) {
        return UserToUserDetailResponse.toUserDetailResponse(
                this.userRepository.findByEmail(email).orElseThrow(
                        () -> new ResourceNotFoundException("UserService.getUser(): Invalid email!")
                )
        );
    }

    public String generateToken(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Invalid email or password!")
        );

        return this.jwtUtil.generateToken(email, List.of(user.getRole()));
    }

    public String generateRefreshToken(String email) {
        return this.refreshTokenService.createRefreshToken(email);
    }

    public String refreshToken(String refreshToken) {
        RefreshToken dbRefreshToken = refreshTokenService.validateToken(refreshToken);
        return generateToken(dbRefreshToken.getEmail());
    }
}
