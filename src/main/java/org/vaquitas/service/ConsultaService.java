package org.vaquitas.service;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Consulta;
import org.vaquitas.model.Receta;
import org.vaquitas.model.Recordatorio;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.ConsultaRepository;
import org.vaquitas.repository.RecetaRepository;
import org.vaquitas.repository.RecordatorioRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final RecetaRepository recetaRepository;
    private final RecordatorioRepository recordatorioRepository;
    private final AnimalRepository animalRepository;

    public ConsultaService(RecetaRepository recetaRepository, RecordatorioRepository recordatorioRepository, ConsultaRepository consultaRepository, AnimalRepository animalRepository) {
        this.consultaRepository=consultaRepository;
        this.recetaRepository=recetaRepository;
        this.recordatorioRepository=recordatorioRepository;
        this.animalRepository=animalRepository;
    }

    public void registrarConsulta(Consulta consulta, Receta receta, Recordatorio recordatorio) throws SQLException{
        try(Connection connection = DatabaseConfig.getDataSource().getConnection()){
            connection.setAutoCommit(false);
            try {
                if (animalRepository.existsByIdArete(consulta.getIdArete()))
                    throw new IllegalArgumentException("Arete duplicado");
                int idConsulta = consultaRepository.save(consulta);
                int idRecordatorio = recordatorioRepository.save(recordatorio);
                recetaRepository.save(receta, idConsulta, idRecordatorio);
                connection.commit();
            }catch (SQLException e){
                connection.rollback();
                throw e;
            }finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public List<Consulta> verConsultas() throws SQLException{
        return consultaRepository.findAll();
    }
}
