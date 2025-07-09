package com.martelli.meatmarket.services;

import com.martelli.meatmarket.model.entidades.MetodoEntrega;

import java.util.List;

public interface MetodoEntregaService {
    List<MetodoEntrega> obtenerTodos();
    MetodoEntrega guardar(MetodoEntrega metodo);
    MetodoEntrega actualizar(Long id, MetodoEntrega metodo);
    void eliminar(Long id);
    MetodoEntrega toggleActivo(Long id);
}
