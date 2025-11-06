package org.vaquitas.service;

import org.vaquitas.model.Recordatorio;
import org.vaquitas.repository.RecordatorioRepository;

import java.sql.SQLException;
import java.util.List;

public class RecordatorioService {
    private final RecordatorioRepository recordatorioRepository;
    public RecordatorioService(RecordatorioRepository recordatorioRepository) {
        this.recordatorioRepository=recordatorioRepository;
    }

    public List<Recordatorio> verRecordatorio() throws SQLException {
        return recordatorioRepository.findAll();
    }
}
