package com.diarychat.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @Schema(description = "로그인 아이디", example = "seungmin")
        @NotBlank
        @Size(min = 4, max = 30)
        @Pattern(regexp = "^[a-zA-Z0-9_]+$")
        String userId,

        @Schema(description = "이메일", example = "test@example.com")
        @NotBlank
        @Email
        @Size(max = 255)
        String email,

        @Schema(description = "비밀번호", example = "12345678", format = "password")
        @NotBlank
        @Size(min = 8, max = 72)
        String password,

        @Schema(description = "닉네임", example = "승민")
        @NotBlank
        @Size(min = 2, max = 20)
        String nickname,

        @Schema(description = "휴대폰 번호", example = "010-1234-5678")
        @NotBlank
        @Pattern(regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$")
        String phoneNumber
) {
}
