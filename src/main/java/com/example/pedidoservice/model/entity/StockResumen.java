package com.example.pedidoservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_resumen")
@Data @AllArgsConstructor @NoArgsConstructor
public class StockResumen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idSucursal")
    private Sucursal sucursal;

    @Column(name = "cantidadDisponible")
    private Integer cantidadDisponible;

    @Column(name = "cantidadEnPedido")
    private Integer cantidadEnPedido;

}
