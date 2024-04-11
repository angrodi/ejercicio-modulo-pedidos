package com.example.pedidoservice.model.dto;

import com.example.pedidoservice.model.entity.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItemDto {

    private Integer id;
    private Producto producto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subTotal;

}
