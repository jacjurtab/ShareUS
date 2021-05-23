package com.shareus.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Conductor {

    @JsonView(Vistas.Completo.class)
    private int id;
    @JsonView(Vistas.Completo.class)
    private String nombre;
    @JsonView(Vistas.Completo.class)
    private String telefono;

    public Conductor () {

    }

    public Conductor(int id, String nombre, String telefono) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
