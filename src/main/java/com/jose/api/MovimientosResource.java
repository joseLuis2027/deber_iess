package com.jose.api;

import com.jose.domain.Cuenta;
import com.jose.domain.Movimiento;
import com.jose.service.SaldoNoDisponibleException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Path("/api/movimientos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovimientosResource {

  public static class NuevoMovimiento {
    @NotNull public Long cuentaId;
    @NotNull public String tipoMovimiento; // CREDITO/DEBITO
    @NotNull public BigDecimal valor;
    public String descripcion;
  }

  @POST @Transactional
  public Response crear(NuevoMovimiento req) {
    Cuenta cuenta = Cuenta.findById(req.cuentaId);
    if (cuenta == null || Boolean.FALSE.equals(cuenta.estado)) {
      throw new NotFoundException("Cuenta no encontrada o inactiva");
    }

    BigDecimal saldoActual = (cuenta.saldoActual == null ? BigDecimal.ZERO : cuenta.saldoActual);
    BigDecimal monto = req.valor.abs();

    BigDecimal saldoPost;
    String t = req.tipoMovimiento == null ? "" : req.tipoMovimiento.toUpperCase().trim();
    switch (t) {
      case "CREDITO":
        saldoPost = saldoActual.add(monto);
        break;
      case "DEBITO":
        if (saldoActual.compareTo(monto) < 0) {
          throw new SaldoNoDisponibleException("Saldo no disponible para la cuenta " + cuenta.id);
        }
        saldoPost = saldoActual.subtract(monto);
        break;
      default:
        throw new BadRequestException("tipoMovimiento debe ser CREDITO o DEBITO");
    }

    Movimiento m = new Movimiento();
    m.fecha = LocalDateTime.now();
    m.tipoMovimiento = t;
    m.valor = monto; // almacenamos positivo
    m.saldoPost = saldoPost;
    m.descripcion = req.descripcion;
    m.cuenta = cuenta;
    m.persist();

    cuenta.saldoActual = saldoPost;
    cuenta.persist();

    return Response.status(201).entity(
      Map.of(
        "movimientoId", m.id,
        "fecha", m.fecha.toString(),
        "tipoMovimiento", m.tipoMovimiento,
        "valor", ("DEBITO".equals(t) ? monto.negate() : monto),
        "saldoPost", m.saldoPost,
        "cuentaId", cuenta.id
      )
    ).build();
  }

  @GET
  public Response listar(@QueryParam("cuentaId") Long cuentaId) {
    if (cuentaId == null) {
      return Response.ok(Movimiento.listAll()).build();
    }
    return Response.ok(Movimiento.list("cuenta.id", cuentaId)).build();
  }
}
