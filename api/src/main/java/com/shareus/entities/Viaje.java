package com.shareus.entities;

import java.sql.Timestamp;
import java.util.List;

public class Viaje {
	
	//TODO: todos los atributos tipo int son "id", cambiar a uuid
	private int id;   
	private String conductor;  
	private String origen;
	private String destino;
	private Timestamp fecha_hora;
	private int num_pasajeros;
	private int max_plazas;
	private List<Pasajero> pasajeros;  //nombres de los pasajeros asociados al viaje
	private float nota_conductor;  //Representa a nota media del conductor

	
	public Viaje () {
		
	}
	
	public Viaje(int id, String conductor, String origen, String destino, Timestamp fecha_hora, 
			int num_pasajeros, int max_plazas, List<Pasajero> pasajeros, float nota_conductor) {
		super();
		this.id = id;
		this.conductor = conductor;
		this.origen = origen;
		this.destino = destino;
		this.setFecha_hora(fecha_hora);
		this.num_pasajeros = num_pasajeros;
		this.max_plazas = max_plazas;
		this.pasajeros = pasajeros;
		this.nota_conductor = nota_conductor;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConductor() {
		return conductor;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public int getNum_pasajeros() {
		return num_pasajeros;
	}

	public void setNum_pasajeros(int num_pasajeros) {
		this.num_pasajeros = num_pasajeros;
	}

	public int getMax_plazas() {
		return max_plazas;
	}

	public void setMax_plazas(int max_plazas) {
		this.max_plazas = max_plazas;
	}

	public List<Pasajero> getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(List<Pasajero> pasajeros) {
		this.pasajeros = pasajeros;
	}

	public Timestamp getFecha_hora() {
		return fecha_hora;
	}

	public void setFecha_hora(Timestamp fecha_hora) {
		this.fecha_hora = fecha_hora;
	}

	public float getNota_conductor() {
		return nota_conductor;
	}

	public void setNota_conductor(float nota_conductor) {
		this.nota_conductor = nota_conductor;
	}


}

