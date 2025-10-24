package com.jose.domain;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.math.BigDecimal;

@Entity @Table(name = "cuenta")
public class Cuenta extends PanacheEntityBase {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cuenta_id")
  public Long id;

  @Column(name="numero_cuenta", nullable = false, unique = true, length = 50)
  public String numeroCuenta;

  public String tipo;

  @Column(name="saldo_inicial")
  public BigDecimal saldoInicial = BigDecimal.ZERO;

  @Column(name="saldo_actual")
  public BigDecimal saldoActual = BigDecimal.ZERO;

  public Boolean estado = true;

  @ManyToOne(optional = false)
  @JoinColumn(name = "cliente_id")
  public Cliente cliente;
}
