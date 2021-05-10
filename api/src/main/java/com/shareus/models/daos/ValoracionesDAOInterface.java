package com.shareus.models.daos;

import com.shareus.models.Valoracion;

import java.util.List;

public interface ValoracionesDAOInterface {


    /**
     * Obtiene una lista de valoraciones para un viaje especifico y un usuario.
     * @param viaje id del viaje en el que se han hecho las valoraciones.
     * @param valorado  usuario del cual se esta accediendo a las valoraciones
     * @return lista de valoraciones
     */
    List<Valoracion> obtenerValoraciones(int viaje, int valorado);

    /**
     * Añade una nueva valoración a un usuario específico.
     * @param viaje del viaje
     * @param valorador que asigna puntación
     * @param valorado que obtiene la valoración
     * @param nota que contiene la puntiación del valorador
     */
    boolean insertarViajeValoracion(int viaje, int valorador, int valorado, int nota);

}
