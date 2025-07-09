package com.martelli.meatmarket.model.entidades;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "compra_items")          // nombre explícito evita confusiones
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CompraItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, scale = 2, precision = 12)
    private BigDecimal precioUnitario;         // ⬅️ mejor que Double

    /* ---------- Relaciones ---------- */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "compra_id")
    private Compra compra;
}
