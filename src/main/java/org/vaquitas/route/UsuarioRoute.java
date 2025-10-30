package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.UsuarioControl;

public class UsuarioRoute {
    private final UsuarioControl usuarioControl;

    public UsuarioRoute(UsuarioControl usuarioControl){
        this.usuarioControl=usuarioControl;
    }

    public void register(Javalin app){
        app.post("/usuarios", usuarioControl::registrarUsuario);
        app.get("/usuarios", usuarioControl::verUsuario);
        app.patch("/usuarios/{id}", usuarioControl::editarUsuario);
        app.delete("usuarios/{id}", usuarioControl::eliminarUsuario);
        app.post("/usuarios/login" , usuarioControl::autenticarUsuario);
    }

}