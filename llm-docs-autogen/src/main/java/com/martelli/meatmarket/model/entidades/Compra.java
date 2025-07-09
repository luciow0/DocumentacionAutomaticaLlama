package com.martelli.meatmarket.model.entidades;

import com.martelli.meatmarket.Enum.TipoCliente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "compras")                   // nombre explícito => DDL claro
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Instant de creación (UTC) — puedes usar ZonedDateTime si necesitas zona */
    @Column(nullable = false)
    private LocalDateTime fecha;

    /** Monto total de la compra */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    /** MINORISTA | MAYORISTA */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoCliente tipoCliente;

    @Column(nullable = false, length = 120)
    private String direccionEnvio;

    @Column(nullable = false, length = 30)
    private String metodoEntrega;

    /** PENDIENTE / CONFIRMADA / ENTREGADA, etc. */
    @Column(nullable = false, length = 20)
    @Builder.Default                    // ⬅️  para que “PENDIENTE” se mantenga
    private String estado = "PENDIENTE";

    /* ---------- Relaciones ---------- */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "compra",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<CompraItem> items;

    /** Control de concurrencia optimista (opcional, pero recomendado) */
    @Version
    private Long version;
}
