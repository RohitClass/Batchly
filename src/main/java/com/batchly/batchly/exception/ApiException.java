package com.batchly.batchly.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}