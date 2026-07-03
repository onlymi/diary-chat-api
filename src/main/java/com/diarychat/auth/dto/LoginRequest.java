package com.diarychat.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest (
    @Schema(description = "로그인 아이디", example = "seungmin")
    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 4, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$")
    String userId,
    
    @Schema(description = "비밀번호", example = "12345678", format = "password")
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 72)
    String password
) {
}
