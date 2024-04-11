package com.example.pedidoservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orden_pedido")
@Data @AllArgsConstructor @NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo")
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "vendedorId")
    private Vendedor vendedor;

    @ManyToOne
    @JoinColumn(name = "formaPagoId")
    private FormaPago formaPago;

    @ManyToOne
    @JoinColumn(name = "sucursalId")
    private Sucursal sucursal;

    @Column(name = "diasCredito")
    private Integer diasCredito;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fechaEmision")
    private LocalDateTime fechaEmision;

    @Column(name = "totalGravado")
    private Double totalGravado;

    @Column(name = "totalIgv")
    private Double totalIgv;

    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JoinColumn(name = "estadoId")
    private EstadoPedido estadoPedido;

    @Column(name = "fechaVencimiento")
    private LocalDateTime fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pedido")
    private List<PedidoItem> items = new ArrayList<>();
}
