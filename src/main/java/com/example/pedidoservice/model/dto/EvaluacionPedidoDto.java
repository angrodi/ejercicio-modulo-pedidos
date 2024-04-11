package com.example.pedidoservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionPedidoDto {

    @NotNull
    private String comentarios;

    @NotNull
    private String resultado;

}
