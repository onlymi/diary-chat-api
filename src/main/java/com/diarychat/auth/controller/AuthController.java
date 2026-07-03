package com.diarychat.auth.controller;

import com.diarychat.auth.dto.SignupRequest;
import com.diarychat.auth.dto.SignupResponse;
import com.diarychat.auth.service.SignupService;
import com.diarychat.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "회원가입 및 로그인 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final SignupService signupService;
    
    public AuthController(SignupService signupService) {
        this.signupService = signupService;
    }

    @Operation(
            summary = "회원가입",
            description = "새로운 사용자를 생성합니다."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "회원가입 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SignupResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "입력값 검증 실패",
                    value = """
                        {
                          "code": "INVALID_REQUEST",
                          "message": "요청 값이 올바르지 않습니다.",
                          "errors": {
                            "email": "올바른 형식의 이메일 주소여야 합니다",
                            "password": "크기가 8에서 72 사이여야 합니다"
                          }
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "아이디 또는 이메일 중복",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "아이디 중복",
                    value = """
                        {
                          "code": "DUPLICATE_USER",
                          "message": "이미 사용 중인 userId입니다.",
                          "errors": {
                            "userId": "이미 사용 중인 userId입니다."
                          }
                        }
                        """
                )
            )
        )
    })
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = signupService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
