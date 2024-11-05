package com.example.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.HashMap;
import com.example.controls.dao.FamilaDao;
import com.example.controls.dao.GeneradorDao;
import com.example.controls.tda.queque.Queque;
import com.example.controls.tda.stack.Stack;
import com.example.models.Familia;
import com.example.models.Generador;
import com.google.gson.Gson;
import com.example.controls.tda.list.LinkedList;

@Path("person")
public class CensoApi {

    // listar familias
    @Path("/list/familia")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        HashMap map = new HashMap<>();
        FamilaDao fd = new FamilaDao();
        map.put("msg", "Ok");
        map.put("data", fd.listAll().toArray());
        if (fd.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    // enumeradores de identificacion
    @Path("/listType")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        FamilaDao ps = new FamilaDao();
        map.put("msg", "Ok");
        map.put("data", ps.getTipos());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    // editar familia
    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        FamilaDao fd = new FamilaDao();
        try {
            fd.setFamilia(fd.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }
        map.put("msg", "Ok");
        map.put("data", fd.getFamilia());
        if (fd.getFamilia().getId() == null) {
            map.put("data", "No existe la persona con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    // guardar
    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        try {
            FamilaDao fd = new FamilaDao();
            fd.getFamilia().setApellido(map.get(("apellido")).toString());
            fd.getFamilia().setNombre(map.get(("nombre")).toString());
            fd.getFamilia().setDireccion(map.get(("direccion")).toString());
            fd.getFamilia().setTelefono(map.get(("fono")).toString());
            fd.getFamilia().setTipo(fd.getTipoIdentificacion(map.get("tipo").toString()));

            fd.getFamilia().setIdentificacion(map.get("identificacion").toString());

            fd.save();
            fd.update();
            res.put("msf", "OK");
            res.put("msg", "Persona registrada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    // actualizar y guardar la modificacion
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        HashMap res = new HashMap<>();
        try {
            FamilaDao fd = new FamilaDao();
            fd.setFamilia(fd.get(Integer.parseInt(map.get("id").toString())));
            fd.getFamilia().setApellido(map.get(("apellido")).toString());
            fd.getFamilia().setNombre(map.get(("nombre")).toString());
            fd.getFamilia().setDireccion(map.get(("direccion")).toString());
            fd.getFamilia().setTelefono(map.get(("fono")).toString());
            fd.getFamilia().setTipo(fd.getTipoIdentificacion(map.get("tipo").toString()));

            // fd.getPersona().setIdentificacion(map.get("identificacion").toString());

            fd.update();
            res.put("msf", "OK");
            res.put("msg", "Persona registrada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    // guardar generador
    @Path("/save/generador/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveGenerador(@PathParam("id") Integer id, HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            GeneradorDao gd = new GeneradorDao();
            FamilaDao fd = new FamilaDao();

            // Obtener la familia por el ID
            Familia familia = fd.get(id);
            if (familia == null) {
                res.put("msg", "No existe la familia con ese identificador");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Crear el generador y configurar sus propiedades
            Generador generador = new Generador();
            generador.setUso(map.get("uso").toString());
            generador.setConsumo(Float.parseFloat(map.get("consumo").toString()));
            generador.setPotencia(Float.parseFloat(map.get("potencia").toString()));
            generador.setCoste(Float.parseFloat(map.get("coste").toString()));

            // Agregar el generador al arreglo de generadores de la familia
            familia.addGenerador(generador);

            // Guardar el generador
            gd.setGenerador(generador);
            gd.save();

            // Actualizar la familia y guardarla nuevamente
            fd.setFamilia(familia);
            fd.update();

            res.put("msg", "Generador registrado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en saveGenerador: " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update/generador")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGenerador(HashMap map) {
        HashMap res = new HashMap<>();
        try {
            GeneradorDao fd = new GeneradorDao();
            fd.setGenerador(fd.get(Integer.parseInt(map.get("id").toString())));
            fd.getGenerador().setUso(map.get("uso").toString());
            fd.getGenerador().setConsumo(Float.parseFloat(map.get("consumo").toString()));
            fd.getGenerador().setPotencia(Float.parseFloat(map.get("potencia").toString()));
            fd.getGenerador().setCoste(Float.parseFloat(map.get("coste").toString()));

            // fd.getPersona().setIdentificacion(map.get("identificacion").toString());

            fd.update();
            res.put("msf", "OK");
            res.put("msg", "Persona registrada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    // modificar generador
    @Path("/getGeneradores/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGeneradores(@PathParam("id") Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        FamilaDao fd = new FamilaDao();
        try {
            // Obtener la familia por el ID
            Familia familia = fd.get(id);
            if (familia == null) {
                res.put("msg", "No existe la familia con ese identificador");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Obtener el arreglo de generadores de la familia
            Generador[] generadores = familia.getGeneradores();
            boolean isEmpty = true;

            // Comprobar si hay algún generador registrado
            for (Generador gen : generadores) {
                if (gen != null) {
                    isEmpty = false;
                    break;
                }
            }

            if (isEmpty) {
                res.put("msg", "La familia no tiene generadores registrados");
                res.put("data", new Object[] {});
            } else {
                res.put("msg", "Ok");
                res.put("data", generadores);
            }
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
        return Response.ok(res).build();
    }
}