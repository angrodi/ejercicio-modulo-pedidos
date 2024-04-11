package com.example.pedidoservice.model.dto.mapper;

import com.example.pedidoservice.model.dto.VendedorDto;
import com.example.pedidoservice.model.entity.Vendedor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendedorMapper {

    VendedorDto vendedorToVendedorDto(Vendedor vendedor);
    Vendedor vendedorDtoToVendedor(VendedorDto vendedorDto);

}
