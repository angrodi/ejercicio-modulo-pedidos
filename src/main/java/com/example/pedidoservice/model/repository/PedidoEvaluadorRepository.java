package com.example.pedidoservice.model.repository;

import com.example.pedidoservice.model.entity.Pedido;
import com.example.pedidoservice.model.entity.PedidoEvaluador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoEvaluadorRepository extends JpaRepository<PedidoEvaluador, Integer> {

    List<PedidoEvaluador> findByPedido(Pedido pedido);

}
