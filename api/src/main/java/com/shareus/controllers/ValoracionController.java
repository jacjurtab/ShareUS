package com.shareus.controllers;

import com.shareus.ShareUSApplication;
import com.shareus.models.Valoracion;
import com.shareus.models.daos.ValoracionesDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ValoracionController {

    private final ValoracionesDAO valoraciones;

    public ValoracionController() {
        valoraciones = new ValoracionesDAO(ShareUSApplication.DS);
    }

    @GetMapping("/valoracion")
    public List<Valoracion> valoraciones(
            @RequestParam(value = "viaje")Integer viaje,
            @RequestParam(value = "valorado")Integer valorado
    ) {
        return valoraciones.obtenerValoraciones(viaje, valorado);
    }

    @PostMapping(path = "/valoracion", consumes = "application/json")
    public boolean insertar(
            @RequestBody Valoracion valoracion
    ) {
        int valorador = Integer.parseInt(valoracion.getValorador());
        return valoraciones.insertarViajeValoracion(valoracion.getViaje(), valorador, valoracion.getValorado(), (int) valoracion.getNota());
    }
}
