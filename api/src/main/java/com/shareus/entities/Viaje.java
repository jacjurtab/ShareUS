package com.shareus.entities;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Viaje {
	
	//TODO: todos los atributos tipo int son "id", cambiar a uuid
	private int id;   
	private int conductor;  
	private int origen;
	private int destino;
	private Date fecha;
	private Time hora;
	private int num_pasajeros;
	private int max_plazas;
	private List<String> pasajeros;  //nombres de los pasajeros asociados al viaje
	private float valoracion;  //Representa a nota media del conductor
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getConductor() {
		return conductor;
	}
	public void setConductor(int conductor) {
		this.conductor = conductor;
	}
	public int getOrigen() {
		return origen;
	}
	public void setOrigen(int origen) {
		this.origen = origen;
	}
	public int getDestino() {
		return destino;
	}
	public void setDestino(int destino) {
		this.destino = destino;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Time getHora() {
		return hora;
	}
	public void setHora(Time hora) {
		this.hora = hora;
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
	public List<String> getPasajeros() {
		return pasajeros;
	}
	public void setPasajeros(List<String> pasajeros) {
		this.pasajeros = pasajeros;
	}
	public float getValoracion() {
		return valoracion;
	}
	public void setValoracion(float valoracion) {
		this.valoracion = valoracion;
	}
}

