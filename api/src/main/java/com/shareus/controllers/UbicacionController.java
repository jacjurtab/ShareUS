package com.shareus.controllers;

import com.shareus.ShareUSApplication;
import com.shareus.models.Ubicacion;
import com.shareus.models.daos.UbicacionesDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UbicacionController {

    private final UbicacionesDAO ubicaciones;

    public UbicacionController() {
        ubicaciones = new UbicacionesDAO(ShareUSApplication.DS);
    }

    @GetMapping("/ubicaciones")
    public List<Ubicacion> ubicaciones(
            @RequestParam(value = "disponible", required = false) Boolean disponible,
            @RequestParam(value = "tipo", defaultValue = "0") int tipo
    ) {
        return ubicaciones.obtenerUbicaciones(disponible, tipo);
    }

}
