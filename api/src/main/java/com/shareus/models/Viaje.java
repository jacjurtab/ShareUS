package com.shareus.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;

import java.sql.Timestamp;
import java.util.List;

public class Viaje {

	@JsonView(VistasViaje.Simple.class)
	private int id;
	@JsonView(VistasViaje.Simple.class)
	private int idConductor;
	@JsonView(VistasViaje.Simple.class)
	private String conductor;
	@JsonView(VistasViaje.Simple.class)
	private String origen;
	@JsonView(VistasViaje.Simple.class)
	private String destino;
	@JsonView(VistasViaje.Simple.class)
	private Long fecha_hora;
	@JsonView(VistasViaje.Simple.class)
	private int num_pasajeros;
	@JsonView(VistasViaje.Simple.class)
	private int max_plazas;
	@JsonView(VistasViaje.Completo.class)
	private List<Pasajero> pasajeros;  //nombres de los pasajeros asociados al viaje
	@JsonView(VistasViaje.Simple.class)
	private float nota_conductor;  //Representa a nota media del conductor
	@JsonView(VistasViaje.Simple.class)
	private float precio;

	public Viaje () {
		
	}
	
	public Viaje(int id, int idConductor, String conductor, String origen, String destino, long fecha_hora,
			int num_pasajeros, int max_plazas, List<Pasajero> pasajeros, float nota_conductor, float precio) {
		super();
		this.id = id;
		this.idConductor = idConductor;
		this.conductor = conductor;
		this.origen = origen;
		this.destino = destino;
		this.fecha_hora = fecha_hora;
		this.num_pasajeros = num_pasajeros;
		this.max_plazas = max_plazas;
		this.pasajeros = pasajeros;
		this.nota_conductor = nota_conductor;
		this.precio = precio;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIdConductor(int idConductor) { this.idConductor = idConductor; }

	public int getIdConductor() { return idConductor; }

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

	public Long getFecha_hora() {
		return fecha_hora;
	}

	public void setFecha_hora(Long fecha_hora) {
		this.fecha_hora = fecha_hora;
	}

	public float getNota_conductor() {
		return nota_conductor;
	}

	public void setNota_conductor(float nota_conductor) {
		this.nota_conductor = nota_conductor;
	}

	public float getPrecio() { return precio; }

	public void setPrecio(float precio) { this.precio = precio; }
}

