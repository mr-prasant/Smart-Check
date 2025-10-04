package com.smartcheck.util;

import com.smartcheck.dto.UserDetailResponseDTO;
import com.smartcheck.entity.User;

public class UserToUserDetailResponse {

    public static UserDetailResponseDTO toUserDetailResponse(User user) {
        if (user == null) return null;

        UserDetailResponseDTO responseDTO = new UserDetailResponseDTO();
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhone(user.getPhone());
        responseDTO.setGender(user.getGender());
        responseDTO.setRole(user.getRole());

        return responseDTO;
    }
}