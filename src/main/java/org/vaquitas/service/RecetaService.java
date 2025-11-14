package org.vaquitas.service;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Consulta;
import org.vaquitas.model.DTOdetalles;
import org.vaquitas.model.Receta;
import org.vaquitas.model.Recordatorio;
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


    public RecetaService(RecetaRepository recetaRepository, RecordatorioRepository recordatorioRepository, ConsultaRepository consultaRepository) {
        this.recetaRepository = recetaRepository;
        this.recordatorioRepository = recordatorioRepository;
        this.consultaRepository = consultaRepository;
    }

    // ...
    public void guardarReceta(Receta receta) throws SQLException {

        try (Connection connection = DatabaseConfig.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try {
                Consulta consulta = receta.getConsulta();
                int idConsulta = consultaRepository.save(consulta);

                // 2. Manejar Recordatorio
                int idRecordatorio;
                // Guardamos una referencia al objeto original (el que viene en el JSON)
                Recordatorio recordatorioASalvar = receta.getRecordatorio();
                // Buscamos si ya existe en la base de datos
                Recordatorio recordatorioEncontrado = recordatorioRepository.search(recordatorioASalvar);

                if (recordatorioEncontrado == null) {
                    // Si NO existe, lo guardamos y obtenemos su ID
                    idRecordatorio = recordatorioRepository.save(recordatorioASalvar);
                } else {
                    // Si SÍ existe, usamos el ID del encontrado
                    idRecordatorio = recordatorioEncontrado.getIdRecordatorio();
                }

                // 3. Guardar Receta
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
// ...

    public List<Receta> verRecetas() throws SQLException {
        return recetaRepository.findAll();
    }


    public List<DTOdetalles> verDetallesRecetas() throws SQLException {
        return recetaRepository.findAllDetalles();
    }
}