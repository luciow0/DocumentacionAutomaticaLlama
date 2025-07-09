package com.martelli.meatmarket.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MetodoEntregaRequest {
    private String nombre;
    private String descripcion;
    private Double costoBase;
    private Integer tiempoEstimadoDias;
    private boolean requiereDireccion;
    private boolean requierePuntoRetiro;
}
