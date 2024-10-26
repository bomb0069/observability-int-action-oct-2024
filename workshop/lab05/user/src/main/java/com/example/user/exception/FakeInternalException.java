package com.example.user.exception;

public class FakeInternalException extends RuntimeException {
    public FakeInternalException() {
        super();
    }

    public FakeInternalException(String message) {
        super(message);
    }
}
