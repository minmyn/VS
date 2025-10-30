package org.vaquitas.controller;

import org.vaquitas.model.Usuario;
import org.vaquitas.service.UsuarioService;

import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.password4j.Password;

public class UsuarioControl {

    private final UsuarioService usuarioService;
    private final TokenManager tokenManager;
    public UsuarioControl(UsuarioService usuarioService, TokenManager tokenManager){
        this.usuarioService=usuarioService;
        this.tokenManager = tokenManager;
    }

    public void registrarUsuario(Context context){
        try{
            Usuario nuevoUsuario = context.bodyAsClass(Usuario.class);
            String claveHash = Password.hash(nuevoUsuario.getClave()).withBcrypt().getResult();
            nuevoUsuario.setClave(claveHash);
            usuarioService.registrarUsuario(nuevoUsuario);
            context.status(201);
            context.json(nuevoUsuario);
        }catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                context.status(409);
            } else {
                System.err.println("Error de SQL: " + e.getMessage());
                context.status(500);
            }
        } catch (Exception e) {
            context.status(400).result("Petición inválida: " + e.getMessage());
        }
    }

    public void verUsuario(Context context){
        try {
            List<Usuario> usuarios= usuarioService.verUsuario();
            context.json(usuarios);
        } catch (SQLException e) {
            context.status(500).result("Error al obtener la lista de usuarios" + e.getMessage());
        }
    }

    public void  editarUsuario(Context context){
        try {
            int idUsuario=Integer.parseInt(context.pathParam("id"));
            Usuario usuarioUpdate = context.bodyAsClass(Usuario.class);
            String claveHash = Password.hash(usuarioUpdate.getClave()).withBcrypt().getResult();
            usuarioUpdate.setClave(claveHash);
            usuarioUpdate.setIdUsuario(idUsuario);
            usuarioService.actualizarUsuario(usuarioUpdate);
            context.status(200);
            context.json(usuarioUpdate);
        }catch (NumberFormatException e) {
            context.status(400);
        } catch (SQLException e) {
            e.printStackTrace();
            context.status(500); // 500 Internal Server Error
        } catch (Exception e) {
            context.status(400);
        }
    }

    public void eliminarUsuario(Context context){
        try {
            int idUsuario=Integer.parseInt(context.pathParam("id"));
            usuarioService.eliminarUsuario(idUsuario);
            context.status(204);
        } catch (SQLException e) {
            e.printStackTrace();
            context.status(500);
        }
    }

    public void autenticarUsuario(Context context) {
        Usuario usuarioLogin = context.bodyAsClass(Usuario.class);
        try {
            Usuario usuario = usuarioService.autenticarUsuario(usuarioLogin);
            if (usuario == null){
                throw new SQLException("Usuariono encontrado");
            }
            boolean clave = Password.check(usuarioLogin.getClave(),usuario.getClave()).withBcrypt();
            if (clave){
                String token = tokenManager.issueToken(usuario.getIdUsuario()+"");
                context.json(Map.of(
                        "idUser",usuario.getIdUsuario(),
                        "token", token,
                        "estado", true,
                        "mensaje", "Usuario encontrado"
                ));
            }else{
                context.status(400).result("Usuario no encontrado");
            }
        } catch (SQLException e) {
            context.status(403).json(Map.of(
                    "error", "usuario no valido",
                    "estado", false
            ));
        } catch (Exception e) {
            System.err.println("Error inesperado en la autenticación: " + e.getMessage());
            context.status(500);
        }
    }
}