package org.vaquitas.util;

import org.vaquitas.model.Usuario;
import org.vaquitas.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UsuarioValidator {
    private final UsuarioRepository usuarioRepository; // Se agrega la dependencia

    // Constructor para inyectar UsuarioRepository
    public UsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Map<String, String> validarUsuario(Usuario usuario){
        Map<String, String> errores = new HashMap<>();
        if (usuario.getNombre() == null || usuario.getNombre().isBlank())
            errores.put("nombre", "Verifique el campo");
        if( usuario.getTelefono() == null || usuario.getTelefono().isBlank())
            errores.put("telefono", "Verifique el campo");
        if (!"Masculino".equals(usuario.getSexo()) && !"Femenino".equals(usuario.getSexo()))
            errores.put("sexo", "Verifique el campo");
        Integer n = usuario.getEdad();
        if (n == null || n < 18) // Se valida si la edad es null o menor a 18
            errores.put("edad", "Verifique el campo");
        if (usuario.getEmail() == null || usuario.getEmail().isBlank())
            errores.put("email", "Verifique el campo");
        if (usuario.getClave() == null || usuario.getClave().isBlank())
            errores.put("clave","Verifique el campo");
        if (usuario.getClave() != null && usuario.getClave().length() < 12) // Se agrega null check antes de length()
            errores.put("clave","Demasiado corta"); // Corregido "Demaciado"
        return errores;
    }

    public Map<String, String> validarDuplicados(String telefono, String email) throws SQLException {
        Map<String, String> errores = new HashMap<>();
        if (usuarioRepository.findTelefono(telefono))
            errores.put("telefono", "telefono duplicado");
        if (usuarioRepository.findEmail(email))
            errores.put("email", "email duplicado");
        return errores;
    }
}