package com.jose.api;

import com.jose.domain.Cliente;
import com.jose.domain.Persona;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientesResource {

  public static class NuevoCliente {
    @NotBlank public String nombre;
    public String genero;
    public Integer edad;
    public String identificacion;
    public String direccion;
    public String telefono;
    @NotBlank public String contrasena;
    public Boolean estado = true;
  }

  @POST @Transactional
  public Response crear(NuevoCliente req) {
    Persona p = new Persona();
    p.nombre = req.nombre;
    p.genero = req.genero;
    p.edad = req.edad;
    p.identificacion = req.identificacion;
    p.direccion = req.direccion;
    p.telefono = req.telefono;
    p.persist();

    Cliente c = new Cliente();
    c.persona = p;
    c.contrasena = req.contrasena;
    c.estado = (req.estado == null) ? true : req.estado;
    c.persist();

    return Response.status(201).entity(c).build();
  }

  @GET
  public Response listar() { return Response.ok(Cliente.listAll()).build(); }

  @GET @Path("/{id}")
  public Response obtener(@PathParam("id") Long id) {
    Cliente c = Cliente.findById(id);
    if (c == null) throw new NotFoundException("Cliente no encontrado");
    return Response.ok(c).build();
  }
}
