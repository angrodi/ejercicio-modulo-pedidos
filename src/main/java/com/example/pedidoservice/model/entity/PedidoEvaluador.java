package com.example.pedidoservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orden_pedido_evaluador")
@Data @AllArgsConstructor @NoArgsConstructor
public class PedidoEvaluador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ordenPedidoId")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @Column(name = "fechaEvaluacion")
    private LocalDateTime fechaEvaluacion;

    @Column(name = "comentarios")
    private String comentarios;

    @Column(name = "resultado")
    private String resultado;

}
