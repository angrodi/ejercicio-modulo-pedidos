package com.example.pedidoservice.model.dto.mapper;

import com.example.pedidoservice.model.dto.ClienteDto;
import com.example.pedidoservice.model.entity.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDto clienteToClienteDto(Cliente cliente);
    Cliente clienteDtoToCliente(ClienteDto clienteDto);

}
