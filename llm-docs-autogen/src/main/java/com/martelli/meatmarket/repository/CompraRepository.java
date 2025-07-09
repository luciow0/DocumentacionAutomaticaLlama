package com.martelli.meatmarket.repository;

import com.martelli.meatmarket.model.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByUsuario(Usuario usuario);
}
