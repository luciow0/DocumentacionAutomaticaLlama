package com.martelli.meatmarket.services;

import com.martelli.meatmarket.dtos.CompraRequest;
import com.martelli.meatmarket.model.entidades.Compra;

import java.util.List;

public interface CompraService {

    /* ---------- CREATE ---------- */
    Compra crearCompra(CompraRequest request);

    /* ---------- READ ---------- */
    List<Compra> obtenerTodas();
    List<Compra> obtenerComprasDeUsuario(Long usuarioId);
    Compra obtenerPorId(Long id);

    /* ---------- UPDATE ---------- */
    Compra actualizarCompra(Long id, CompraRequest request);

    /* ---------- DELETE ---------- */
    void eliminarCompra(Long id);
}
