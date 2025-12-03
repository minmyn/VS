package org.vaquitas.util;

import org.vaquitas.model.Usuario;
import org.vaquitas.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria para validar los datos de la entidad {@link Usuario}.
 * <p>
 * Incluye validación de campos obligatorios y validación de unicidad en la base de datos.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class UsuarioValidator {
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor que inyecta la dependencia del repositorio para realizar validaciones en la base de datos.
     *
     * @param usuarioRepository El repositorio de usuarios.
     */
    public UsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Valida que todos los campos obligatorios del usuario cumplan con las reglas de negocio básicas.
     *
     * @param usuario El objeto {@link Usuario} a validar.
     * @return Un mapa de errores. Retorna un mapa vacío si no hay errores.
     */
    public Map<String, String> validarUsuario(Usuario usuario) {
        Map<String, String> errores = new HashMap<>();

        if (usuario.getNombre() == null || usuario.getNombre().isBlank())
            errores.put("nombre", "El nombre es obligatorio.");

        if (usuario.getTelefono() == null || usuario.getTelefono().isBlank() || usuario.getTelefono().length() != 10)
            errores.put("telefono", "El teléfono debe tener 10 dígitos.");

        if (usuario.getSexo() == null || (!"Masculino".equals(usuario.getSexo()) && !"Femenino".equals(usuario.getSexo())))
            errores.put("sexo", "El sexo debe ser 'Masculino' o 'Femenino'.");

        if (usuario.getEdad() < 18)
            errores.put("edad", "El usuario debe ser mayor de 18 años.");

        if (usuario.getEmail() == null || usuario.getEmail().isBlank())
            errores.put("email", "El email es obligatorio.");

        if (usuario.getClave() == null || usuario.getClave().isBlank())
            errores.put("clave", "La clave es obligatoria.");

        return errores;
    }

    /**
     * Valida que el email y la clave no estén vacíos al momento de actualizar las credenciales de un usuario.
     *
     * @param usuario El objeto {@link Usuario} a verificar.
     * @return Un mapa de errores.
     */
    public Map<String, String> validarActualizacion(Usuario usuario) {
        Map<String, String> errores = new HashMap<>();
        if (usuario.getEmail() == null || usuario.getEmail().isBlank())
            errores.put("email", "El email es obligatorio para la actualización.");

        if (usuario.getClave() == null || usuario.getClave().isBlank())
            errores.put("clave","La clave es obligatoria para la actualización.");
        return errores;
    }

    /**
     * Valida que el teléfono y el email no estén duplicados en la base de datos.
     *
     * @param telefono El número de teléfono a verificar.
     * @param email El correo electrónico a verificar.
     * @return Un mapa de errores con mensajes específicos para campos duplicados.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public Map<String, String> validarDuplicados(String telefono, String email) throws SQLException {
        Map<String, String> errores = new HashMap<>();
        if (usuarioRepository.findTelefono(telefono))
            errores.put("telefono", "Teléfono duplicado");
        if (usuarioRepository.findEmail(email))
            errores.put("email", "Email duplicado");
        return errores;
    }
}
