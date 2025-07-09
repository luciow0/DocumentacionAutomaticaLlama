package com.martelli.meatmarket.controller.usuario;

import com.martelli.meatmarket.dtos.*;
import com.martelli.meatmarket.Mapper.CompraMapper;
import com.martelli.meatmarket.model.entidades.Usuario;
import com.martelli.meatmarket.services.CompraService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {


    private final CompraService compraService;

    /* ---------- CREATE ---------- */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompraResponse crear(@Valid @RequestBody CompraRequest request) {
        return CompraMapper.toResponse(
                compraService.crearCompra(request)
        );
    }

    @GetMapping("/mias")
    public List<CompraResponse> misCompras(HttpServletRequest request) {
        Usuario actual = (Usuario) request.getAttribute("usuarioActual");
        return compraService.obtenerComprasDeUsuario(actual.getId())
                .stream()
                .map(CompraMapper::toResponse)
                .toList();
    }




    /* ---------- READ ---------- */
    @GetMapping
    public List<CompraResponse> listarTodas() {
        return compraService.obtenerTodas()
                .stream()
                .map(CompraMapper::toResponse)
                .toList();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<CompraResponse> comprasDeUsuario(@PathVariable Long idUsuario) {
        return compraService.obtenerComprasDeUsuario(idUsuario)
                .stream()
                .map(CompraMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CompraResponse obtenerPorId(@PathVariable Long id) {
        return CompraMapper.toResponse(
                compraService.obtenerPorId(id)
        );
    }

    /* ---------- UPDATE ---------- */
    @PutMapping("/{id}")
    public CompraResponse actualizar(@PathVariable Long id,
                                     @Valid @RequestBody CompraRequest request) {
        return CompraMapper.toResponse(
                compraService.actualizarCompra(id, request)
        );
    }

    /* ---------- DELETE ---------- */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        compraService.eliminarCompra(id);
    }
}
