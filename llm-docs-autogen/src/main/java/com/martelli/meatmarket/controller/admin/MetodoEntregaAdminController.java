package com.martelli.meatmarket.controller.admin;

import com.martelli.meatmarket.dtos.MetodoEntregaRequest;
import com.martelli.meatmarket.dtos.MetodoEntregaResponse;
import com.martelli.meatmarket.model.entidades.MetodoEntrega;
import com.martelli.meatmarket.services.MetodoEntregaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/metodos-entrega/admin")
public class MetodoEntregaAdminController {

    private final MetodoEntregaService metodoService;

    public MetodoEntregaAdminController(MetodoEntregaService metodoService) {
        this.metodoService = metodoService;
    }

    @GetMapping
    public ResponseEntity<List<MetodoEntregaResponse>> obtenerTodos() {
        List<MetodoEntrega> metodos = metodoService.obtenerTodos();
        List<MetodoEntregaResponse> response = metodos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MetodoEntregaResponse> crear(@RequestBody MetodoEntregaRequest request) {
        MetodoEntrega nuevo = fromRequest(request);
        nuevo.setActivo(true);
        MetodoEntrega guardado = metodoService.guardar(nuevo);
        return ResponseEntity.ok(toResponse(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoEntregaResponse> actualizar(@PathVariable Long id, @RequestBody MetodoEntregaRequest request) {
        MetodoEntrega actualizado = fromRequest(request);
        MetodoEntrega result = metodoService.actualizar(id, actualizado);
        return ResponseEntity.ok(toResponse(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        metodoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<MetodoEntregaResponse> toggleActivo(@PathVariable Long id) {
        MetodoEntrega result = metodoService.toggleActivo(id);
        return ResponseEntity.ok(toResponse(result));
    }

    // Converters
    private MetodoEntrega fromRequest(MetodoEntregaRequest r) {
        return MetodoEntrega.builder()
                .nombre(r.getNombre())
                .descripcion(r.getDescripcion())
                .costoBase(r.getCostoBase())
                .tiempoEstimadoDias(r.getTiempoEstimadoDias())
                .requiereDireccion(r.isRequiereDireccion())
                .requierePuntoRetiro(r.isRequierePuntoRetiro())
                .build();
    }

    private MetodoEntregaResponse toResponse(MetodoEntrega m) {
        return new MetodoEntregaResponse(
                m.getId(),
                m.getNombre(),
                m.getDescripcion(),
                m.getCostoBase(),
                m.getTiempoEstimadoDias(),
                m.isRequiereDireccion(),
                m.isRequierePuntoRetiro(),
                m.isActivo()
        );
    }
}
