package com.shareus.entities.daos;

import java.util.List;

import com.shareus.entities.Viaje;

public interface ViajesDAOInterface {
	
	/**
	 * Obtiene todos los viajes realizados por un usuario como conductor 
	 * @param id del usuario que actúa como conductor del viaje
	 * @return lista de todos los viajes realizados por un usuario 
	 * (lista vacia si no tiene viajes asociados)
	 */
	public List<Viaje> obtenerViajesConductor(int conductor);
	
	/**
	 * Obtiene todos los viajes de un usuario como pasajero
	 * @param id del usuario 
	 * @return lista de todos los viajes en los que está inscrito el usuario 
	 * (lista vacia si no tiene viajes asociados)
	 */
	public List<Viaje> obtenerViajesPasajero(int pasajero);
	
	/**
	 * Obtiene el viaje cuyo id corresponde con el indicado como parametro
	 * @param id del viaje
	 * @return viaje cuyo id corresponde con el indicado (null si no lo encuentra)
	 */
	public Viaje obtenerViajeId(int idViaje);
	
	/**
	 * Obtiene el historial de viajes (historial completo o solo viajes disponibles,
	 * es decir, viaves que tengan alguna plaza libre y que no hayan prescrito
	 * @param boolean disponibles (si es true solo se obtienen los viajes 
	 * con fecha posterior a la actual)
	 * @return lista de viajes o lista de viajes disponibles
	 */
	public List<Viaje> obtenerViajes(boolean disponibles);

}
