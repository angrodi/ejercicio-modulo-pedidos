package com.example.pedidoservice.model.dto.mapper;

import com.example.pedidoservice.model.dto.FormaPagoDto;
import com.example.pedidoservice.model.entity.FormaPago;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FormaPagoMapper {

    FormaPagoDto formaPagoToFormaPagoDto(FormaPago formaPago);
    FormaPago formaPagoDtoToFormaPago(FormaPagoDto formaPagoDto);

}
