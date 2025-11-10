package org.vaquitas.controller;

import org.vaquitas.model.Usuario;
import org.vaquitas.service.UsuarioService;

import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.password4j.Password;
import org.vaquitas.util.Error;
import org.vaquitas.util.UsuarioValidator;

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
            UsuarioValidator usuarioValidator = new UsuarioValidator();
            Map<String,String> errores = usuarioValidator.validarUsuario(nuevoUsuario);
            if (!errores.isEmpty()){
                context.status(400).json(Map.of("errores", errores));
                return;
            }
            String claveHash = Password.hash(nuevoUsuario.getClave()).withBcrypt().getResult();
            nuevoUsuario.setClave(claveHash);
            errores = usuarioService.registrarUsuario(nuevoUsuario);
            if (!errores.isEmpty()){
                context.status(400).json(Map.of("errores", errores));
                return;
            }
            context.status(201).json(nuevoUsuario);
        } catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void verUsuario(Context context){
        try {
            List<Usuario> usuarios= usuarioService.verUsuario();
            context.json(usuarios);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
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
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void eliminarUsuario(Context context){
        try {
            int idUsuario=Integer.parseInt(context.pathParam("id"));
            usuarioService.eliminarUsuario(idUsuario);
            context.status(204);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
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
//        } catch (SQLException e) {
//            context.status(403).json(Map.of(
//                    "error", "usuario no valido",
//                    "estado", false
//            ));
//        } catch (Exception e) {
//            System.err.println("Error inesperado en la autenticación: " + e.getMessage());
//            context.status(500);
//        }
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}