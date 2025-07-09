package com.martelli.meatmarket.controller.admin;

import com.martelli.meatmarket.dtos.UsuarioRequest;
import com.martelli.meatmarket.dtos.UsuarioResponse;
import com.martelli.meatmarket.dtos.UsuarioUpdate;
import com.martelli.meatmarket.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioAdminController {

    private final UsuarioService service;

    @Operation(
            summary = "Listar usuarios",
            description = "Listar usuarios con filtros opcionales (activo, búsqueda por nombre/email) y paginación"
    )
    @GetMapping
    public Page<UsuarioResponse> listar(
            @Parameter(description = "Filtrar por estado activo/inactivo")
            @RequestParam(required = false) Boolean activo,
            @Parameter(description = "Buscar por nombre o email")
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        return service.listar(activo, search, pageable);
    }

    @PatchMapping("/{id}/toggle-estado")
    public UsuarioResponse alternarEstado(@PathVariable Long id) {
        return service.alternarEstado(id);
    }

    @Operation(
            summary = "Listar usuarios activos",
            description = "Devuelve todos los usuarios cuyo estado activo es true"
    )
    @GetMapping("/activos")
    public List<UsuarioResponse> listarActivos() {
        return service.listar(true);
    }

    @Operation(
            summary = "Listar usuarios inactivos",
            description = "Devuelve todos los usuarios cuyo estado activo es false"
    )
    @GetMapping("/inactivos")
    public List<UsuarioResponse> listarInactivos() {
        return service.listar(false);
    }



    @PatchMapping("/{id}/activar")
    public UsuarioResponse activar(@PathVariable Long id) {
        UsuarioUpdate dto = UsuarioUpdate.builder()
                .activo(true)
                .build();
        return service.actualizar(id, dto);
    }

    @PatchMapping("/{id}/actualizar-estado")
    public UsuarioResponse actualizarEstado(@PathVariable Long id) {
        UsuarioResponse actual = service.obtener(id);

        UsuarioUpdate dto = UsuarioUpdate.builder()
                .activo(!actual.isActivo())
                .build();

        return service.actualizar(id, dto);
    }




    @Operation(
            summary = "Obtener usuario",
            description = "Devuelve los datos completos de un usuario por su ID"
    )
    @GetMapping("/{id}")
    public UsuarioResponse obtener(
            @Parameter(description = "ID del usuario a consultar") @PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(
            summary = "Crear usuario",
            description = "Crea un nuevo usuario especificando nombre, apellido, email, contraseña y rol"
    )
    @PostMapping
    public UsuarioResponse crear(
            @RequestBody UsuarioRequest dto) {
        return service.crear(dto);
    }

    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza el rol y/o el estado activo/inactivo de un usuario existente"
    )
    @PatchMapping("/{id}")
    public UsuarioResponse actualizar(
            @Parameter(description = "ID del usuario a actualizar") @PathVariable Long id,
            @RequestBody UsuarioUpdate dto) {
        return service.actualizar(id, dto);
    }

    @Operation(
            summary = "Resetear contraseña",
            description = "Resetea la contraseña de un usuario a una nueva especificada"
    )
    @PatchMapping("/{id}/reset-password")
    public void resetPassword(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @Parameter(description = "Nueva contraseña") @RequestParam String nuevaPassword) {
        service.resetPassword(id, nuevaPassword);
    }

    @Operation(
            summary = "Desactivar usuario",
            description = "Marca al usuario como inactivo (soft delete)"
    )
    @PatchMapping("/{id}/desactivar")
    public UsuarioResponse desactivar(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        UsuarioUpdate dto = UsuarioUpdate.builder()
                .activo(false)
                .build();
        return service.actualizar(id, dto);
    }

    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina físicamente al usuario de la base de datos (hard delete)"
    )
    @DeleteMapping("/{id}")
    public void eliminar(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        service.eliminar(id);
    }
}
