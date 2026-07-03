package com.diarychat.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank
        @Size(min = 4, max = 30)
        @Pattern(regexp = "^[a-zA-Z0-9_]+$")
        String userId,

        @NotBlank
        @Email
        @Size(max = 255)
        String email,

        @NotBlank
        @Size(min = 8, max = 72)
        String password,

        @NotBlank
        @Size(min = 2, max = 20)
        String nickname,

        @NotBlank
        @Pattern(regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$")
        String phoneNumber
) {
}
