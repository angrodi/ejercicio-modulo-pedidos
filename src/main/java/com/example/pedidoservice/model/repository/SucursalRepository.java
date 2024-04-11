package com.example.pedidoservice.model.repository;

import com.example.pedidoservice.model.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
}
