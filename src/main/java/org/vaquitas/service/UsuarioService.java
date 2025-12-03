package org.vaquitas.service;

import org.vaquitas.model.Usuario;
import org.vaquitas.repository.UsuarioRepository;
import org.vaquitas.util.UsuarioValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Usuario}.
 * <p>
 * Se encarga de coordinar la validación y la persistencia de datos.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;

    /**
     * Constructor que inyecta las dependencias de los repositorios y validadores.
     *
     * @param usuarioRepository Repositorio para operaciones de Usuario.
     * @param usuarioValidator Validador para lógica de negocio de Usuario.
     */
    public UsuarioService (UsuarioRepository usuarioRepository, UsuarioValidator usuarioValidator){
        this.usuarioRepository = usuarioRepository;
        this.usuarioValidator = usuarioValidator;
    }

    /**
     * Registra un nuevo usuario, incluyendo la validación de duplicados (email y teléfono).
     *
     * @param usuario El objeto {@link Usuario} a registrar. La clave debe estar hasheada.
     * @return Un mapa de errores si existen duplicados, o un mapa vacío si el registro fue exitoso.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Map<String,String> registrarUsuario(Usuario usuario) throws SQLException{
        Map<String,String> validar = usuarioValidator.validarDuplicados(usuario.getTelefono(), usuario.getEmail());
        if (!validar.isEmpty())
            return validar;
        usuarioRepository.save(usuario);
        return validar; // Retorna el mapa vacío
    }

    /**
     * Obtiene la lista completa de usuarios.
     *
     * @return Una lista de objetos {@link Usuario}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Usuario> verUsuario() throws SQLException{
        return usuarioRepository.findAll();
    }

    /**
     * Actualiza el email y la clave (ya hasheada) de un usuario existente.
     *
     * @param usuario El objeto {@link Usuario} con el ID y la nueva información.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public void actualizarUsuario(Usuario usuario) throws SQLException{
        usuarioRepository.update(usuario);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param idUsuario El ID del usuario a eliminar.
     * @return El número de filas afectadas (0 si no existe, 1 si fue eliminado).
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public int eliminarUsuario(int idUsuario) throws SQLException{
        return usuarioRepository.deleter(idUsuario);
    }

    /**
     * Busca y recupera un usuario por su ID.
     *
     * @param idUsuario El ID del usuario a buscar.
     * @return El objeto {@link Usuario} o {@code null} si no es encontrado.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Usuario encontrarUsuario(int idUsuario) throws SQLException{
        return usuarioRepository.findUsuario(idUsuario);
    }

    /**
     * Busca un usuario por su email para obtener la clave hasheada para la autenticación.
     *
     * @param usuario El objeto {@link Usuario} que contiene el email de login.
     * @return El objeto {@link Usuario} con ID, email y clave hasheada, o {@code null} si no existe.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Usuario autenticarUsuario(Usuario usuario) throws SQLException{
        return usuarioRepository.findByEmailPsw(usuario);
    }
}