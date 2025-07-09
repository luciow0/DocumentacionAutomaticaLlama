package com.martelli.meatmarket.controller.usuario;

import com.martelli.meatmarket.dtos.UsuarioResponse;
import com.martelli.meatmarket.dtos.UsuarioUpdate;
import com.martelli.meatmarket.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/usuarios")
public class ClienteUsuarioController {

    private final UsuarioService service;

    /**
     * Ver mi propio perfil
     */
    @Operation(summary = "Obtener mi perfil",
            description = "Devuelve los datos del usuario autenticado")
    @GetMapping("/me")
    public UsuarioResponse me(HttpServletRequest request) {
        var usuarioActual = (com.martelli.meatmarket.model.entidades.Usuario)
                request.getAttribute("usuarioActual");
        return service.obtener(usuarioActual.getId());
    }

    /**
     * Cambiar mi propia contraseña
     */
    @Operation(summary = "Cambiar contraseña",
            description = "Permite al usuario autenticado cambiar su propia contraseña")
    @PatchMapping("/me/password")
    public void cambiarPassword(
            HttpServletRequest request,
            @Parameter(description = "Nueva contraseña del usuario")
            @RequestParam String nuevaPassword) {
        var usuarioActual = (com.martelli.meatmarket.model.entidades.Usuario)
                request.getAttribute("usuarioActual");
        service.resetPassword(usuarioActual.getId(), nuevaPassword);
    }

    /**
     * Actualizar mi perfil (nombre/apellido)
     */
    @Operation(summary = "Actualizar perfil",
            description = "Permite al usuario autenticado actualizar su nombre y/o apellido")
    @PatchMapping("/me")
    public UsuarioResponse actualizarPerfil(
            HttpServletRequest request,
            @Parameter(description = "Nuevo nombre del usuario")
            @RequestParam(required = false) String nombre,
            @Parameter(description = "Nuevo apellido del usuario")
            @RequestParam(required = false) String apellido) {
        var usuarioActual = (com.martelli.meatmarket.model.entidades.Usuario)
                request.getAttribute("usuarioActual");

        var usuarioUpdate = com.martelli.meatmarket.dtos.UsuarioUpdate.builder()
                .nombre(nombre)
                .apellido(apellido)
                .build();

        return service.actualizar(usuarioActual.getId(), usuarioUpdate);
    }
}
