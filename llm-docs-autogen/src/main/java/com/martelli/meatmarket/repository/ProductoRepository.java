package com.martelli.meatmarket.repository;

import com.martelli.meatmarket.model.entidades.Producto;
import com.martelli.meatmarket.Enum.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByTipo(TipoProducto tipo);
    List<Producto> findByActivoTrue();

}
