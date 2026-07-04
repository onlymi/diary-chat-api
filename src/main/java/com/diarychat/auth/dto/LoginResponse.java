package com.diarychat.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(
                description = "API 인증에 사용하는 액세스 토큰",
                example = "eyJhbGciOiJIUzI1NiJ9.access-token"
        )
        String accessToken,

        @Schema(
                description = "액세스 토큰 재발급에 사용하는 리프레시 토큰",
                example = "eyJhbGciOiJIUzI1NiJ9.refresh-token"
        )
        String refreshToken
) {
}
