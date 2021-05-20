package com.shareus.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.shareus.ShareUSApplication;
import com.shareus.models.Viaje;
import com.shareus.models.Vistas;
import com.shareus.models.daos.ViajesDAO;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class ViajeController {

    private final ViajesDAO viajes;

    public ViajeController() {
        viajes = new ViajesDAO(ShareUSApplication.DS);
    }

    @GetMapping("/viajes")
    @JsonView(Vistas.Simple.class)
    public List<Viaje> viajes(
            @RequestParam(value = "conductor", required = false) Integer conductor,
            @RequestParam(value = "pasajero", required = false) Integer pasajero,
            @RequestParam(value = "disponibles", required = false) Boolean disponibles,
            @RequestParam(value = "vencidos", required = false) Boolean vencidos
    ) {
        if(conductor != null) {
            return viajes.obtenerViajesConductor(conductor, vencidos);
        } else if(pasajero != null) {
            return viajes.obtenerViajesPasajero(pasajero, vencidos);
        } else{
            return viajes.obtenerViajes(disponibles);
        }/* else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "parámetro conductor o pasajero es necesario");
        }*/
    }

    @GetMapping("/viaje/{id}")
    @JsonView(Vistas.Completo.class)
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
        Timestamp ts = new Timestamp(viaje.getFecha_hora());
        Float precio = viaje.getPrecio();

        return viajes.insertarViajeConductor(conductor, origen, destino, ts, viaje.getMax_plazas(), precio);
    }
}
