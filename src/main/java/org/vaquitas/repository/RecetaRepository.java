package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.DTOdetalles;
import org.vaquitas.model.Receta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecetaRepository {

    public void save(Receta receta, int idConsulta, int idRecordatorio) throws SQLException {
        // Asumo que tu tabla RECETA tiene 5 campos
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


    public List<Receta> findAll() throws SQLException{
        List<Receta> recetas = new ArrayList<>();
        String sql = "SELECT * FROM RECETA";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Receta receta = new Receta();
                receta.setDosis(resultSet.getInt("dosis"));
                // CORRECCIÓN: Asumiendo que la columna es "fecha_inicio", no "fecha_baja"
                Date fechaSql = resultSet.getDate("fecha_inicio");
                if (fechaSql != null) {
                    receta.setFechaInicio(fechaSql.toLocalDate());
                }
                // Nota: Los objetos anidados (Consulta, Medicamento, Recordatorio) no se están cargando aquí.
                recetas.add(receta);
            }
        }
        return recetas;
    }

    public List<DTOdetalles> findAllDetalles() throws SQLException {
        List<DTOdetalles> detalles = new ArrayList<>();

        // Se usan ALIAS (AS) en los nombres duplicados (como 'nombre' y 'fecha') para evitar ambigüedad.
        String sql = "SELECT " +
                "ANIMAL.arete_id, " +
                "ANIMAL.nombre AS nombre_animal, " +
                "CONSULTA.padecimiento, " +
                "MEDICAMENTO.nombre AS nombre_medicamento, " +
                "RECETA.dosis, " +
                "RECETA.fecha_inicio, " +
                "RECORDATORIO.fecha AS fecha_recordatorio " +
                "FROM ANIMAL " +
                "JOIN CONSULTA ON ANIMAL.arete_id = CONSULTA.arete_id " +
                "JOIN RECETA ON CONSULTA.consulta_id = RECETA.consulta_id " +
                "JOIN MEDICAMENTO ON RECETA.medicamento_id = MEDICAMENTO.medicamento_id " +
                "JOIN RECORDATORIO ON RECETA.calendario_id = RECORDATORIO.calendario_id";

        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DTOdetalles dto = new DTOdetalles();

                dto.setAreteId(resultSet.getLong("arete_id"));
                dto.setNombreAnimal(resultSet.getString("nombre_animal"));
                dto.setPadecimiento(resultSet.getString("padecimiento"));
                dto.setNombreMedicamento(resultSet.getString("nombre_medicamento"));
                dto.setDosis(resultSet.getInt("dosis"));

                // Mapeo de fechas (usando los alias o nombres de columna)
                Date fechaInicioSql = resultSet.getDate("fecha_inicio");
                if (fechaInicioSql != null) {
                    dto.setFechaInicioReceta(fechaInicioSql.toLocalDate());
                }

                Date fechaRecordatorioSql = resultSet.getDate("fecha_recordatorio");
                if (fechaRecordatorioSql != null) {
                    dto.setFechaRecordatorio(fechaRecordatorioSql.toLocalDate());
                }

                detalles.add(dto);
            }
        }
        return detalles;
    }
}