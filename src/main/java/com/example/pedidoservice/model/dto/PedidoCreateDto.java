package com.example.pedidoservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class PedidoCreateDto {

    @NotNull
    private Integer vendedorId;

    @NotNull
    private Integer sucursalId;

    @NotNull
    private Integer formaPagoId;

    @NotNull
    private Integer diasCredito;

    @NotNull
    private String observaciones;

    @NotNull
    private Integer idCliente;

    @NotNull
    private List<PedidoItemCreateDto> items;

}
