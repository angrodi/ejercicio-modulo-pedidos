package com.example.pedidoservice.model.repository;

import com.example.pedidoservice.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
