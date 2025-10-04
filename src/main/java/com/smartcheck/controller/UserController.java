package com.smartcheck.controller;

import com.smartcheck.dto.UserLoginRequestDTO;
import com.smartcheck.dto.UserLoginResponseDTO;
import com.smartcheck.dto.UserRegisterRequestDTO;
import com.smartcheck.dto.UserDetailResponseDTO;
import com.smartcheck.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/p/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authManager;

    public UserController(UserService userService, AuthenticationManager authManager) {
        this.userService = userService;
        this.authManager = authManager;
    }

    @GetMapping
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("health is good", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailResponseDTO> registerUser(@RequestBody UserRegisterRequestDTO userDTO) {
        return ResponseEntity.ok().body(userService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO requestDTO) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()));
        String token = this.userService.generateToken(requestDTO.getEmail());
        String refreshToken = this.userService.generateRefreshToken(requestDTO.getEmail());
        return ResponseEntity.ok().body(new UserLoginResponseDTO(token, refreshToken));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> getRefreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        return ResponseEntity.ok().body(Map.of("token", userService.refreshToken(refreshToken)));
    }
}
