package com.example.pedidoservice.model.repository;

import com.example.pedidoservice.model.entity.EstadoPedido;
import com.example.pedidoservice.model.entity.Pedido;
import com.example.pedidoservice.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("select p from Pedido p " +
            "inner join PedidoEvaluador pe on pe.pedido.id = p.id " +
            "inner join Usuario u on u.id = pe.usuario.id " +
            "where p.estadoPedido.descripcion = :estadoPedido and u.id = :usuarioId")
    List<Pedido> findByEstadoPedidoAndUsuario(String estadoPedido, Integer usuarioId);

}
