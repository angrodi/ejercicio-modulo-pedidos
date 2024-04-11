package com.example.pedidoservice.model.repository;

import com.example.pedidoservice.model.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {
}
