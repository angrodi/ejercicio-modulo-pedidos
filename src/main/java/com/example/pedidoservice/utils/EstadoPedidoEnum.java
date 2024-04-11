package com.example.pedidoservice.utils;

import lombok.Getter;

@Getter
public enum EstadoPedidoEnum {

    BORRADOR("BORRADOR"),
    PENDIENTE_EVALUACION("PENDIENTE_EVALUACION"),
    APROBADO("APROBADO"),
    DESAPROBADO("DESAPROBADO");

    private final String estado;

    EstadoPedidoEnum(final String estado) {
        this.estado = estado;
    }

}
