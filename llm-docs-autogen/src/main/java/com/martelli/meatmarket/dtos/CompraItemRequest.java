package com.martelli.meatmarket.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraItemRequest {

    @NotNull @Positive
    private Long productoId;

    @NotNull @Positive
    private Integer cantidad;
}
