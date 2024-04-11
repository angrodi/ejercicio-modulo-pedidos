package com.example.pedidoservice.error.exceptions;

public class NoEstadoBorradorException extends RuntimeException {
    public NoEstadoBorradorException() {}

    public NoEstadoBorradorException(String message) {
        super(message);
    }

    public NoEstadoBorradorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEstadoBorradorException(Throwable cause) {
        super(cause);
    }
}