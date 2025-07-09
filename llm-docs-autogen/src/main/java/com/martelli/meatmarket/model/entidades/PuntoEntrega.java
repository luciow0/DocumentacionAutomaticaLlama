package com.martelli.meatmarket.model.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PuntoEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String localidad;
    private String horarios;

    private boolean activo = true;

    @ManyToOne
    private MetodoEntrega metodoEntrega; // opcional si necesitás vincular
}
