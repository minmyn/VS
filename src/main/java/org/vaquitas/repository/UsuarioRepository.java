package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import  java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de repositorio que maneja las operaciones de persistencia (CRUD) para la entidad {@link Usuario}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class UsuarioRepository {

    /**
     * Persiste un nuevo usuario en la tabla USUARIO.
     * <p>
     * **Advertencia:** La clave debe estar hasheada antes de llamar a este método.
     * </p>
     *
     * @param usuario El objeto {@link Usuario} a guardar.
     * @throws SQLException Si ocurre un error de base de datos o si no se afecta ninguna fila.
     */
    public void save(Usuario usuario)  throws SQLException {
        String sql = "INSERT INTO USUARIO (nombre, telefono, sexo, edad, correo_electronico, clave_acceso) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getTelefono());
            statement.setString(3, usuario.getSexo());
            statement.setInt(4, usuario.getEdad());
            statement.setString(5, usuario.getEmail());
            //statement.setString(6, usuario.getClave());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del usuario no afectó ninguna fila.");
            }
        }
    }

    /**
     * Recupera todos los usuarios de la base de datos (excluyendo la clave de acceso en el mapeo).
     *
     * @return Una lista de objetos {@link Usuario}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Usuario> findAll() throws SQLException{
        List<Usuario> usuario = new ArrayList<>();
        String sql = "SELECT usuario_id, nombre, telefono, sexo, edad, correo_electronico, clave_acceso FROM USUARIO";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Usuario usuarios = new Usuario();
                usuarios.setIdUsuario(resultSet.getInt("usuario_id"));
                usuarios.setNombre(resultSet.getString("nombre"));
                usuarios.setTelefono(resultSet.getString("telefono"));
                usuarios.setSexo(resultSet.getString("sexo"));
                usuarios.setEdad(resultSet.getInt("edad"));
                usuarios.setEmail(resultSet.getString("correo_electronico"));
                //usuarios.setClave(resultSet.getString("clave_acceso"));
                usuario.add(usuarios);
            }
            return usuario;
        }
    }

    /**
     * Busca y recupera un único usuario por su ID.
     *
     * @param idUsuario El ID del usuario a buscar.
     * @return El objeto {@link Usuario} con todos sus campos, o {@code null}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Usuario findUsuario(int idUsuario)throws  SQLException{
        String sql = "SELECT usuario_id, nombre, telefono, sexo, edad, correo_electronico, clave_acceso FROM USUARIO WHERE usuario_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(1, idUsuario );
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuarioBD = new Usuario();
                    usuarioBD.setIdUsuario(resultSet.getInt("usuario_id"));
                    usuarioBD.setNombre(resultSet.getString("nombre"));
                    usuarioBD.setTelefono(resultSet.getString("telefono"));
                    usuarioBD.setSexo(resultSet.getString("sexo"));
                    usuarioBD.setEdad(resultSet.getInt("edad"));
                    usuarioBD.setEmail(resultSet.getString("correo_electronico"));
                    //usuarioBD.setClave(resultSet.getString("clave_acceso")); // Incluido el campo 'clave_acceso'
                    return usuarioBD;
                }
                return null;
            }
        }
    }

    /**
     * Actualiza el email y la clave (hasheada) de un usuario.
     *
     * @param usuario El objeto {@link Usuario} con el ID y los campos a actualizar.
     * @return El número de filas afectadas (1 si fue exitoso, 0 si el usuario no existe).
     * @throws SQLException Si ocurre un error de base de datos.
     */
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

    /**
     * Elimina un usuario por su ID.
     *
     * @param idUsuario El ID del usuario a eliminar.
     * @return El número de filas eliminadas (1 si existe, 0 si no existe).
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public int deleter(int idUsuario) throws SQLException{
        String sql = "DELETE FROM USUARIO WHERE usuario_id = ?";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, idUsuario);
            return statement.executeUpdate();
        }
    }

    /**
     * Busca un usuario por su email para obtener la clave hasheada y el ID (para autenticación).
     *
     * @param usuario El objeto {@link Usuario} que contiene el email a buscar.
     * @return El objeto {@link Usuario} con ID, email y clave hasheada, o {@code null}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Usuario findByEmailPsw(Usuario usuario) throws SQLException{
        String sql = "SELECT usuario_id, correo_electronico, clave_acceso FROM USUARIO WHERE correo_electronico = ?";
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

    /**
     * Verifica si un correo electrónico ya existe en la base de datos (Validación).
     *
     * @param email El correo electrónico a verificar.
     * @return {@code true} si el email ya existe, {@code false} en caso contrario.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public boolean findEmail(String email) throws SQLException{
        String sql = "SELECT 1 FROM USUARIO WHERE correo_electronico = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    /**
     * Verifica si un número de teléfono ya existe en la base de datos (Validación).
     *
     * @param telefono El número de teléfono a verificar.
     * @return {@code true} si el teléfono ya existe, {@code false} en caso contrario.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public boolean findTelefono(String telefono)throws SQLException{
        String sql = "SELECT 1 FROM USUARIO WHERE telefono =?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, telefono);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
}