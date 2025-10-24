package com.jose.api;

import com.jose.domain.Cliente;
import com.jose.domain.Cuenta;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/api/cuentas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CuentasResource {

  public static class NuevaCuenta {
    @NotNull public Long clienteId;
    @NotBlank public String numeroCuenta;
    public String tipo;
    public BigDecimal saldoInicial = BigDecimal.ZERO;
  }

  @POST @Transactional
  public Response crear(NuevaCuenta req) {
    Cliente cli = Cliente.findById(req.clienteId);
    if (cli == null || Boolean.FALSE.equals(cli.estado)) {
      throw new NotFoundException("Cliente no encontrado o inactivo");
    }
    Cuenta c = new Cuenta();
    c.cliente = cli;
    c.numeroCuenta = req.numeroCuenta;
    c.tipo = req.tipo;
    c.saldoInicial = (req.saldoInicial == null ? BigDecimal.ZERO : req.saldoInicial.abs());
    c.saldoActual = c.saldoInicial;
    c.estado = true;
    c.persist();
    return Response.status(201).entity(c).build();
  }

  @GET
  public Response listar() { return Response.ok(Cuenta.listAll()).build(); }

  @GET @Path("/{id}")
  public Response obtener(@PathParam("id") Long id) {
    Cuenta c = Cuenta.findById(id);
    if (c == null) throw new NotFoundException("Cuenta no encontrada");
    return Response.ok(c).build();
  }
}
