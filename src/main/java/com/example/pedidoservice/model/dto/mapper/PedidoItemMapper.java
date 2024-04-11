package com.example.pedidoservice.model.dto.mapper;

import com.example.pedidoservice.model.dto.PedidoItemDto;
import com.example.pedidoservice.model.entity.PedidoItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoItemMapper {

    PedidoItemDto pedidoItemToPedidoItemDto(PedidoItem pedidoItem);
    PedidoItem pedidoItemDtoToPedidoItem(PedidoItemDto pedidoItemDto);

}
