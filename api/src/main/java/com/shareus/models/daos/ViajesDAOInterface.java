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
	 * @param viaje del viaje
	 * @param valorador que asigna puntación
	 * @param valorado que obtiene la valoración
	 * @param nota que contiene la puntiación del valorador
	 */
	boolean insertarViajeValoracion(int viaje, int valorador, int valorado, int nota);

	/**
	 * Añade una nueva valoración a un usuario específico.
	 * @param conductor del viaje
	 * @param origen del viaje
	 * @param destino del viaje
	 * @param fecha timestamp del dia y hora
	 * @param max_plazas numero de plazas maximo
	 */
	boolean insertarViajeConductor(int conductor, int origen, int destino, Timestamp fecha, int max_plazas);
  
  /**
	 * Obtiene todos los viajes realizados por un usuario como conductor 
	 * @param conductor del usuario que actúa como conductor del viaje
	 * @return lista de todos los viajes realizados por un usuario 
	 * (lista vacia si no tiene viajes asociados)
	 */
	List<Viaje> obtenerViajesConductor(int conductor);
	
	/**
	 * Obtiene todos los viajes de un usuario como pasajero
	 * @param pasajero del usuario
	 * @return lista de todos los viajes en los que está inscrito el usuario 
	 * (lista vacia si no tiene viajes asociados)
	 */
	List<Viaje> obtenerViajesPasajero(int pasajero);
	
	/**
	 * Obtiene el viaje cuyo id corresponde con el indicado como parametro
	 * @param idViaje del viaje
	 * @return viaje cuyo id corresponde con el indicado (null si no lo encuentra)
	 */
	Viaje obtenerViajeId(int idViaje);
	
	/**
	 * Obtiene el historial de viajes (historial completo o solo viajes disponibles,
	 * es decir, viaves que tengan alguna plaza libre y que no hayan prescrito
	 * @param disponibles disponibles (si es true solo se obtienen los viajes
	 * con fecha posterior a la actual)
	 * @return lista de viajes o lista de viajes disponibles
	 */
	List<Viaje> obtenerViajes(boolean disponibles);

	/**
	 * Obtiene una lista de valoraciones para un viaje especifico y un usuario.
	 * @param viaje id del viaje en el que se han hecho las valoraciones.
	 * @param valorado  usuario del cual se esta accediendo a las valoraciones
	 * @return lista de valoraciones
	 */
	List<Valoracion> obtenerValoraciones(int viaje, int valorado);

}
