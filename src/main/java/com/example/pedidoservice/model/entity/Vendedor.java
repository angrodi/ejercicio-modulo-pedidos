package com.example.pedidoservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendedor")
@Data @AllArgsConstructor @NoArgsConstructor
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombreCompleto")
    private String nombreCompleto;

}
