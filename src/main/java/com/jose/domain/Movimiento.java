package com.jose.domain;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "movimiento")
public class Movimiento extends PanacheEntityBase {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "movimiento_id")
  public Long id;

  public LocalDateTime fecha;

  @Column(name = "tipo_movimiento", length = 20)
  public String tipoMovimiento; // CREDITO / DEBITO

  public BigDecimal valor;      // almacenado positivo

  @Column(name = "saldo_post")
  public BigDecimal saldoPost;

  @ManyToOne(optional = false)
  @JoinColumn(name = "cuenta_id")
  public Cuenta cuenta;

  public String descripcion;
}
