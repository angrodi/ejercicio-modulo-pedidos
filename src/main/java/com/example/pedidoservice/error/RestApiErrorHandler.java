package com.example.pedidoservice.error;

import com.example.pedidoservice.error.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class RestApiErrorHandler {

    @ExceptionHandler({ InvalidInputException.class })
    public ResponseEntity<ApiError> handleInvalidInputException(HttpServletRequest request, Exception ex, Locale locale) {
        ApiError error = new ApiError();
        error.setEstado(HttpStatus.BAD_REQUEST);
        error.setMensaje(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ NoEstadoBorradorException.class, NoEstadoPendienteException.class })
    public ResponseEntity<ApiError> handleInvalidEstadoPedidoException(HttpServletRequest request, Exception ex, Locale locale) {
        ApiError error = new ApiError();
        error.setEstado(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setMensaje(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ApiError> handleNotFoundException(HttpServletRequest request, Exception ex, Locale locale) {
        ApiError error = new ApiError();
        error.setEstado(HttpStatus.NOT_FOUND);
        error.setMensaje(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ OutOfStockException.class })
    public ResponseEntity<ApiError> handleOutOfStockException(HttpServletRequest request, Exception ex, Locale locale) {
        ApiError error = new ApiError();
        error.setEstado(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setMensaje(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
