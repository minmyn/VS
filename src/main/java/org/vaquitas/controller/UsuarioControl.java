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

/**
 * Controlador para la gestión de {@link Usuario} (Registro, Autenticación y CRUD).
 * <p>
 * Maneja la recepción de peticiones HTTP, la validación de entrada, el hasheo de claves
 * y la coordinación con la capa de servicio y el gestor de tokens.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class UsuarioControl {

    private final UsuarioService usuarioService;
    private final UsuarioValidator usuarioValidator;
    private final TokenManager tokenManager;

    /**
     * Constructor que inyecta las dependencias del servicio, validador y gestor de tokens.
     */
    public UsuarioControl(UsuarioService usuarioService, TokenManager tokenManager, UsuarioValidator usuarioValidator){
        this.usuarioService = usuarioService;
        this.usuarioValidator = usuarioValidator;
        this.tokenManager = tokenManager;
    }

    /**
     * Registra un nuevo usuario.
     * <p>Procesa la petición POST a {@code /usuarios}.</p>
     *
     * @param context El contexto de la petición HTTP.
     */
    public void registrarUsuario(Context context){
        try{
            Usuario nuevoUsuario = context.bodyAsClass(Usuario.class);

            Map<String,String> errores = usuarioValidator.validarUsuario(nuevoUsuario);
            if (!errores.isEmpty()){
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            String claveHash = Password.hash(nuevoUsuario.getClave()).withBcrypt().getResult();
            nuevoUsuario.setClave(claveHash);

            errores = usuarioService.registrarUsuario(nuevoUsuario);
            if (!errores.isEmpty()){
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }


            context.status(201).json(Map.of("estado", true, "mensaje", "Usuario registrado con éxito", "data", nuevoUsuario));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera el listado completo de todos los usuarios.
     * <p>Procesa la petición GET a {@code /usuarios}.</p>
     *
     * @param context El contexto de la petición HTTP.
     */
    public void verUsuario(Context context){
        try {
            List<Usuario> usuarios= usuarioService.verUsuario();
            context.status(200).json(usuarios);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Actualiza el email y la clave de un usuario por su ID.
     * <p>Procesa la petición PATCH a {@code /usuarios/{id}}.</p>
     *
     * @param context El contexto de la petición HTTP.
     */
    public void  editarUsuario(Context context){
        try {
            int idUsuario = Integer.parseInt(context.pathParam("id"));
            Usuario usuarioUpdate = context.bodyAsClass(Usuario.class);

            Map<String,String> errores = usuarioValidator.validarActualizacion(usuarioUpdate);
            if (!errores.isEmpty()){
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            String claveHash = Password.hash(usuarioUpdate.getClave()).withBcrypt().getResult();
            usuarioUpdate.setClave(claveHash);
            usuarioUpdate.setIdUsuario(idUsuario);

            usuarioService.actualizarUsuario(usuarioUpdate);

            context.status(201).json(Map.of("estado", true, "mensaje", "Usuario actualizado con éxito", "data", usuarioUpdate));
        }catch (NumberFormatException e) {
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID de usuario debe ser un número entero válido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Elimina un usuario por su ID.
     * <p>Procesa la petición DELETE a {@code /usuarios/{id}}.</p>
     *
     * @param context El contexto de la petición HTTP.
     */
    public void eliminarUsuario(Context context){
        try {
            int idUsuario=Integer.parseInt(context.pathParam("id"));
            int filasAfectadas = usuarioService.eliminarUsuario(idUsuario);

            if (filasAfectadas == 0) {
                context.status(404).json(Map.of("estado", false, "mensaje", "Usuario inexistente para eliminar."));
                return;
            }

            context.status(204);
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID de usuario debe ser un número entero válido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Busca y recupera un usuario por su ID.
     * <p>Procesa la petición GET a {@code /usuarios/{id}}.</p>
     *
     * @param context El contexto de la petición HTTP.
     */
    public void encontrarUsuario(Context context){
        try {
            int idUsuario=Integer.parseInt(context.pathParam("id"));
            Usuario usuario = usuarioService.encontrarUsuario(idUsuario);

            if (usuario==null){
                context.status(404).json(Map.of("estado", false, "mensaje", "Usuario inexistente"));
                return;
            }

            usuario.setClave(null); // Seguridad: No retornar la clave hasheada.
            context.status(200).json(usuario);
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID de usuario debe ser un número entero válido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Autentica un usuario con email y clave.
     * <p>Procesa la petición POST a {@code /usuarios/login}.</p>
     *
     * @param context El contexto de la petición HTTP.
     */
    public void autenticarUsuario(Context context) {
        Usuario usuarioLogin = context.bodyAsClass(Usuario.class);
        try {
            Usuario usuario = usuarioService.autenticarUsuario(usuarioLogin);

            if (usuario == null){
                context.status(401).json(Map.of("estado", false, "mensaje", "Usuario no encontrado."));
                return;
            }

            boolean claveCorrecta = Password.check(usuarioLogin.getClave(), usuario.getClave()).withBcrypt();

            if (claveCorrecta){
                String token = tokenManager.issueToken(String.valueOf(usuario.getIdUsuario()));

                context.status(200).json(Map.of(
                        "idUser",usuario.getIdUsuario(),
                        "token", token,
                        "estado", true,
                        "mensaje", "Autenticación exitosa."
                ));
            }else{
                context.status(401).json(Map.of(
                        "estado", false,
                        "mensaje", "Contraseña incorrecta."
                ));
            }
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}