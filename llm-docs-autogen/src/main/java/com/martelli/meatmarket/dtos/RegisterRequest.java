package com.martelli.meatmarket.dtos;


import com.martelli.meatmarket.Enum.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String apellido;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    private Rol rol;
}
