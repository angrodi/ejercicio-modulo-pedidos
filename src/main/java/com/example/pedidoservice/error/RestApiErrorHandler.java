package com.example.pedidoservice.error;

import com.example.pedidoservice.error.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class RestApiErrorHandler {

    @ExceptionHandler({ InvalidInputException.class })
    public ResponseEntity<ApiError> handleInvalidInputException(HttpServletRequest request, Exception ex, Locale locale) {
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NoEstadoBorradorException.class, NoEstadoPendienteException.class })
    public ResponseEntity<ApiError> handleInvalidEstadoPedidoException(HttpServletRequest request, Exception ex, Locale locale) {
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ApiError> handleNotFoundException(HttpServletRequest request, Exception ex, Locale locale) {
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ OutOfStockException.class })
    public ResponseEntity<ApiError> handleOutOfStockException(HttpServletRequest request, Exception ex, Locale locale) {
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ApiError> handleValidationsException(HttpServletRequest request, MethodArgumentNotValidException ex, Locale locale) {
        ApiError error = new ApiError();

        List<ValidationError> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();

            ValidationError validationError = new ValidationError(fieldName, errorMessage);
            errors.add(validationError);
        });

        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setMessage("Validación fallida");
        error.setDetails(errors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
