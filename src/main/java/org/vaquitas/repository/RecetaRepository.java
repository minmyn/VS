package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Consulta;
import org.vaquitas.model.DTOdetalles;
import org.vaquitas.model.Receta;
import org.vaquitas.model.Recordatorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de repositorio que maneja las operaciones de persistencia (CRUD) para la entidad {@link Receta}.
 * <p>
 * Es responsable de las uniones (JOINs) complejas para recuperar los detalles consolidados.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RecetaRepository {

    /**
     * Persiste un nuevo registro de receta en la tabla RECETA.
     * <p>
     * Nota: Utiliza una conexión nueva. Esta operación debe ser parte de una transacción
     * gestionada externamente por la capa de Servicio.
     * </p>
     *
     * @param receta El objeto {@link Receta} con la información de la dosis y fecha.
     * @param idConsulta El ID de la {@link Consulta} recién creada (FK).
     * @param idRecordatorio El ID del {@link Recordatorio} (FK).
     * @throws SQLException Si ocurre un error durante la ejecución de la sentencia SQL.
     */
    public void save(Receta receta, int idConsulta, int idRecordatorio) throws SQLException {
        String sql ="INSERT INTO RECETA (consulta_id, medicamento_id, calendario_id, dosis, fecha_inicio) VALUES (?,?,?,?,?)";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idConsulta);
            statement.setInt(2, receta.getMedicamento().getIdMedicamento());
            statement.setInt(3, idRecordatorio);
            statement.setInt(4, receta.getDosis());
            statement.setDate(5, Date.valueOf(receta.getFechaInicio()));
            statement.executeUpdate();
        }
    }

    /**
     * Recupera una lista de recetas (solo los campos de la tabla RECETA).
     * <p>
     * Este método es menos utilizado que {@code findAllDetalles()} ya que no recupera las entidades asociadas.
     * </p>
     *
     * @return Una lista de objetos {@link Receta}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Receta> findAll() throws SQLException{
        List<Receta> recetas = new ArrayList<>();
        String sql = "SELECT * FROM RECETA";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Receta receta = new Receta();
                receta.setDosis(resultSet.getInt("dosis"));
                Date fechaSql = resultSet.getDate("fecha_inicio");
                if (fechaSql != null) {
                    receta.setFechaInicio(fechaSql.toLocalDate());
                }
                recetas.add(receta);
            }
        }
        return recetas;
    }

    /**
     * Realiza una consulta compleja para obtener los detalles consolidados de todas las recetas.
     * <p>
     * Realiza JOINs a las tablas ANIMAL, CONSULTA, MEDICAMENTO y RECORDATORIO para construir el DTO.
     * </p>
     *
     * @return Una lista de objetos {@link DTOdetalles}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<DTOdetalles> findAllDetalles() throws SQLException {
        List<DTOdetalles> detalles = new ArrayList<>();
        String sql ="SELECT ANIMAL.arete_id, " +
                "ANIMAL.nombre AS nombre_animal, " +
                "CONSULTA.padecimiento, " +
                "MEDICAMENTO.nombre AS nombre_medicamento, " +
                "CONSULTA.consulta_id, " +
                "RECETA.dosis, RECETA.fecha_inicio, " +
                "RECORDATORIO.fecha AS fecha_recordatorio " +
                "FROM ANIMAL " +
                "JOIN CONSULTA ON ANIMAL.arete_id = CONSULTA.arete_id " +
                "JOIN RECETA ON CONSULTA.consulta_id = RECETA.consulta_id " +
                "JOIN MEDICAMENTO ON RECETA.medicamento_id = MEDICAMENTO.medicamento_id " +
                "JOIN RECORDATORIO ON RECETA.calendario_id = RECORDATORIO.calendario_id " +
                "ORDER BY RECETA.fecha_inicio DESC;";

        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                detalles.add(mapDetalles(resultSet));
            }
        }
        return detalles;
    }

    /**
     * Realiza una consulta compleja para obtener los detalles de las recetas asociadas a un medicamento específico.
     *
     * @param id El ID del medicamento.
     * @return Una lista de objetos {@link DTOdetalles}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<DTOdetalles> findRecetaByMedicina(int id) throws SQLException {
        List<DTOdetalles> detalles = new ArrayList<>();
        String sql = "SELECT " +
                "ANIMAL.arete_id, " +
                "ANIMAL.nombre AS nombre_animal, " +
                "CONSULTA.padecimiento, " +
                "MEDICAMENTO.nombre AS nombre_medicamento, " +
                "CONSULTA.consulta_id,"+
                "RECETA.dosis, " +
                "RECETA.fecha_inicio, " +
                "RECORDATORIO.fecha AS fecha_recordatorio " +
                "FROM ANIMAL " +
                "JOIN CONSULTA ON ANIMAL.arete_id = CONSULTA.arete_id " +
                "JOIN RECETA ON CONSULTA.consulta_id = RECETA.consulta_id " +
                "JOIN MEDICAMENTO ON RECETA.medicamento_id = MEDICAMENTO.medicamento_id " +
                "JOIN RECORDATORIO ON RECETA.calendario_id = RECORDATORIO.calendario_id "+
                "WHERE MEDICAMENTO.medicamento_id = ?";

        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                detalles.add(mapDetalles(resultSet));
            }
        }
        return detalles;
    }

    /**
     * Realiza una consulta compleja para obtener los detalles de las recetas asociadas a un recordatorio específico.
     *
     * @param id El ID del recordatorio (calendario_id).
     * @return Una lista de objetos {@link DTOdetalles}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<DTOdetalles> findRecetaByFecha(int id) throws SQLException {
        List<DTOdetalles> detalles = new ArrayList<>();
        String sql = "SELECT " +
                "ANIMAL.arete_id, " +
                "ANIMAL.nombre AS nombre_animal, " +
                "CONSULTA.padecimiento, " +
                "MEDICAMENTO.nombre AS nombre_medicamento, " +
                "CONSULTA.consulta_id,"+
                "RECETA.dosis, " +
                "RECETA.fecha_inicio, " +
                "RECORDATORIO.fecha AS fecha_recordatorio " +
                "FROM ANIMAL " +
                "JOIN CONSULTA ON ANIMAL.arete_id = CONSULTA.arete_id " +
                "JOIN RECETA ON CONSULTA.consulta_id = RECETA.consulta_id " +
                "JOIN MEDICAMENTO ON RECETA.medicamento_id = MEDICAMENTO.medicamento_id " +
                "JOIN RECORDATORIO ON RECETA.calendario_id = RECORDATORIO.calendario_id "+
                "WHERE RECETA.calendario_id = ?";

        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                detalles.add(mapDetalles(resultSet));
            }
        }
        return detalles;
    }

    /**
     * Método auxiliar privado para mapear un {@code ResultSet} a un objeto {@link DTOdetalles}.
     *
     * @param resultSet El conjunto de resultados de la consulta SQL.
     * @return Un objeto {@link DTOdetalles} completamente mapeado.
     * @throws SQLException Si ocurre un error al leer del {@code ResultSet}.
     */
    private DTOdetalles mapDetalles(ResultSet resultSet) throws SQLException{
        DTOdetalles dto = new DTOdetalles();
        dto.setNumeroReceta(resultSet.getInt("consulta_id"));
        dto.setAreteId(resultSet.getInt("arete_id"));
        dto.setNombreAnimal(resultSet.getString("nombre_animal"));
        dto.setPadecimiento(resultSet.getString("padecimiento"));
        dto.setNombreMedicamento(resultSet.getString("nombre_medicamento"));
        dto.setDosis(resultSet.getInt("dosis"));
        Date fechaInicioSql = resultSet.getDate("fecha_inicio");
        if (fechaInicioSql != null) {
            dto.setFechaInicioReceta(fechaInicioSql.toLocalDate());
        }
        Date fechaRecordatorioSql = resultSet.getDate("fecha_recordatorio");
        if (fechaRecordatorioSql != null) {
            dto.setFechaRecordatorio(fechaRecordatorioSql.toLocalDate());
        }
        return dto;
    }
}