package com.martelli.meatmarket.model.entidades;

import com.martelli.meatmarket.Enum.TipoProducto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String imagenUrl;

    private Double precioMinorista;
    private Double precioMayorista;

    private Integer stock;

    @Enumerated(EnumType.STRING)
    private TipoProducto tipo; // CORTES, COMBOS

    private boolean activo = true;

}
