package com.martelli.meatmarket.services;

import com.martelli.meatmarket.Enum.TipoProducto;
import com.martelli.meatmarket.model.entidades.*;
import com.martelli.meatmarket.model.entidades.Producto;


import java.util.List;

public interface ProductoService {
    List<Producto> obtenerTodos();
    List<Producto> obtenerPorTipo(TipoProducto tipo);
    Producto obtenerPorId(Long id);
    Producto guardar(Producto producto);
    Producto actualizar(Long id, Producto producto);
    void eliminar(Long id);
    List<Producto> obtenerTodosPublicos(); // solo productos activos
    Producto toggleActivo(Long id);
}
