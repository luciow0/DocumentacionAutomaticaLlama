package com.martelli.meatmarket.controller.admin;

import com.martelli.meatmarket.dtos.ProductoRequest;
import com.martelli.meatmarket.dtos.ProductoResponse;
import com.martelli.meatmarket.model.entidades.Producto;
import com.martelli.meatmarket.services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos/admin")
public class ProductoAdminController {

    private final ProductoService productoService;

    public ProductoAdminController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 🔍 Listar todos los productos (activos e inactivos)
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> obtenerTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        List<ProductoResponse> response = productos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // ➕ Crear producto
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody ProductoRequest request) {
        Producto producto = fromRequest(request);
        producto.setActivo(true); // por defecto
        Producto guardado = productoService.guardar(producto);
        return ResponseEntity.ok(toResponse(guardado));
    }

    // ✏️ Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id, @RequestBody ProductoRequest request) {
        Producto actualizado = fromRequest(request);
        Producto result = productoService.actualizar(id, actualizado);
        return ResponseEntity.ok(toResponse(result));
    }

    // ❌ Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // 🔁 Activar/Desactivar
    @PutMapping("/{id}/activar")
    public ResponseEntity<ProductoResponse> toggleActivo(@PathVariable Long id) {
        Producto actualizado = productoService.toggleActivo(id);
        return ResponseEntity.ok(toResponse(actualizado));
    }

    // 🧱 Convertir entidad → response
    private ProductoResponse toResponse(Producto p) {
        return new ProductoResponse(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getImagenUrl(),
                p.getPrecioMinorista(),
                p.getPrecioMayorista(),
                p.getStock(),
                p.getTipo(),
                p.isActivo()
        );
    }

    // 🧱 Convertir request → entidad
    private Producto fromRequest(ProductoRequest r) {
        return Producto.builder()
                .nombre(r.getNombre())
                .descripcion(r.getDescripcion())
                .imagenUrl(r.getImagenUrl())
                .precioMinorista(r.getPrecioMinorista())
                .precioMayorista(r.getPrecioMayorista())
                .stock(r.getStock())
                .tipo(r.getTipo())
                .build();
    }
}
