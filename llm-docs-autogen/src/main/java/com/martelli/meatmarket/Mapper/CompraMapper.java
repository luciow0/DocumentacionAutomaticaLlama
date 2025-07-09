package com.martelli.meatmarket.Mapper;

import com.martelli.meatmarket.dtos.CompraItemResponse;
import com.martelli.meatmarket.dtos.CompraResponse;
import com.martelli.meatmarket.model.entidades.Compra;
import com.martelli.meatmarket.model.entidades.CompraItem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CompraMapper {

    private CompraMapper() { }               // clase util estática

    /* ---------- ENTITY ➜ DTO ---------- */
    public static CompraResponse toResponse(Compra compra) {

        List<CompraItemResponse> items = Optional.ofNullable(compra.getItems())
                .orElse(Collections.emptyList())
                .stream()
                .map(CompraMapper::toItemResponse)
                .toList();

        return CompraResponse.builder()
                .id(compra.getId())
                .fecha(compra.getFecha())
                .tipoCliente(compra.getTipoCliente().name())
                .direccionEnvio(compra.getDireccionEnvio())
                .metodoEntrega(compra.getMetodoEntrega())
                .total(compra.getTotal())
                .estado(compra.getEstado())
                .items(items)
                .build();
    }


    /* ---------- ITEM ---------- */
    private static CompraItemResponse toItemResponse(CompraItem item) {
        return CompraItemResponse.builder()
                .productoId(item.getProducto().getId())
                .nombreProducto(item.getProducto().getNombre())
                .cantidad(item.getCantidad())
                .precioUnitario(item.getPrecioUnitario())
                .build();
    }
}
