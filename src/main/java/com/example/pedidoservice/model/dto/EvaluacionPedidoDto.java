package com.example.pedidoservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionPedidoDto {

    private String comentarios;
    private String resultado;

}
