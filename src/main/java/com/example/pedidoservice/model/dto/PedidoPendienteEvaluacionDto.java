package com.example.pedidoservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class PedidoPendienteEvaluacionDto {

    private String codigo;
    private LocalDateTime fechaEmision;
    private String nombreCompletoVendedor;
    private Double importeTotal;

}
