package com.martelli.meatmarket.services.imp;

import com.martelli.meatmarket.Enum.TipoCliente;
import com.martelli.meatmarket.dtos.CompraRequest;
import com.martelli.meatmarket.model.entidades.*;
import com.martelli.meatmarket.repository.CompraRepository;
import com.martelli.meatmarket.repository.ProductoRepository;
import com.martelli.meatmarket.repository.UsuarioRepository;
import com.martelli.meatmarket.services.CompraService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompraServiceImpl implements CompraService {

    private final CompraRepository  compraRepo;
    private final UsuarioRepository usuarioRepo;
    private final ProductoRepository productoRepo;

    /* ---------- CREATE ---------- */
    @Override
    public Compra crearCompra(CompraRequest request) {
        Usuario usuario = usuarioRepo.findById(request.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Compra compra = Compra.builder()
                .usuario(usuario)
                .direccionEnvio(request.getDireccionEnvio())
                .metodoEntrega(request.getMetodoEntrega())
                .tipoCliente(request.getTipoCliente())      // ← enum directo
                .fecha(LocalDateTime.now())
                .estado("PENDIENTE")
                .build();

        compra.setItems(mapeaItems(request, compra));
        compra.setTotal(calculaTotal(compra.getItems()));

        return compraRepo.save(compra);
    }

    /* ---------- READ ---------- */
    @Override
    public List<Compra> obtenerTodas() {
        return compraRepo.findAll();
    }

    @Override
    public List<Compra> obtenerComprasDeUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return compraRepo.findByUsuario(usuario);
    }

    @Override
    public Compra obtenerPorId(Long id) {
        return compraRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compra no encontrada"));
    }

    /* ---------- UPDATE ---------- */
    @Override
    public Compra actualizarCompra(Long id, CompraRequest request) {
        Compra compra = compraRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compra no encontrada"));

        compra.setDireccionEnvio(request.getDireccionEnvio());
        compra.setMetodoEntrega(request.getMetodoEntrega());
        compra.setTipoCliente(request.getTipoCliente());
        compra.setItems(mapeaItems(request, compra));
        compra.setTotal(calculaTotal(compra.getItems()));

        return compraRepo.save(compra);
    }

    /* ---------- DELETE ---------- */
    @Override
    public void eliminarCompra(Long id) {
        if (!compraRepo.existsById(id)) {
            throw new EntityNotFoundException("Compra no encontrada");
        }
        compraRepo.deleteById(id);
    }

    /* ---------- Helpers ---------- */
    private List<CompraItem> mapeaItems(CompraRequest request, Compra compra) {
        TipoCliente tipo = request.getTipoCliente();

        return request.getItems().stream().map(itemReq -> {
            Producto producto = productoRepo.findById(itemReq.getProductoId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

            BigDecimal precio = BigDecimal.valueOf(tipo == TipoCliente.MAYORISTA
                    ? producto.getPrecioMayorista()
                    : producto.getPrecioMinorista());

            return CompraItem.builder()
                    .producto(producto)
                    .cantidad(itemReq.getCantidad())
                    .precioUnitario(precio)
                    .compra(compra)
                    .build();
        }).toList();
    }

    private BigDecimal calculaTotal(List<CompraItem> items) {
        return items.stream()
                .map(i -> i.getPrecioUnitario()
                        .multiply(BigDecimal.valueOf(i.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
