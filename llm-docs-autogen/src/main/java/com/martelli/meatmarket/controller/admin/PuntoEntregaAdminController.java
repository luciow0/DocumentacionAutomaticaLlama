package com.martelli.meatmarket.controller.admin;

import com.martelli.meatmarket.dtos.PuntoEntregaRequest;
import com.martelli.meatmarket.dtos.PuntoEntregaResponse;
import com.martelli.meatmarket.model.entidades.MetodoEntrega;
import com.martelli.meatmarket.model.entidades.PuntoEntrega;
import com.martelli.meatmarket.repository.MetodoEntregaRepository;
import com.martelli.meatmarket.services.PuntoEntregaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/puntos-retiro/admin")
public class PuntoEntregaAdminController {

    private final PuntoEntregaService puntoService;
    private final MetodoEntregaRepository metodoRepo;

    public PuntoEntregaAdminController(PuntoEntregaService puntoService, MetodoEntregaRepository metodoRepo) {
        this.puntoService = puntoService;
        this.metodoRepo = metodoRepo;
    }

    @GetMapping
    public ResponseEntity<List<PuntoEntregaResponse>> obtenerTodos() {
        List<PuntoEntrega> puntos = puntoService.obtenerTodos();
        List<PuntoEntregaResponse> response = puntos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PuntoEntregaResponse> crear(@RequestBody PuntoEntregaRequest request) {
        PuntoEntrega nuevo = fromRequest(request);
        nuevo.setActivo(true);
        PuntoEntrega guardado = puntoService.guardar(nuevo);
        return ResponseEntity.ok(toResponse(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PuntoEntregaResponse> actualizar(@PathVariable Long id, @RequestBody PuntoEntregaRequest request) {
        PuntoEntrega actualizado = fromRequest(request);
        PuntoEntrega result = puntoService.actualizar(id, actualizado);
        return ResponseEntity.ok(toResponse(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        puntoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<PuntoEntregaResponse> toggleActivo(@PathVariable Long id) {
        PuntoEntrega result = puntoService.toggleActivo(id);
        return ResponseEntity.ok(toResponse(result));
    }

    // Conversión de DTO → entidad
    private PuntoEntrega fromRequest(PuntoEntregaRequest r) {
        MetodoEntrega metodo = r.getMetodoEntregaId() != null
                ? metodoRepo.findById(r.getMetodoEntregaId()).orElse(null)
                : null;

        return PuntoEntrega.builder()
                .nombre(r.getNombre())
                .direccion(r.getDireccion())
                .localidad(r.getLocalidad())
                .horarios(r.getHorarios())
                .metodoEntrega(metodo)
                .build();
    }

    // Conversión de entidad → DTO
    private PuntoEntregaResponse toResponse(PuntoEntrega p) {
        Long metodoId = p.getMetodoEntrega() != null ? p.getMetodoEntrega().getId() : null;

        return new PuntoEntregaResponse(
                p.getId(),
                p.getNombre(),
                p.getDireccion(),
                p.getLocalidad(),
                p.getHorarios(),
                p.isActivo(),
                metodoId
        );
    }
}
