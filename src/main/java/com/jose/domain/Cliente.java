package com.jose.domain;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity @Table(name = "cliente")
public class Cliente extends PanacheEntityBase {
  @Id
  @Column(name = "cliente_id")
  public Long id; // 1:1 con persona

  @OneToOne(optional = false)
  @MapsId
  @JoinColumn(name = "cliente_id")
  public Persona persona;

  @Column(nullable = false)
  public String contrasena;

  @Column(nullable = false)
  public Boolean estado = true;
}
