package com.martelli.meatmarket.repository;

import com.martelli.meatmarket.model.entidades.MetodoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetodoEntregaRepository extends JpaRepository<MetodoEntrega, Long> {
    List<MetodoEntrega> findByActivoTrue();
}
