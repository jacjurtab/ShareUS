package com.shareus.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.shareus.ShareUSApplication;
import com.shareus.models.Usuario;
import com.shareus.models.Vistas;
import com.shareus.models.daos.UsuariosDAO;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    private final UsuariosDAO usuarios;

    public UsuarioController() {
        usuarios = new UsuariosDAO(ShareUSApplication.DS);
    }

    @PostMapping("/usuario/inicializar")
    public String iniciar(
            @RequestBody String email
    ) {
        if(usuarios.obtenerUsuario(email) == null) {
            return usuarios.inicializarUsuario(email) ? "TOKEN" : "false";
        } else {
            //TODO: Generar token.
            return "TOKEN";
        }
    }

    @GetMapping("/usuario/{id}")
    @JsonView(Vistas.Completo.class)
    public Usuario obtener(
            @PathVariable Integer id
    ) {
        return usuarios.obtenerUsuario(id);
    }

    @PutMapping(path="/usuario/completar", consumes = "application/json")
    @JsonView(Vistas.Simple.class)
    public boolean completar(
            @RequestBody Usuario usuario
    ) {
        return usuarios.completarUsuario(usuario.getId(), usuario.getNombre(), usuario.getPrimer_apellido(),
                usuario.getSegundo_apellido(), usuario.getTelefono());
    }

}
