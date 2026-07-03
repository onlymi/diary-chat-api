package com.diarychat.auth.dto;

import com.diarychat.user.entity.User;

public record SignupResponse(
        Long id,
        String userId,
        String email,
        String nickname,
        String phoneNumber
) {
    public static SignupResponse from(User user) {
        return new SignupResponse(
                user.getId(),
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getPhoneNumber()
        );
    }
}
