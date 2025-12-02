package org.vaquitas.service;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Consulta;
import org.vaquitas.model.DTOdetalles;
import org.vaquitas.model.Receta;
import org.vaquitas.model.Recordatorio;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.ConsultaRepository;
import org.vaquitas.repository.RecetaRepository;
import org.vaquitas.repository.RecordatorioRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import java.util.List;

public class RecetaService {
    private final RecetaRepository recetaRepository;
    private final RecordatorioRepository recordatorioRepository;
    private final ConsultaRepository consultaRepository;
    private final AnimalRepository animalRepository;


    public RecetaService(RecetaRepository recetaRepository,
                         RecordatorioRepository recordatorioRepository,
                         ConsultaRepository consultaRepository,
                         AnimalRepository animalRepository) {
        this.recetaRepository = recetaRepository;
        this.recordatorioRepository = recordatorioRepository;
        this.consultaRepository = consultaRepository;
        this.animalRepository = animalRepository;
    }

    public void guardarReceta(Receta receta) throws SQLException {

        try (Connection connection = DatabaseConfig.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try {
                Consulta consulta = receta.getConsulta();
                int idConsulta = consultaRepository.save(consulta);
                int idRecordatorio;
                Recordatorio recordatorioASalvar = receta.getRecordatorio();
                Recordatorio recordatorioEncontrado = recordatorioRepository.search(recordatorioASalvar);
                if (recordatorioEncontrado == null) {
                    idRecordatorio = recordatorioRepository.save(recordatorioASalvar);
                } else {
                    idRecordatorio = recordatorioEncontrado.getIdRecordatorio();
                }
                if (animalRepository.validateCuidado(consulta.getGanado().getIdArete()))
                    throw new IllegalArgumentException("El ganado no existe ó no esta activo");
                recetaRepository.save(receta, idConsulta, idRecordatorio);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public List<DTOdetalles> verDetallesRecetas() throws SQLException {
        return recetaRepository.findAllDetalles();
    }

    public List<DTOdetalles> verRecetaPorMedicamento(int id) throws SQLException {
        return recetaRepository.findRecetaByMedicina(id);
    }


}