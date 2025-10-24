package com.jose.domain;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity @Table(name = "persona")
public class Persona extends PanacheEntityBase {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "persona_id")
  public Long id;

  @Column(nullable = false, length = 150)
  public String nombre;

  public String genero;
  public Integer edad;

  @Column(unique = true, length = 50)
  public String identificacion;

  public String direccion;
  public String telefono;
}
