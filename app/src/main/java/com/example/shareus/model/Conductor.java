package com.example.shareus.model;

public class Conductor {

    private int id;
    private String nombre;
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
