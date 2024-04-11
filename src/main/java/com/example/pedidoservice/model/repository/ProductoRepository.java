package com.example.pedidoservice.model.repository;

import com.example.pedidoservice.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
