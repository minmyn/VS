package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Receta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Receta> findAll() throws SQLException{
        List<Receta> recetas = new ArrayList<>();
        String sql = "SELECT * FROM RECETA";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Receta receta = new Receta();
                receta.setIdConsulta(resultSet.getInt("consulta_id"));
                receta.setDosis(resultSet.getDouble("dosis"));
                receta.setIdMedicamento(resultSet.getInt("medicamento_id"));
                receta.setIdRecordatorio(resultSet.getInt("calendario_id"));
                recetas.add(receta);
            }
        }
        return recetas;
    }
}
