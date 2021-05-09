package com.shareus.entities;

public class Pasajero {

	private String nombre;
	private int viaje;
	
	public Pasajero () {
		
	}
	
	public Pasajero(String nombre, int viaje) {
		super();
		this.nombre = nombre;
		this.viaje = viaje;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getViaje() {
		return viaje;
	}
	public void setViaje(int viaje) {
		this.viaje = viaje;
	}
	
}
