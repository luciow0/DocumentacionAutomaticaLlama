package com.martelli.meatmarket.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter

@Builder                                 // único mecanismo de construcción
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompraResponse {

    private Long id;
    private LocalDateTime fecha;
    private String tipoCliente;          // "MINORISTA" / "MAYORISTA"
    private String direccionEnvio;
    private String metodoEntrega;

    @JsonSerialize(using = ToStringSerializer.class)   // → "12345.67"
    private BigDecimal total;

    private String estado;               // "PENDIENTE", ...

    private List<CompraItemResponse> items;
}
