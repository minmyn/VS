package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Raza;
import org.vaquitas.model.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de repositorio que maneja las operaciones de persistencia para la entidad {@link Venta}.
 * <p>
 * También maneja la consulta consolidada de ventas y la validación de venta previa.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class VentaRepository {

    /**
     * Persiste un nuevo registro de venta.
     *
     * @param venta El objeto {@link Venta} a guardar.
     * @throws SQLException Si ocurre un error de base de datos o si no se afecta ninguna fila.
     */
    public void save(Venta venta) throws SQLException{
        String sql="INSERT INTO VENTA (arete_id, precio_venta, peso_final, fecha_baja) VALUES (?,?,?,?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, venta.getGanado().getIdArete());
            statement.setDouble(2, venta.getPrecioVenta());
            statement.setDouble(3, venta.getPesoFinal());
            Date sqlDate = Date.valueOf(venta.getFechaBaja());
            statement.setDate(4, sqlDate);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción de la venta no afectó ninguna fila.");
            }
        }
    }

    /**
     * Recupera una lista de todos los animales vendidos, incluyendo detalles del animal, raza y la venta.
     * <p>
     * Realiza JOINs entre VENTA, ANIMAL y RAZA. Se asume que el estado del animal en la tabla ANIMAL
     * se actualiza a 'Vendido' tras registrar la venta.
     * </p>
     *
     * @return Una lista de objetos {@link Venta} con el campo {@code ganado} (Animal) poblado.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Venta> findVendidos() throws SQLException{
        List<Venta> animal = new ArrayList<>();
        String sql="SELECT v.venta_id, a.nombre, a.arete_id, a.sexo, a.raza_id, rz.nombre AS raza_nombre, a.fecha_nacimiento, a.peso, v.peso_final, v.precio_venta, v.fecha_baja " +
                "FROM ANIMAL a " +
                "INNER JOIN VENTA v ON a.arete_id = v.arete_id " +
                "INNER JOIN RAZA rz ON a.raza_id = rz.raza_id " +
                "WHERE a.estado = 'Vendido' ORDER BY a.arete_id";

        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()) {
                Venta ganado = new Venta();
                Animal bovino = new Animal();
                Raza raza = new Raza();

                // Mapeo de Venta
                ganado.setIdVenta(resultSet.getInt("venta_id"));
                ganado.setPrecioVenta(resultSet.getDouble("precio_venta"));
                ganado.setPesoFinal(resultSet.getDouble("peso_final"));
                Date sqlDateBaja = resultSet.getDate("fecha_baja");
                ganado.setFechaBaja(sqlDateBaja.toLocalDate());

                // Mapeo de Animal
                bovino.setNombre(resultSet.getString("nombre"));
                bovino.setIdArete(resultSet.getInt("arete_id"));
                bovino.setSexo(resultSet.getString("sexo"));
                Date sqlDateNac = resultSet.getDate("fecha_nacimiento");
                bovino.setFechaNacimiento(sqlDateNac.toLocalDate());
                bovino.setPeso(resultSet.getDouble("peso"));

                // Mapeo de Raza
                raza.setIdRaza(resultSet.getInt("raza_id"));
                raza.setNombreRaza(resultSet.getString("raza_nombre"));
                bovino.setRaza(raza);

                ganado.setGanado(bovino);
                animal.add(ganado);
            }
            return animal;
        }
    }

    /**
     * Actualiza el precio de venta y el peso final de una venta existente.
     *
     * @param venta El objeto {@link Venta} con los nuevos valores y el ID del animal.
     * @return El número de filas afectadas.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public int update(Venta venta) throws SQLException {
        String sql = "UPDATE VENTA SET precio_venta = ?, peso_final = ? WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, venta.getPrecioVenta());
            statement.setDouble(2, venta.getPesoFinal());
            statement.setInt(3, venta.getGanado().getIdArete());
            return statement.executeUpdate();
        }
    }

    /**
     * Verifica si un animal ya tiene un registro de venta en la tabla VENTA.
     * <p>
     * Se utiliza como microservicio o validación para evitar registrar ventas duplicadas.
     * </p>
     *
     * @param idArete El ID del arete del animal a verificar.
     * @return {@code true} si el animal ya fue vendido (existe en la tabla VENTA), {@code false} en caso contrario.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public boolean findVendido(int idArete)throws SQLException{
        String sql = "SELECT arete_id FROM VENTA WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, idArete);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
}