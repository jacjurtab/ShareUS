package com.shareus.entities;

public class Valoracion {
	private int id;
	private int valorador;
	private int valorado; 
	private int viaje;
	private String comentario;
	private float nota;
	
	public Valoracion() {
		
	}
	
	public Valoracion(int id, int valorador, int valorado, int viaje, String comentario, float nota) {
		super();
		this.id = id;
		this.valorador = valorador;
		this.valorado = valorado;
		this.viaje = viaje;
		this.comentario = comentario;
		this.nota = nota;
  }
 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getValorador() {
		return valorador;
	}
	public void setValorador(int valorador) {
		this.valorador = valorador;
	}
	public int getValorado() {
		return valorado;
	}
	public void setValorado(int valorado) {
		this.valorado = valorado;
	}
	public int getViaje() {
		return viaje;
	}
	public void setViaje(int viaje) {
		this.viaje = viaje;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public float getNota() {
		return nota;
	}
	public void setNota(float nota) {
		this.nota = nota;
	}
	
	

}
