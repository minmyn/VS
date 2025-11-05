package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Receta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecetaRepository {

    public void save(Receta receta, int idConsulta, int idRecordatorio) throws SQLException {
        String sql ="INSERT INTO RECETA (consulta_id, medicamento_id, calendario_id, dosis) VALUES (?,?,?,?)";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idConsulta);
            statement.setInt(2, receta.getIdMedicamento());
            statement.setInt(3, idRecordatorio);
            statement.setDouble(4, receta.getDosis());
            statement.executeUpdate();
        }
    }
}
