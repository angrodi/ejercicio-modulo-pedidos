package com.example.pedidoservice.model.dto.mapper;

import com.example.pedidoservice.model.dto.PedidoDto;
import com.example.pedidoservice.model.entity.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoDto pedidoToPedidoDto(Pedido pedido);
    Pedido pedidoDtoToPedido(PedidoDto pedidoDto);

}
