package com.example.pedidoservice.controller;

import com.example.pedidoservice.error.exceptions.InvalidInputException;
import com.example.pedidoservice.model.dto.EvaluacionPedidoDto;
import com.example.pedidoservice.model.dto.PedidoCreateDto;
import com.example.pedidoservice.model.dto.PedidoDto;
import com.example.pedidoservice.model.dto.PedidoPendienteEvaluacionDto;
import com.example.pedidoservice.model.dto.mapper.PedidoMapper;
import com.example.pedidoservice.model.entity.Pedido;
import com.example.pedidoservice.service.PedidoService;
import com.example.pedidoservice.utils.PedidoUtils;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<PedidoDto> crearPedido(@Valid @RequestBody PedidoCreateDto body) {
        Pedido pedido = this.pedidoService.crearPedido(body);
        PedidoDto data = this.pedidoMapper.pedidoToPedidoDto(pedido);

        return ResponseEntity.ok(data);
    }

    @PutMapping(
            value = "prepararevaluacion/{pedidoId}",
            produces = "application/json"
    )
    public ResponseEntity<PedidoDto> prepararEvaluacionPedido(@PathVariable Integer pedidoId) {
        Pedido pedido = this.pedidoService.prepararEvaluacionPedido(pedidoId);
        PedidoDto data = this.pedidoMapper.pedidoToPedidoDto(pedido);

        return ResponseEntity.ok(data);
    }

    @GetMapping(
            value = "listarpendientes",
            produces = "application/json"
    )
    public ResponseEntity<List<PedidoPendienteEvaluacionDto>> listarPedidosPendientesEvaluacion(@RequestParam Integer usuarioId) {
        List<PedidoPendienteEvaluacionDto> data = this.pedidoService.listarPedidosPendientesEvaluacion(usuarioId);

        return ResponseEntity.ok(data);
    }

    @PostMapping(
            value = "evaluarpedido/{pedidoId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<PedidoDto> evaluarPedido(@PathVariable Integer pedidoId, @RequestParam Integer usuarioId, @RequestBody EvaluacionPedidoDto body) {
        if (!PedidoUtils.esResultadoValido(body.getResultado())) {
            throw new InvalidInputException("Valor no válido para resultado");
        }

        Pedido pedido = this.pedidoService.evaluarPedido(pedidoId, usuarioId, body);
        PedidoDto data = this.pedidoMapper.pedidoToPedidoDto(pedido);

        return ResponseEntity.ok(data);
    }

}
