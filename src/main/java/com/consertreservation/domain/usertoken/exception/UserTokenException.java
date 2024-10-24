package com.consertreservation.domain.usertoken.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserTokenException extends RuntimeException {

    private final UserTokenErrorCode errorCode;
    private final String message;

    public UserTokenException(UserTokenErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "[%s] %s".formatted(errorCode, message);
    }

    @JsonIgnore
    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @JsonIgnore
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @JsonIgnore
    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
