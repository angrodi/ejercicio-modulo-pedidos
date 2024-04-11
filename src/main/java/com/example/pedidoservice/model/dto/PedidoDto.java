package com.example.pedidoservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {


    private Integer id;
    private String codigo;
    private VendedorDto vendedor;
    private FormaPagoDto formaPago;
    private SucursalDto sucursal;
    private Integer diasCredito;
    private String observaciones;
    private LocalDateTime fechaEmision;
    private Double totalGravado;
    private Double totalIgv;
    private Double total;
    private EstadoPedidoDto estadoPedido;
    private LocalDateTime fechaVencimiento;
    private ClienteDto cliente;
    private List<PedidoItemDto> items = new ArrayList<>();

}
