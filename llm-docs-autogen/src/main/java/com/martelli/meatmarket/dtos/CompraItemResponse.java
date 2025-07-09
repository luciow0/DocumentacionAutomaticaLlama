package com.martelli.meatmarket.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraItemResponse {

    private Long productoId;
    private String nombreProducto;
    private Integer cantidad;

    /** Precio unitario con dos decimales serializado como string */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal precioUnitario;
}
