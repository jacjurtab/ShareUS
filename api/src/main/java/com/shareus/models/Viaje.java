package com.shareus.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Viaje {

	@JsonView(Vistas.Simple.class)
	private int id;
	@JsonView(Vistas.Simple.class)
	private int idConductor;
	@JsonView(Vistas.Simple.class)
	private String conductor;
	@JsonView(Vistas.Completo.class)
	private Conductor conductorObj;
	@JsonView(Vistas.Simple.class)
	private String origen;
	@JsonView(Vistas.Simple.class)
	private String destino;
	@JsonView(Vistas.Simple.class)
	private Long fecha_hora;
	@JsonView(Vistas.Simple.class)
	private int num_pasajeros;
	@JsonView(Vistas.Simple.class)
	private int max_plazas;
	@JsonView(Vistas.Completo.class)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<Pasajero> pasajeros;  //nombres de los pasajeros asociados al viaje
	@JsonView(Vistas.Simple.class)
	private float nota_conductor;  //Representa a nota media del conductor
	@JsonView(Vistas.Simple.class)
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

	public Viaje(int id, String origen, String destino, long fecha_hora,
				 int num_pasajeros, int max_plazas, List<Pasajero> pasajeros, float nota_conductor, float precio) {
		super();
		this.id = id;
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

	public Conductor getConductorObj() {
		return conductorObj;
	}

	public void setConductorObj(Conductor conductorObj) {
		this.conductorObj = conductorObj;
	}
}

