package com.martelli.meatmarket.repository;

import com.martelli.meatmarket.model.entidades.PuntoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuntoEntregaRepository extends JpaRepository<PuntoEntrega, Long> {
    List<PuntoEntrega> findByActivoTrue();
}
