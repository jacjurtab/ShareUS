package com.shareus.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario {

	@JsonView(Vistas.Simple.class)
	private int id;
	@JsonView(Vistas.Simple.class)
	private String usuario;
	@JsonView(Vistas.Completo.class)
	private String nombre;
	@JsonView(Vistas.Completo.class)
	private String primer_apellido;
	@JsonView(Vistas.Completo.class)
	private String segundo_apellido;
	@JsonView(Vistas.Simple.class)
	private String email;
	@JsonView(Vistas.Completo.class)
	private String telefono;
	@JsonView(Vistas.Completo.class)
	private float valoracion;
	@JsonView(Vistas.Simple.class)
	private String token;
	@JsonView(Vistas.Simple.class)
	private boolean firstTime;

	public Usuario(int id, String usuario, String email) {
		this.id = id;
		this.usuario = usuario;
		this.email = email;
	}

	public Usuario() {

	}

	public Usuario(int id, String usuario, String nombre, String primer_apellido, String segundo_apellido, String email, String telefono, float valoracion) {
		this.id = id;
		this.usuario = usuario;
		this.nombre = nombre;
		this.primer_apellido = primer_apellido;
		this.segundo_apellido = segundo_apellido;
		this.email = email;
		this.telefono = telefono;
		this.valoracion = valoracion;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	public String getPrimer_apellido() {
		return primer_apellido;
	}
	public void setPrimer_apellido(String primer_apellido) {
		this.primer_apellido = primer_apellido;
	}
	public String getSegundo_apellido() {
		return segundo_apellido;
	}
	public void setSegundo_apellido(String segundo_apellido) {
		this.segundo_apellido = segundo_apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public float getValoracion() {
		return valoracion;
	}
	public void setValoracion(float valoracion) {
		this.valoracion = valoracion;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isFirstTime() {
		return firstTime;
	}
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	
}
