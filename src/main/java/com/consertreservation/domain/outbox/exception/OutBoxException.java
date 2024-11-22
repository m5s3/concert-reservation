package com.consertreservation.domain.outbox.exception;

public class OutBoxException extends RuntimeException {

    private final OutBoxErrorCode errorCode;
    private final String message;

    public OutBoxException(OutBoxErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "[%s] %s".formatted(errorCode, message);
    }
}
