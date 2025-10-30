package org.vaquitas.service;

import com.password4j.Password;
import org.vaquitas.model.Usuario;
import org.vaquitas.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    public UsuarioService (UsuarioRepository usuarioRepository){
        this.usuarioRepository=usuarioRepository;
    }

    public void registrarUsuario(Usuario usuario) throws SQLException{
        usuarioRepository.save(usuario);
    }

    public List<Usuario> verUsuario() throws SQLException{
        return usuarioRepository.findAll();
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException{
        usuarioRepository.update(usuario);
    }

    public int eliminarUsuario(int idUsuario) throws SQLException{
        return usuarioRepository.deleter(idUsuario);
    }

    public Usuario autenticarUsuario(Usuario usuario) throws SQLException{
        return usuarioRepository.findByEmailPsw(usuario);
    }
}