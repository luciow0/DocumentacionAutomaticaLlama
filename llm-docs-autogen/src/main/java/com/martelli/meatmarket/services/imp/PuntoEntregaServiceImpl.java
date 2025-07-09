package com.martelli.meatmarket.services.imp;

import com.martelli.meatmarket.model.entidades.PuntoEntrega;
import com.martelli.meatmarket.repository.PuntoEntregaRepository;
import com.martelli.meatmarket.services.PuntoEntregaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntoEntregaServiceImpl implements PuntoEntregaService {

    private final PuntoEntregaRepository puntoRepo;

    public PuntoEntregaServiceImpl(PuntoEntregaRepository puntoRepo) {
        this.puntoRepo = puntoRepo;
    }

    @Override
    public List<PuntoEntrega> obtenerTodos() {
        return puntoRepo.findAll();
    }

    @Override
    public PuntoEntrega guardar(PuntoEntrega punto) {
        return puntoRepo.save(punto);
    }

    @Override
    public PuntoEntrega actualizar(Long id, PuntoEntrega punto) {
        punto.setId(id);
        return puntoRepo.save(punto);
    }

    @Override
    public void eliminar(Long id) {
        puntoRepo.deleteById(id);
    }

    @Override
    public PuntoEntrega toggleActivo(Long id) {
        PuntoEntrega punto = puntoRepo.findById(id).orElseThrow();
        punto.setActivo(!punto.isActivo());
        return puntoRepo.save(punto);
    }
}
