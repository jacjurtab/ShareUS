package com.shareus.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.shareus.ShareUSApplication;
import com.shareus.models.Viaje;
import com.shareus.models.VistasViaje;
import com.shareus.models.daos.ViajesDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
public class ViajeController {

    private final ViajesDAO viajes;

    public ViajeController() {
        viajes = new ViajesDAO(ShareUSApplication.DS);
    }

    @GetMapping("/viajes")
    @JsonView(VistasViaje.Simple.class)
    public List<Viaje> viajes(
            @RequestParam(value = "conductor", required = false) Integer conductor,
            @RequestParam(value = "pasajero", required = false) Integer pasajero,
            @RequestParam(value = "disponible", required = false) Boolean disponible
    ) {
        if(conductor != null) {
            return viajes.obtenerViajesConductor(conductor);
        } else if(pasajero != null) {
            return viajes.obtenerViajesPasajero(pasajero);
        } else if(disponible != null) {
            return viajes.obtenerViajes(disponible);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "parámetro conductor o pasajero es necesario");
        }
    }

    @GetMapping("/viaje/{id}")
    @JsonView(VistasViaje.Completo.class)
    public Viaje viaje(
            @PathVariable(value = "id") Integer viaje
    ) {
        return viajes.obtenerViajeId(viaje);
    }
    
    @DeleteMapping("/viaje/{id}")
    public boolean borrar(
    		@PathVariable(value = "id") Integer viaje
    ) {
		return viajes.eliminarViaje(viaje);
    }    
    
    @PutMapping(path = "/viaje/{id}/pasajeros/eliminar")
    public boolean eliminarPasajero(
    		@PathVariable(value = "id") Integer viaje,
    		@RequestBody  String pasajero    		
    ) {
    	return viajes.eliminarPasajeroViaje(viaje, Integer.parseInt(pasajero)); 	
    }
       
    @PutMapping(path = "/viaje/{id}/pasajeros/insertar")
    public boolean insertaPasajero(
    		@PathVariable(value = "id") Integer viaje,
    		@RequestBody  String pasajero    		
    ) {
        return viajes.insertarPasajeroViaje(viaje, Integer.parseInt(pasajero));
    }

    @PostMapping(path = "/viaje", consumes = "application/json")
    public boolean insertar(
            @RequestBody Viaje viaje
    ) {
        Integer conductor = Integer.parseInt(viaje.getConductor());
        Integer origen = Integer.parseInt(viaje.getOrigen());
        Integer destino = Integer.parseInt(viaje.getDestino());

        return viajes.insertarViajeConductor(conductor, origen, destino, viaje.getFecha_hora(), viaje.getMax_plazas());
    }
}