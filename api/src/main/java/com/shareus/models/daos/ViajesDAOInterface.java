package com.shareus.models.daos;

import java.sql.Timestamp;
import java.util.List;

import com.shareus.models.Valoracion;
import com.shareus.models.Viaje;

public interface ViajesDAOInterface {
	/**
	 * Añade al pasajero especificado a un viaje especifico.
	 * @param viaje del viaje
	 * @return viaje cuyo id corresponde con el indicado
	 */
	boolean insertarPasajeroViaje(int viaje, int pasajero);

	/**
	 * Elimina el pasajero especificado del viaje especifico.
	 * @param viaje del viaje
	 * @return viaje cuyo id corresponde con el indicado
	 */
	boolean eliminarPasajeroViaje(int viaje, int pasajero);

	/**
	 * Elimina el viaje cuyo id corresponde con el indicado como parametro.
	 * @param viaje del viaje
	 * @return viaje cuyo id corresponde con el indicado
	 */
	boolean eliminarViaje(int viaje);

	/**
	 * Añade una nueva valoración a un usuario específico.
	 * @param conductor del viaje
	 * @param origen del viaje
	 * @param destino del viaje
	 * @param fecha timestamp del dia y hora
	 * @param max_plazas numero de plazas maximo
	 * @param precio del viaje
	 */
	public boolean insertarViajeConductor(Integer conductor, Integer origen, Integer destino, Timestamp fecha, Integer max_plazas, Float precio);
  
  /**
	 * Obtiene todos los viajes realizados por un usuario como conductor 
	 * @param conductor del usuario que actúa como conductor del viaje
	 * @param vencidos 
	 * @return lista de los viajes de un usuario como conductor
	 * (si vencidos es true solo devuelve viajes pasados,
	 * si vencidos es false solo devuelve viajes futuros,
	 * si vencidos es null devuelve viejes pasador y fururos)
	 * (lista vacia si no tiene viajes asociados)
	 * (lista vacia si no tiene viajes asociados)
	 */
	List<Viaje> obtenerViajesConductor(int conductor, Boolean vencidos);
	
	/**
	 * Obtiene todos los viajes de un usuario como pasajero
	 * @param pasajero del usuario
	 * @param vencidos 
	 * @return lista los viajes en los que se ha inscrito el usuario 
	 * (si vencidos es true solo devuelve viajes pasados,
	 * si vencidos es false solo devuelve viajes futuros,
	 * si vencidos es null devuelve viejes pasador y fururos)
	 * (lista vacia si no tiene viajes asociados)
	 */
	
	List<Viaje> obtenerViajesPasajero(int pasajero, Boolean vencidos);
	
	/**
	 * Obtiene el viaje cuyo id corresponde con el indicado como parametro
	 * @param idViaje del viaje
	 * @return viaje cuyo id corresponde con el indicado (null si no lo encuentra)
	 */
	Viaje obtenerViajeId(int idViaje);
	
	/**
	 * Obtiene el historial de viajes (historial completo o solo viajes disponibles,
	 * es decir, viaves que tengan alguna plaza libre y que no hayan prescrito
	 * @param disponibles (si es true solo se obtienen los viajes
	 * con fecha posterior a la actual)
	 * @return lista de viajes o lista de viajes disponibles
	 */
	List<Viaje> obtenerViajes(Boolean disponibles);

}
