package com.martelli.meatmarket.dtos;

import com.martelli.meatmarket.Enum.TipoProducto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private Double precioMinorista;
    private Double precioMayorista;
    private Integer stock;
    private TipoProducto tipo;
    private boolean activo;
}
