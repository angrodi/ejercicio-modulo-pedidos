package com.example.pedidoservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PedidoItemCreateDto {

    private Integer productoId;
    private Integer cantidad;
    private Double precioUnitario;

}
