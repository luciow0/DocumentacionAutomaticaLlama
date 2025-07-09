package com.martelli.meatmarket.dtos;

import com.martelli.meatmarket.Enum.Rol;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Rol rol; // ADMIN o CLIENTE
}

