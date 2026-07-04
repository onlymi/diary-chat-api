package com.diarychat.global.error;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public record ErrorResponse(
        @Schema(description = "오류 코드")
        String code,

        @Schema(description = "오류 메시지")
        String message,

        @Schema(description = "필드별 오류 메시지")
        Map<String, String> errors
) {
}
