package com.martelli.meatmarket.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PuntoEntregaRequest {
    private String nombre;
    private String direccion;
    private String localidad;
    private String horarios;
    private Long metodoEntregaId; // opcional
}
