package com.shareus.models;

import com.fasterxml.jackson.annotation.JsonView;

public class Pasajero {

	@JsonView(VistasViaje.Completo.class)
	private int id;
	@JsonView(VistasViaje.Completo.class)
	private String nombre;
	
	public Pasajero () {
		
	}

	public Pasajero(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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
	
}
