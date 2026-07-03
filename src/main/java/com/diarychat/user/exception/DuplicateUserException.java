package com.diarychat.user.exception;

public class DuplicateUserException extends RuntimeException {

    private final String field;

    public DuplicateUserException(String field) {
        super("이미 사용 중인 " + field + "입니다.");
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
