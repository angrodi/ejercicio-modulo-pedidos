package com.example.pedidoservice.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PedidoUtils {

    private PedidoUtils() {}

    public static final List<Integer> GRUPO_EVALUADORES_01 = Arrays.asList(1, 2, 3);
    public static final List<Integer> GRUPO_EVALUADORES_02 = Arrays.asList(4, 5);;
    public static final List<Integer> GRUPO_EVALUADORES_03 = Arrays.asList(6, 7);;

    public static double redondeoBanquero(double valor) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(2, RoundingMode.HALF_EVEN); // RoundingMode.HALF_EVEN es el modo de redondeo del método banquero
        return  bd.doubleValue();
    }

    public static LocalDateTime agregarDias(LocalDateTime fechaInicial, int diasPorAgregar) {
        LocalDateTime result = fechaInicial;
        int diasAgregados = 0;

        while (diasAgregados < diasPorAgregar) {
            result = result.plusDays(1);
            if (result.getDayOfWeek() != DayOfWeek.SATURDAY && result.getDayOfWeek() != DayOfWeek.SUNDAY) {
                diasAgregados++;
            }
        }
        return result;
    }

}
