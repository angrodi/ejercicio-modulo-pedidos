package com.example.pedidoservice.error.exceptions;

public class NoEstadoPendienteException extends RuntimeException {
    public NoEstadoPendienteException() {}

    public NoEstadoPendienteException(String message) {
        super(message);
    }

    public NoEstadoPendienteException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEstadoPendienteException(Throwable cause) {
        super(cause);
    }
}