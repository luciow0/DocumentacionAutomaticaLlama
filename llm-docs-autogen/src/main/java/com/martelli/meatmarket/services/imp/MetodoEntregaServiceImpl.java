package com.martelli.meatmarket.services.imp;

import com.martelli.meatmarket.model.entidades.MetodoEntrega;
import com.martelli.meatmarket.repository.MetodoEntregaRepository;
import com.martelli.meatmarket.services.MetodoEntregaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetodoEntregaServiceImpl implements MetodoEntregaService {

    private final MetodoEntregaRepository metodoRepo;

    public MetodoEntregaServiceImpl(MetodoEntregaRepository metodoRepo) {
        this.metodoRepo = metodoRepo;
    }

    @Override
    public List<MetodoEntrega> obtenerTodos() {
        return metodoRepo.findAll();
    }

    @Override
    public MetodoEntrega guardar(MetodoEntrega metodo) {
        return metodoRepo.save(metodo);
    }

    @Override
    public MetodoEntrega actualizar(Long id, MetodoEntrega metodo) {
        metodo.setId(id);
        return metodoRepo.save(metodo);
    }

    @Override
    public void eliminar(Long id) {
        metodoRepo.deleteById(id);
    }

    @Override
    public MetodoEntrega toggleActivo(Long id) {
        MetodoEntrega metodo = metodoRepo.findById(id).orElseThrow();
        metodo.setActivo(!metodo.isActivo());
        return metodoRepo.save(metodo);
    }
}
