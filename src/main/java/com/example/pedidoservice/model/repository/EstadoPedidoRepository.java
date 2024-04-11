package com.example.pedidoservice.model.repository;

import com.example.pedidoservice.model.entity.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Integer> {

    Optional<EstadoPedido> findByDescripcion(String descripcion);

}
