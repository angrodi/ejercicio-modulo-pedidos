package com.example.pedidoservice.service;

import com.example.pedidoservice.model.dto.EvaluacionPedidoDto;
import com.example.pedidoservice.model.dto.PedidoCreateDto;
import com.example.pedidoservice.model.dto.PedidoPendienteEvaluacionDto;
import com.example.pedidoservice.model.entity.Pedido;
import com.example.pedidoservice.model.entity.Usuario;

import java.util.List;

public interface PedidoService {

    Pedido crearPedido(PedidoCreateDto pedidoDto);
    Pedido prepararEvaluacionPedido(Integer pedidoId);
    List<PedidoPendienteEvaluacionDto> listarPedidosPendientesEvaluacion(Integer usuarioId);
    Pedido evaluarPedido(Integer pedidoId, Integer evaluadorId, EvaluacionPedidoDto evaluacion);

}
