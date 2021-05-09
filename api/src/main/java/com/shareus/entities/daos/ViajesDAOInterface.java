package com.shareus.entities.daos;

import java.util.List;

import com.shareus.entities.Viaje;

public interface ViajesDAOInterface {
	
	/**
	 * Obtiene todos los viajes realizados por un usuario como conductor 
	 * @param id del usuario que actúa como conductor del viaje
	 * @return lista de todos los viajes realizados por un usuario
	 */
	public List<Viaje> obtenerViajeConductor(int conductor);
	
	/**
	 * Obtiene todos los viajes de un usuario como pasajero
	 * @param id del usuario 
	 * @return lista de todos los viajes en los que está inscrito el usuario
	 */
	public List<Viaje> obtenerViajePasajero(int pasajero);
	
	/**
	 * Obtiene el viaje cuyo id corresponde con el indicado como parametro
	 * @param id del viaje
	 * @return viaje cuyo id corresponde con el indicado
	 */
	public Viaje obtenerViajeId(int viaje);
	
	public List<Viaje> obtenerViajesDisponibles();
	//public void obtenerViajePasajero();
	public List<Viaje> obtenerViajes();

	//public boolean insertarPasajeroViaje(...);
	//public boolean eliminarPasajeroViaje(int pasajero);
	//public boolean eliminarViajeId(int viaje);
	//public boolean insertarViajeValoracion(...);
	//public boolean insertarViajeConductor(...);

}
