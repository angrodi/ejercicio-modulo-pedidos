package com.example.pedidoservice.model.dto.mapper;

import com.example.pedidoservice.model.dto.EstadoPedidoDto;
import com.example.pedidoservice.model.entity.EstadoPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstadoPedidoMapper {

    EstadoPedidoDto estadoPedidoToEstadoPedidoDto(EstadoPedido estadoPedido);
    EstadoPedido estadoPedidoDtoToEstadoPedido(EstadoPedidoDto estadoPedidoDto);

}
