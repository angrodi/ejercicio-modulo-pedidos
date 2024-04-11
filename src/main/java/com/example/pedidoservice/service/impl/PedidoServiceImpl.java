package com.example.pedidoservice.service.impl;

import com.example.pedidoservice.error.exceptions.*;
import com.example.pedidoservice.model.dto.EvaluacionPedidoDto;
import com.example.pedidoservice.model.dto.PedidoCreateDto;
import com.example.pedidoservice.model.dto.PedidoPendienteEvaluacionDto;
import com.example.pedidoservice.model.entity.*;
import com.example.pedidoservice.model.repository.*;
import com.example.pedidoservice.service.PedidoService;
import com.example.pedidoservice.utils.EstadoPedidoEnum;
import com.example.pedidoservice.utils.PedidoUtils;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Data
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final VendedorRepository vendedorRepository;
    private final FormaPagoRepository formaPagoRepository;
    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;
    private final StockRepository stockRepository;
    private final ClienteRepository clienteRepository;
    private final EstadoPedidoRepository estadoPedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoEvaluadorRepository pedidoEvaluadorRepository;

    private final List<Integer> DIAS_CREDITO = Arrays.asList(20, 60, 90);
    private final int OBSERVACIONES_MAX_LENGTH = 200;
    private final int COMENTARIOS_MAX_LENGTH = 200;
    private final double IGV = 0.18;

    @Override
    public Pedido crearPedido(PedidoCreateDto pedidoDto) {
        Pedido pedido = new Pedido();
        pedido.setCodigo(UUID.randomUUID().toString());
        pedido.setFechaEmision(LocalDateTime.now());

        // Estado
        EstadoPedido estado = estadoPedidoRepository.findByDescripcion(EstadoPedidoEnum.BORRADOR.getEstado())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado estado con la descripción: " + EstadoPedidoEnum.BORRADOR.getEstado()));
        pedido.setEstadoPedido(estado);

        // Vendedor
        Vendedor vendedor = vendedorRepository.findById(pedidoDto.getVendedorId())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado vendedor con el id: " + pedidoDto.getVendedorId()));
        pedido.setVendedor(vendedor);

        // Forma de Pago
        FormaPago formaPago = formaPagoRepository.findById(pedidoDto.getFormaPagoId())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado forma de pago con el id: " + pedidoDto.getFormaPagoId()));
        pedido.setFormaPago(formaPago);

        // Días de crédito
        if ( (formaPago.getId() == 1 && pedidoDto.getDiasCredito() != 0) || (formaPago.getId() != 1 && !DIAS_CREDITO.contains(pedido.getDiasCredito())) ) {
            throw new InvalidInputException("Valor de dias de crédito no válido");
        }
        pedido.setDiasCredito(pedidoDto.getDiasCredito());

        // Fecha Vencimiento
        pedido.setFechaVencimiento(PedidoUtils.agregarDias(pedido.getFechaEmision(), pedido.getDiasCredito()));

        // Sucursal
        Sucursal sucursal = sucursalRepository.findById(pedidoDto.getSucursalId())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado sucursal con el id: " + pedidoDto.getSucursalId()));
        pedido.setSucursal(sucursal);

        // Observaciones
        if (pedidoDto.getObservaciones().length() >= OBSERVACIONES_MAX_LENGTH) {
            throw new InvalidInputException("Observaciones no debe exceder los 200 caracteres");
        }
        pedido.setObservaciones(pedidoDto.getObservaciones());

        // Cliente
        Cliente cliente = clienteRepository.findById(pedidoDto.getIdCliente())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado cliente con el id: " + pedidoDto.getIdCliente()));
        pedido.setCliente(cliente);

        // Productos
        AtomicReference<Double> totalGravado = new AtomicReference<>((double) 0);

        pedidoDto.getItems()
                .forEach(productoDto -> {
                    Producto producto = productoRepository.findById(productoDto.getProductoId())
                            .orElseThrow(() -> new NotFoundException("No se ha encontrado producto con el id: " + productoDto.getProductoId()));

                    // Stock
                    StockResumen stock = stockRepository.findByProductoAndSucursal(producto, pedido.getSucursal())
                            .orElseThrow(() -> new NotFoundException("No se encontro información de stock para el producto con el id: " + productoDto.getProductoId()));

                    if (stock.getCantidadDisponible() < productoDto.getCantidad()) {
                        throw new OutOfStockException("Sin stock para el producto con el id: " + productoDto.getProductoId());
                    }

                    // Reserva de stock
                    stock.setCantidadDisponible(stock.getCantidadDisponible() - productoDto.getCantidad());
                    stock.setCantidadEnPedido(stock.getCantidadEnPedido() + productoDto.getCantidad());
                    stockRepository.save(stock);

                    // Pedido item
                    PedidoItem item = new PedidoItem();
                    item.setProducto(producto);
                    item.setCantidad(productoDto.getCantidad());
                    item.setPrecioUnitario(productoDto.getPrecioUnitario());
                    item.setSubTotal(PedidoUtils.redondeoBanquero(item.getCantidad() * item.getPrecioUnitario()));
                    item.setPedido(pedido);

                    totalGravado.updateAndGet(v -> v + item.getSubTotal());
                    pedido.getItems().add(item);
                });

        // Totales
        pedido.setTotalGravado(PedidoUtils.redondeoBanquero(totalGravado.get()));
        pedido.setTotalIgv(PedidoUtils.redondeoBanquero(pedido.getTotalGravado() * IGV));
        pedido.setTotal(PedidoUtils.redondeoBanquero(pedido.getTotalGravado() + pedido.getTotalIgv()));

        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido prepararEvaluacionPedido(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado pedido con el id: " + pedidoId));

        // Actualizar estado
        EstadoPedido estadoPendiente = estadoPedidoRepository.findByDescripcion(EstadoPedidoEnum.PENDIENTE_EVALUACION.getEstado())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado estado de pedido: " + EstadoPedidoEnum.PENDIENTE_EVALUACION.getEstado()));

        if (!pedido.getEstadoPedido().getDescripcion().equalsIgnoreCase(EstadoPedidoEnum.BORRADOR.getEstado())) {
            throw new NoEstadoBorradorException("El pedido no tiene el estado 'BORRADOR'");
        }

        pedido.setEstadoPedido(estadoPendiente);

        // Orden pedido evaluador
        List<Integer> ids = obtenerEvaluadores(pedido);

        usuarioRepository.findByIdIn(ids)
                .forEach(usuario -> {
                    PedidoEvaluador pedidoEvaluador = new PedidoEvaluador();
                    pedidoEvaluador.setPedido(pedido);
                    pedidoEvaluador.setUsuario(usuario);
                    pedidoEvaluadorRepository.save(pedidoEvaluador);
                });
        return pedidoRepository.save(pedido);
    }

    @Override
    public List<PedidoPendienteEvaluacionDto> listarPedidosPendientesEvaluacion(Integer evaluadorId) {
        if (!usuarioRepository.existsById(evaluadorId)) {
            throw new NotFoundException("No se ha encontrado usuario con el id: " + evaluadorId);
        }

        return pedidoRepository.findByEstadoPedidoAndUsuario(EstadoPedidoEnum.PENDIENTE_EVALUACION.getEstado(), evaluadorId)
                .stream()
                .map(pedido -> {
                    return PedidoPendienteEvaluacionDto.builder()
                            .codigo(pedido.getCodigo())
                            .fechaEmision(pedido.getFechaEmision())
                            .nombreCompletoVendedor(pedido.getVendedor().getNombreCompleto())
                            .importeTotal(pedido.getTotal())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Pedido evaluarPedido(Integer pedidoId, Integer evaluadorId, EvaluacionPedidoDto evaluacion) {
        Usuario usuario = usuarioRepository.findById(evaluadorId)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado usuario con el id: " + evaluadorId));

        // Comentarios
        if (evaluacion.getComentarios().length() >= COMENTARIOS_MAX_LENGTH) {
            throw new InvalidInputException("Comentarios no debe exceder los 200 caracteres");
        }

        return pedidoRepository.findById(pedidoId)
                .map(pedido -> {
                    List<Integer> idsEvaluadores = obtenerEvaluadores(pedido);
                    if (!idsEvaluadores.contains(evaluadorId)) {
                        throw new InvalidInputException("El usuario ingresado no puede evaluar el pedido");
                    }
                    if (!pedido.getEstadoPedido().getDescripcion().equalsIgnoreCase(EstadoPedidoEnum.PENDIENTE_EVALUACION.getEstado())) {
                        throw new NoEstadoPendienteException("El pedido no tiene el estado 'PENDIENTE_EVALUACION'");
                    }

                    String estadoPedido;

                    // Actualizar estado de pedido
                    if (idsEvaluadores.equals(PedidoUtils.GRUPO_EVALUADORES_01) || idsEvaluadores.equals(PedidoUtils.GRUPO_EVALUADORES_02)) {
                        estadoPedido = obtenerEstadoPedidoPorResultadoEvaluacion(evaluacion.getResultado());

                        pedidoEvaluadorRepository.findByPedido(pedido)
                                .forEach(pEvaluacion -> {
                                    if ( Objects.equals(pEvaluacion.getUsuario().getId(), usuario.getId()) ) {
                                        pEvaluacion.setFechaEvaluacion(LocalDateTime.now());
                                        pEvaluacion.setResultado(evaluacion.getResultado());
                                        pEvaluacion.setComentarios(evaluacion.getComentarios());
                                    } else {
                                        pEvaluacion.setResultado("Sin Respuesta");
                                    }
                                    pedidoEvaluadorRepository.save(pEvaluacion);
                                });
                    } else {
                        List<PedidoEvaluador> pEvaluaciones = pedidoEvaluadorRepository.findByPedido(pedido);

                        if (evaluacion.getResultado().equalsIgnoreCase("desaprobado")) {
                            estadoPedido = EstadoPedidoEnum.DESAPROBADO.getEstado();
                            pEvaluaciones.forEach(pEvaluacion -> {
                                if ( Objects.equals(pEvaluacion.getUsuario().getId(), usuario.getId()) ) {
                                    pEvaluacion.setFechaEvaluacion(LocalDateTime.now());
                                    pEvaluacion.setComentarios(evaluacion.getComentarios());
                                }
                                pEvaluacion.setResultado(evaluacion.getResultado());

                                pedidoEvaluadorRepository.save(pEvaluacion);
                            });
                        } else {
                            long pEvaluacionesAprobadas = pEvaluaciones.stream()
                                    .filter(pEvaluacion ->
                                            pEvaluacion.getResultado() != null && pEvaluacion.getResultado().equalsIgnoreCase("aprobado") &&
                                                    !Objects.equals(pEvaluacion.getUsuario().getId(), usuario.getId()))
                                    .count();

                            estadoPedido = pEvaluacionesAprobadas == 1 ? EstadoPedidoEnum.APROBADO.getEstado() : EstadoPedidoEnum.PENDIENTE_EVALUACION.getEstado();

                            pEvaluaciones.stream()
                                    .filter(pEvaluacion ->  Objects.equals(pEvaluacion.getUsuario().getId(), usuario.getId()) )
                                    .forEach(pEvaluacion -> {
                                        pEvaluacion.setFechaEvaluacion(LocalDateTime.now());
                                        pEvaluacion.setResultado(evaluacion.getResultado());
                                        pEvaluacion.setComentarios(evaluacion.getComentarios());

                                        pedidoEvaluadorRepository.save(pEvaluacion);
                                    });
                        }
                    }

                    EstadoPedido estado = estadoPedidoRepository.findByDescripcion(estadoPedido)
                            .orElseThrow(() -> new NotFoundException("No se ha encontrado estado de pedido: " + estadoPedido));
                    pedido.setEstadoPedido(estado);

                    pedido = pedidoRepository.save(pedido);
                    sendEmail();

                    return pedido;
                })
                .orElseThrow(() -> new NotFoundException("No se ha encontrado pedido con el id: " + pedidoId));
    }

    private static String obtenerEstadoPedidoPorResultadoEvaluacion(String resultado) {
        String estadoPedido;
        if (resultado.equalsIgnoreCase("aprobado")) {
            estadoPedido = EstadoPedidoEnum.APROBADO.getEstado();
        } else  {
            estadoPedido = EstadoPedidoEnum.DESAPROBADO.getEstado();
        }
        return estadoPedido;
    }

    private List<Integer> obtenerEvaluadores(Pedido pedido) {
        List<Integer> ids;

        if (pedido.getTotal() >= 1 && pedido.getTotal() <= 5000) {
            ids = PedidoUtils.GRUPO_EVALUADORES_01;
        } else if (pedido.getTotal() > 5000 && pedido.getTotal() <= 10000) {
            ids = PedidoUtils.GRUPO_EVALUADORES_02;
        } else {
            ids = PedidoUtils.GRUPO_EVALUADORES_03;
        }

        return ids;
    }

    private void sendEmail() {
        // TODO: Enviar email con información del pedido aprobado
    }
}
