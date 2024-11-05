package com.example.models;

import com.example.controls.tda.stack.Stack;
import com.example.models.enumerador.TipoIdentificacion;

public class Familia {
    private Integer id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private TipoIdentificacion tipo;
    private String identificacion;
    private Generador[] generadores;

    public Familia() {
        this.generadores = new Generador[5];
    }

 
    public Familia(Integer id, String nombre, String apellido, String direccion, String telefono,
            TipoIdentificacion tipo, String identificacion, Generador[] generadores) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipo = tipo;
        this.identificacion = identificacion;
        this.generadores = generadores;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getApellido() {
        return apellido;
    }


    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getTelefono() {
        return telefono;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public TipoIdentificacion getTipo() {
        return tipo;
    }


    public void setTipo(TipoIdentificacion tipo) {
        this.tipo = tipo;
    }


    public String getIdentificacion() {
        return identificacion;
    }


    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }


    public Generador[] getGeneradores() {
        return generadores;
    }


    public void setGeneradores(Generador[] generadores) {
        this.generadores = generadores;
    }


    public void addGenerador(Generador generador) {
        // Verificar si el arreglo está lleno
        int length = generadores.length;
        int count = 0;
        
        // Contar cuántos elementos no son nulos
        for (Generador gen : generadores) {
            if (gen != null) {
                count++;
            }
        }
        
        if (count == length) {
            // Duplicar tamaño del arreglo si está lleno
            Generador[] newGeneradores = new Generador[length * 2];
            System.arraycopy(generadores, 0, newGeneradores, 0, length);
            generadores = newGeneradores;
        }

        // Agregar el nuevo generador en la primera posición libre
        for (int i = 0; i < generadores.length; i++) {
            if (generadores[i] == null) {
                generadores[i] = generador;
                break;
            }
        }
    }
}
