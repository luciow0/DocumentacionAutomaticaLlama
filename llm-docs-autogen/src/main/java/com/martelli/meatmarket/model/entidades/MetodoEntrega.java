package com.martelli.meatmarket.model.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MetodoEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    private Double costoBase;
    private Integer tiempoEstimadoDias;

    private boolean requiereDireccion;
    private boolean requierePuntoRetiro;

    private boolean activo = true;
}
