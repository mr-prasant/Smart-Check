package com.smartcheck.util;

import com.smartcheck.dto.UserRegisterRequestDTO;
import com.smartcheck.entity.User;

public class UserRequestToUser {

    public static User toUser(UserRegisterRequestDTO userDTO) {
        if (userDTO == null) return null;

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setGender(userDTO.getGender());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        return user;
    }
}
