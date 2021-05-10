package com.shareus.entities;

public class Valoracion {
	private int id;
	private String valorador;
	private int viaje;
	private String comentario;
	private float nota;

	public Valoracion(int id, int viaje, String valorador, String comentario, float nota) {
		super();
		this.id = id;
		this.valorador = valorador;
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

	public String getValorador() {
		return valorador;
	}

	public void setValorador(String valorador) {
		this.valorador = valorador;
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
