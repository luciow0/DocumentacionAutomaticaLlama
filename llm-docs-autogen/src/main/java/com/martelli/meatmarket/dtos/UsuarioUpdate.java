package com.martelli.meatmarket.dtos;

import com.martelli.meatmarket.Enum.Rol;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioUpdate {
    private String nombre;
    private String apellido;
    private Rol rol;
    private Boolean activo;
}

