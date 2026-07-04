package com.diarychat.auth.docs;

public final class AuthApiExamples {

    public static final String INVALID_REQUEST = """
            {
              "code": "INVALID_REQUEST",
              "message": "요청 값이 올바르지 않습니다.",
              "errors": {
                "email": "올바른 형식의 이메일 주소여야 합니다",
                "password": "크기가 8에서 72 사이여야 합니다"
              }
            }
            """;

    public static final String DUPLICATE_USER = """
            {
              "code": "DUPLICATE_USER",
              "message": "이미 사용 중인 loginId입니다.",
              "errors": {
                "loginId": "이미 사용 중인 loginId입니다."
              }
            }
            """;

    public static final String LOGIN_INVALID_REQUEST = """
            {
              "code": "INVALID_REQUEST",
              "message": "요청 값이 올바르지 않습니다.",
              "errors": {
                "loginId": "아이디는 필수입니다.",
                "password": "비밀번호는 필수입니다."
              }
            }
            """;

    public static final String INVALID_CREDENTIALS = """
            {
              "code": "INVALID_CREDENTIALS",
              "message": "아이디 또는 비밀번호가 올바르지 않습니다.",
              "errors": {}
            }
            """;

    private AuthApiExamples() {
    }
}
