package org.vaquitas.controller;

import org.vaquitas.model.Usuario;
import org.vaquitas.service.UsuarioService;

import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.password4j.Password;
import org.vaquitas.util.Error;
import org.vaquitas.util.UsuarioValidator;

public class UsuarioControl {

    private final UsuarioService usuarioService;
    private final UsuarioValidator usuarioValidator; 
    private final TokenManager tokenManager;

    public UsuarioControl(UsuarioService usuarioService, TokenManager tokenManager, UsuarioValidator usuarioValidator){
        this.usuarioService = usuarioService;
        this.usuarioValidator = usuarioValidator;
        this.tokenManager = tokenManager;
    }

    public void registrarUsuario(Context context){
        try{

            Usuario nuevoUsuario = context.bodyAsClass(Usuario.class);

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

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("estado", true);
            respuesta.put("data", nuevoUsuario);
            context.status(201).json(respuesta);

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

            int idUsuario = Integer.parseInt(context.pathParam("id"));
            Usuario usuarioUpdate = context.bodyAsClass(Usuario.class);

            if (usuarioUpdate.getClave() == null || usuarioUpdate.getClave().isBlank()) {
                context.status(400).json(Map.of("error", "La clave no puede estar vacía."));
                return;
            }

            String claveHash = Password.hash(usuarioUpdate.getClave()).withBcrypt().getResult();
            usuarioUpdate.setClave(claveHash);
            usuarioUpdate.setIdUsuario(idUsuario);
            usuarioService.actualizarUsuario(usuarioUpdate);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("estado", true);
            respuesta.put("data", usuarioUpdate);
            context.status(201).json(respuesta);

        }catch (NumberFormatException e) {
            context.status(400).json(Map.of("error", "ID de usuario inválido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void eliminarUsuario(Context context){
        try {

            int idUsuario=Integer.parseInt(context.pathParam("id"));
            int filasAfectadas = usuarioService.eliminarUsuario(idUsuario);
            if (filasAfectadas == 0) {
                context.status(404).json("Usuario inexistente para eliminar.");
                return;
            }
            context.status(204);

        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("error", "ID de usuario inválido.")); // Manejo más claro
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void encontrarUsuario(Context context){
        try {

            int idUsuario=Integer.parseInt(context.pathParam("id"));
            Usuario usuario = usuarioService.encontrarUsuario(idUsuario);

            if (usuario==null){
                context.status(404).json("Usuario inexistente");
                return;
            }

            usuario.setClave(null);
            context.status(200).json(usuario);

        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("error", "ID de usuario inválido."));
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
                context.status(401).json(Map.of(
                        "estado", false,
                        "mensaje", "Usuario no encontrado"
                ));
                return;
            }

            boolean claveCorrecta = Password.check(usuarioLogin.getClave(), usuario.getClave()).withBcrypt();

            if (claveCorrecta){

                String token = tokenManager.issueToken(usuario.getIdUsuario()+"");
                context.json(Map.of(
                        "idUser",usuario.getIdUsuario(),
                        "token", token,
                        "estado", true,
                        "mensaje", "Usuario encontrado"
                ));

            }else{

                context.status(401).json(Map.of(
                        "estado", false,
                        "mensaje", "Contraseña incorrecta. Inténtalo de nuevo."
                ));

            }

        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}
