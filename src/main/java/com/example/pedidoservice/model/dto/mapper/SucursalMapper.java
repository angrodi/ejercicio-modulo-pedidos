package com.example.pedidoservice.model.dto.mapper;

import com.example.pedidoservice.model.dto.SucursalDto;
import com.example.pedidoservice.model.entity.Sucursal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SucursalMapper {

    SucursalDto sucursalToSucursalDto(Sucursal sucursal);
    Sucursal sucursalDtoToSucursal(SucursalDto sucursalDto);

}
