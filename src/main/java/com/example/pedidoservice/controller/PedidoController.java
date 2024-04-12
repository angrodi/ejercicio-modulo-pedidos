package com.example.pedidoservice.controller;

import com.example.pedidoservice.error.ApiError;
import com.example.pedidoservice.error.exceptions.InvalidInputException;
import com.example.pedidoservice.model.dto.EvaluacionPedidoDto;
import com.example.pedidoservice.model.dto.PedidoCreateDto;
import com.example.pedidoservice.model.dto.PedidoDto;
import com.example.pedidoservice.model.dto.PedidoPendienteEvaluacionDto;
import com.example.pedidoservice.model.dto.mapper.PedidoMapper;
import com.example.pedidoservice.model.entity.Pedido;
import com.example.pedidoservice.service.PedidoService;
import com.example.pedidoservice.utils.PedidoUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
@RequiredArgsConstructor
@Tag(name = "PedidoController", description =
        "REST API para la gestión pedidos.")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @Operation(
            summary = "${api.pedido-controller.crear-pedido.description}",
            description = "${api.pedido-controller.crear-pedido.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class), examples = {})),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<PedidoDto> crearPedido(@Valid @RequestBody PedidoCreateDto body) {
        Pedido pedido = this.pedidoService.crearPedido(body);
        PedidoDto data = this.pedidoMapper.pedidoToPedidoDto(pedido);

        return ResponseEntity.ok(data);
    }

    @Operation(
            summary = "${api.pedido-controller.preparar-evaluacion-pedido.description}",
            description = "${api.pedido-controller.preparar-evaluacion-pedido.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping(
            value = "prepararevaluacion/{pedidoId}",
            produces = "application/json"
    )
    public ResponseEntity<PedidoDto> prepararEvaluacionPedido(@PathVariable Integer pedidoId) {
        Pedido pedido = this.pedidoService.prepararEvaluacionPedido(pedidoId);
        PedidoDto data = this.pedidoMapper.pedidoToPedidoDto(pedido);

        return ResponseEntity.ok(data);
    }

    @Operation(
            summary = "${api.pedido-controller.listar-pendientes-evaluacion.description}",
            description = "${api.pedido-controller.listar-pendientes-evaluacion.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(
            value = "listarpendientes",
            produces = "application/json"
    )
    public ResponseEntity<List<PedidoPendienteEvaluacionDto>> listarPedidosPendientesEvaluacion(@RequestParam Integer usuarioId) {
        List<PedidoPendienteEvaluacionDto> data = this.pedidoService.listarPedidosPendientesEvaluacion(usuarioId);

        return ResponseEntity.ok(data);
    }

    @Operation(
            summary = "${api.pedido-controller.evaluar-pedido.description}",
            description = "${api.pedido-controller.evaluar-pedido.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
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
