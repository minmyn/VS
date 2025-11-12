package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import  java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    //AGREGAR O REGISTRAR USUARIO
    public void save(Usuario usuario)  throws SQLException {
        String sql = "INSERT INTO USUARIO (nombre, telefono, sexo, edad, correo_electronico, clave_acceso) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getTelefono());
            statement.setString(3, usuario.getSexo());
            statement.setInt(4, usuario.getEdad());
            statement.setString(5, usuario.getEmail());
            statement.setString(6, usuario.getClave());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del encargado no afectó ninguna fila.");
            }
        }
    }

    //VER TODOS LOS USUARIOS AGRAGADOS
    public List<Usuario> findAll() throws SQLException{
        List<Usuario> usuario = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Usuario usuarios = new Usuario();//int usersql = resultSet.getInt("usuario_id");//if(usersql!=1) {
                usuarios.setIdUsuario(resultSet.getInt("usuario_id"));
                usuarios.setNombre(resultSet.getString("nombre"));
                usuarios.setTelefono(resultSet.getString("telefono"));
                usuarios.setSexo(resultSet.getString("sexo"));
                usuarios.setEdad(resultSet.getInt("edad"));//usuarios.setPuesto(usuarios.getPuesto());
                usuario.add(usuarios); //}
            }
            return usuario;
        }
    }
    //ENCONTRAR UN SOLO USUARIO Y VERLO
    public Usuario findUsuario(int idUsuario)throws  SQLException{
        String sql = "SELECT * FROM USUARIO WHERE usuario_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(1, idUsuario );
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuarioBD = new Usuario();
                    usuarioBD.setNombre(resultSet.getString("nombre"));
                    usuarioBD.setTelefono(resultSet.getString("telefono"));
                    usuarioBD.setSexo(resultSet.getString("sexo"));
                    usuarioBD.setEdad(resultSet.getInt("edad"));
                    usuarioBD.setEmail(resultSet.getString("correo_electronico"));
                    return usuarioBD;
                }
                return null;
            }
        }
    }
    //ACTUALIZAR USUARIO
    public int update(Usuario usuario) throws SQLException{
        String sql = "UPDATE USUARIO SET correo_electronico = ?, clave_acceso = ? WHERE usuario_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, usuario.getEmail());
            statement.setString(2, usuario.getClave());
            statement.setInt(3, usuario.getIdUsuario());
            return statement.executeUpdate();
        }
    }
    //ELIMINAR UN USUARIO
    public int deleter(int idUsuario) throws SQLException{
        String sql = "DELETE FROM USUARIO WHERE usuario_id = ?";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, idUsuario);
            return statement.executeUpdate();
        }
    }

    //AUTENTICACION DE USUARIO
    public Usuario findByEmailPsw(Usuario usuario) throws SQLException{
        String sql = "SELECT * FROM USUARIO WHERE correo_electronico = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, usuario.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Usuario usuarioBase = new Usuario();
                usuarioBase.setIdUsuario(resultSet.getInt("usuario_id"));
                usuarioBase.setEmail(resultSet.getString("correo_electronico"));
                usuarioBase.setClave(resultSet.getString("clave_acceso"));
                return usuarioBase;
            }
        }
        return null;
    }

    //MICROSERVICIOS Y VALIDACIONES PARA USUARIOS
    public boolean findEmail(String email) throws SQLException{
        String sql = "SELECT * FROM USUARIO WHERE correo_electronico = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return true;
        }
        return false;
    }
    public boolean findTelefono(String telefeno)throws SQLException{
        String sql = "SELECT * FROM USUARIO WHERE telefono =?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, telefeno);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return true;
        }
        return false;
    }
}