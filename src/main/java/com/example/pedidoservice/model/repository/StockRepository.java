package com.example.pedidoservice.model.repository;

import com.example.pedidoservice.model.entity.Producto;
import com.example.pedidoservice.model.entity.StockResumen;
import com.example.pedidoservice.model.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockResumen, Integer> {

    Optional<StockResumen> findByProductoAndSucursal(Producto producto, Sucursal sucursal);

}
