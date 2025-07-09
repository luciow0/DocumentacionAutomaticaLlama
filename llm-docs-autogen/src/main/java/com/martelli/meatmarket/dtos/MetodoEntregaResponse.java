package com.martelli.meatmarket.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MetodoEntregaResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double costoBase;
    private Integer tiempoEstimadoDias;
    private boolean requiereDireccion;
    private boolean requierePuntoRetiro;
    private boolean activo;
}
