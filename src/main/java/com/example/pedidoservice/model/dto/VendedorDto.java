package com.example.pedidoservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendedorDto {

    private Integer id;
    private String nombreCompleto;

}
