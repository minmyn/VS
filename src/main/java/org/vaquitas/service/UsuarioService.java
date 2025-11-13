package org.vaquitas.service;

import com.password4j.Password;
import org.vaquitas.model.Usuario;
import org.vaquitas.repository.UsuarioRepository;
//import org.vaquitas.util.UsuarioValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    public UsuarioService (UsuarioRepository usuarioRepository){
        this.usuarioRepository=usuarioRepository;
    }

//    public Map<String,String> registrarUsuario(Usuario usuario) throws SQLException{
////        UsuarioValidator usuarioValidator = new UsuarioValidator();
////        Map<String,String> validar = usuarioValidator.validarDuplicados(usuario.getTelefono(), usuario.getEmail());
////        if (!validar.isEmpty())
////            return validar;
//        usuarioRepository.save(usuario);
////        return validar;
//    }

    public List<Usuario> verUsuario() throws SQLException{
        return usuarioRepository.findAll();
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException{
        usuarioRepository.update(usuario);
    }

    public int eliminarUsuario(int idUsuario) throws SQLException{
        return usuarioRepository.deleter(idUsuario);
    }

    public Usuario econtrarUsuaio(int idUsuario) throws SQLException{
        return usuarioRepository.findUsuario(idUsuario);
    }

    public Usuario autenticarUsuario(Usuario usuario) throws SQLException{
        return usuarioRepository.findByEmailPsw(usuario);
    }
}