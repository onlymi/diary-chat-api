package com.diarychat.auth.dto;

import com.diarychat.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignupResponse(
        @Schema(description = "사용자 식별자", example = "1")
        Long id,

        @Schema(description = "로그인 아이디", example = "seungmin")
        String userId,

        @Schema(description = "이메일", example = "test@example.com")
        String email,

        @Schema(description = "닉네임", example = "승민")
        String nickname,

        @Schema(description = "휴대폰 번호", example = "010-1234-5678")
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
