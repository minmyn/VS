package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.UsuarioControl;
import org.vaquitas.model.Usuario;

/**
 * Clase de enrutamiento que define todos los {@code endpoints} relacionados con la gestión de {@link Usuario}.
 * <p>
 * Asigna rutas HTTP específicas a los métodos correspondientes en el {@link UsuarioControl}.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class UsuarioRoute {
    private final UsuarioControl usuarioControl;

    /**
     * Constructor que inyecta la dependencia del controlador.
     *
     * @param usuarioControl El controlador que maneja la lógica de la petición.
     */
    public UsuarioRoute(UsuarioControl usuarioControl){
        this.usuarioControl=usuarioControl;
    }

    /**
     * Registra todos los {@code endpoints} de usuario en la aplicación Javalin.
     *
     * <table>
     * <caption>Endpoints de Usuario</caption>
     * <tr><th>Método</th><th>Ruta</th><th>Propósito</th></tr>
     * <tr><td>POST</td><td>/usuarios</td><td>Registra un nuevo usuario.</td></tr>
     * <tr><td>POST</td><td>/usuarios/login</td><td>Autentica un usuario (Login) y retorna un token JWT.</td></tr>
     * <tr><td>GET</td><td>/usuarios</td><td>Recupera el listado completo de usuarios.</td></tr>
     * <tr><td>GET</td><td>/usuarios/{id}</td><td>Recupera un usuario por su ID.</td></tr>
     * <tr><td>PATCH</td><td>/usuarios/{id}</td><td>Actualiza email y clave de un usuario por su ID.</td></tr>
     * <tr><td>DELETE</td><td>/usuarios/{id}</td><td>Elimina un usuario por su ID.</td></tr>
     * </table>
     *
     * @param app La instancia de {@link Javalin} donde se registrarán las rutas.
     */
    public void register(Javalin app){
        app.post("/usuarios", usuarioControl::registrarUsuario);
        app.get("/usuarios", usuarioControl::verUsuario);
        app.patch("/usuarios/{id}", usuarioControl::editarUsuario);
        app.get("/usuarios/{id}", usuarioControl::encontrarUsuario);
        app.delete("/usuarios/{id}", usuarioControl::eliminarUsuario);
        app.post("/usuarios/login" , usuarioControl::autenticarUsuario);
    }
}