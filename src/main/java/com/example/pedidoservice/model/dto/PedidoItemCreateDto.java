package com.example.pedidoservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PedidoItemCreateDto {

    @NotNull
    private Integer productoId;

    @NotNull
    private Integer cantidad;

    @NotNull
    private Double precioUnitario;

}
