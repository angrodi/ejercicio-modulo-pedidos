package com.example.pedidoservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orden_pedido_item")
@Data @AllArgsConstructor @NoArgsConstructor
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "productoId")
    private Producto producto;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precioUnitario")
    private Double precioUnitario;

    @Column(name = "subTotal")
    private Double subTotal;

    @ManyToOne
    @JoinColumn(name = "pedidoId", nullable = false, updatable = false)
    private Pedido pedido;

}
