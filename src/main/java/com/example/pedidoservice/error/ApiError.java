package com.example.pedidoservice.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ApiError {

    private HttpStatus status;
    private LocalDateTime date = LocalDateTime.now();
    private String message;
    private List<ValidationError> details;

}
