package com.martelli.meatmarket.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PuntoEntregaResponse {
    private Long id;
    private String nombre;
    private String direccion;
    private String localidad;
    private String horarios;
    private boolean activo;
    private Long metodoEntregaId; // opcional para mostrar en el admin
}
