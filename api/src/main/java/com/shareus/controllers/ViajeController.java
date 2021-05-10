package com.shareus.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.shareus.ShareUSApplication;
import com.shareus.models.Viaje;
import com.shareus.models.VistasViaje;
import com.shareus.models.daos.ViajesDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
            @RequestParam(value = "conductor", required = false) int conductor,
            @RequestParam(value = "pasajero", required = false) int pasajero
    ) {
        if(conductor > 0) {
            return viajes.obtenerViajesConductor(conductor);
        } else if(pasajero > 0) {
            return viajes.obtenerViajesPasajero(pasajero);
        }
        return null;
    }

    @GetMapping("/viaje/{id}")
    @JsonView(VistasViaje.Completo.class)
    public Viaje viaje(
            @PathVariable(value = "id") int viaje
    ) {
        return viajes.obtenerViajeId(viaje);
    }
}
