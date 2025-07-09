package com.martelli.meatmarket.services.imp;

import com.martelli.meatmarket.model.entidades.Producto;
import com.martelli.meatmarket.Enum.TipoProducto;
import com.martelli.meatmarket.repository.ProductoRepository;
import com.martelli.meatmarket.services.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepo;

    public ProductoServiceImpl(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }

    @Override
    public List<Producto> obtenerTodosPublicos() {
        return productoRepo.findByActivoTrue();
    }

    @Override
    public Producto toggleActivo(Long id) {
        Producto producto = obtenerPorId(id);
        producto.setActivo(!producto.isActivo());
        return productoRepo.save(producto);
    }


    @Override
    public List<Producto> obtenerTodos() {
        return productoRepo.findAll();
    }

    @Override
    public List<Producto> obtenerPorTipo(TipoProducto tipo) {
        return productoRepo.findByTipo(tipo);
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepo.findById(id).orElseThrow();
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepo.save(producto);
    }

    @Override
    public Producto actualizar(Long id, Producto producto) {
        producto.setId(id);
        return productoRepo.save(producto);
    }

    @Override
    public void eliminar(Long id) {
        productoRepo.deleteById(id);
    }
}
