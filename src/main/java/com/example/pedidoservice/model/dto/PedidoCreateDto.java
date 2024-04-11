package com.example.pedidoservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class PedidoCreateDto {

    private Integer vendedorId;
    private Integer sucursalId;
    private Integer formaPagoId;
    private Integer diasCredito;
    private String observaciones;
    private Integer idCliente;
    private List<PedidoItemCreateDto> items;

}
