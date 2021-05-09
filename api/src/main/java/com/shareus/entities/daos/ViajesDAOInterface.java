package com.shareus.entities.daos;

import java.sql.Timestamp;
import java.util.List;

import com.shareus.entities.Viaje;

public interface ViajesDAOInterface {
	/**
	 * Añade al pasajero especificado a un viaje especifico.
	 * @param viaje del viaje
	 * @return viaje cuyo id corresponde con el indicado
	 */
	public boolean insertarPasajeroViaje(int viaje, int pasajero);

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

}
