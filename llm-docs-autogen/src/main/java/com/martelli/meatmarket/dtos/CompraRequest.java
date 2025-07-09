package com.martelli.meatmarket.dtos;

import com.martelli.meatmarket.Enum.TipoCliente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CompraRequest {

    @NotNull(message = "usuarioId es obligatorio")
    @Positive
    private Long usuarioId;

    @NotNull(message = "La dirección de envío es obligatoria")
    private String direccionEnvio;

    @NotNull(message = "El método de entrega es obligatorio")
    private String metodoEntrega;

    @NotNull(message = "El tipo de cliente es obligatorio")
    private TipoCliente tipoCliente;

    @NotEmpty(message = "Debe haber al menos un ítem")
    private List<@Valid CompraItemRequest> items;
}
