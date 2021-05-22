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

    @PostMapping("/usuario")
    @JsonView(Vistas.Simple.class)
    public Usuario iniciar(
            @RequestBody String email
    ) {
        Usuario usuario = usuarios.obtenerUsuario(email);
        if(usuario == null && usuarios.inicializarUsuario(email)) {
            usuario = usuarios.obtenerUsuario(email);
        }

        if (usuario.getNombre() == null) usuario.setFirstTime(true);

        usuario.setToken("TOKEN");

        return usuario;
    }

    @GetMapping("/usuario/{id}")
    @JsonView(Vistas.Completo.class)
    public Usuario obtener(
            @PathVariable Integer id
    ) {
        return usuarios.obtenerUsuario(id);
    }

    @PutMapping(path="/usuario/{id}/completar", consumes = "application/json")
    @JsonView(Vistas.Simple.class)
    public boolean completar(
            @PathVariable Integer id,
            @RequestBody Usuario usuario
    ) {
        return usuarios.completarUsuario(id, usuario.getNombre(), usuario.getPrimer_apellido(),
                usuario.getSegundo_apellido(), usuario.getTelefono());
    }

}
