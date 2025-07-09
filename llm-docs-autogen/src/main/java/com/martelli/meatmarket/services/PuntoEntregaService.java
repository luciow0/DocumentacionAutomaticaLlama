package com.martelli.meatmarket.services;

import com.martelli.meatmarket.model.entidades.PuntoEntrega;

import java.util.List;

public interface PuntoEntregaService {
    List<PuntoEntrega> obtenerTodos();
    PuntoEntrega guardar(PuntoEntrega punto);
    PuntoEntrega actualizar(Long id, PuntoEntrega punto);
    void eliminar(Long id);
    PuntoEntrega toggleActivo(Long id);
}
