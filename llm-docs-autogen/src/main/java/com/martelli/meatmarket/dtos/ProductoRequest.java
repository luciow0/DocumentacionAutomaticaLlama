package com.martelli.meatmarket.dtos;

import com.martelli.meatmarket.Enum.TipoProducto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductoRequest {
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private Double precioMinorista;
    private Double precioMayorista;
    private Integer stock;
    private TipoProducto tipo;
}
