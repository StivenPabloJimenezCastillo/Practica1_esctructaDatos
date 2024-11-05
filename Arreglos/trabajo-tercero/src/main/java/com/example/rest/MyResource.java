package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.controls.dao.FamilaDao;
import com.example.controls.dao.GeneradorDao;
import com.example.controls.tda.list.LinkedList;
import com.example.controls.tda.queque.Queque;
import com.example.controls.tda.stack.Stack;
import com.example.models.Generador;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        HashMap mapa = new HashMap<>();
        //PersonaDao pd = new PersonaDao();
        FamilaDao pd = new FamilaDao();
        GeneradorDao gd = new GeneradorDao();
        Queque<Generador> ss = new Queque<>(2);
        LinkedList<Generador> qq = new LinkedList<>();
        String aux = "";
        try {
            gd.getGenerador().setCoste(1000.0f);
            gd.getGenerador().setConsumo(0.3f);
            gd.getGenerador().setPotencia(10f);
            gd.getGenerador().setUso("Internet y electrodomesticos");
            gd.save();
            qq.add(gd.getGenerador());

            pd.getFamilia().setNombre("Stiven");
            pd.getFamilia().setApellido("Garcia");
            pd.getFamilia().setDireccion("Calle 123");
            pd.getFamilia().setTelefono("123456");
            pd.getFamilia().setIdentificacion("1150096681");
            pd.save();
            aux = "La lista esta vasia"+pd.listAll().isEmpty();
        } catch (Exception e) {
            System.out.println("Error"+e);
        }
        mapa.put("msg","Ok");
        mapa.put("data", "test"+aux);
        return Response.ok(mapa).build();
    }
}
