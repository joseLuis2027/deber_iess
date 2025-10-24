package com.jose.api;

import com.jose.service.SaldoNoDisponibleException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.Instant;
import java.util.Map;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {
  @Override
  public Response toResponse(Exception e) {
    int status = (e instanceof SaldoNoDisponibleException) ? 400 :
                 (e instanceof jakarta.ws.rs.NotFoundException) ? 404 : 500;
    String error = (e instanceof SaldoNoDisponibleException) ? "Saldo no disponible" :
                   (e instanceof jakarta.ws.rs.NotFoundException) ? "Recurso no encontrado" : "Error interno";
    return Response.status(status).entity(
      Map.of("timestamp", Instant.now().toString(), "status", status, "error", error, "message", e.getMessage())
    ).build();
  }
}
