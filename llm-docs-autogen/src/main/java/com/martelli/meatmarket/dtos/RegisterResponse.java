package com.martelli.meatmarket.dtos;

import com.martelli.meatmarket.Enum.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class RegisterResponse {
    private String token;
    private String nombre;
    private String email;
    private String rol;
}
