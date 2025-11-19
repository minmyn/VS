package org.vaquitas.service;

import org.vaquitas.model.Medicamento;
import org.vaquitas.repository.MedicamentoRepository;

import java.sql.SQLException;
import java.util.List;

public class MedicamentoService {
    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public void registrarMedicina(Medicamento medicamento) throws SQLException {
        medicamentoRepository.save(medicamento);
    }

    public List<Medicamento> verMedicinas() throws SQLException{
        return medicamentoRepository.findAll();
    }


    public List<Medicamento> buscarMedicamentos(String texto) throws SQLException {
        return medicamentoRepository.findByNombre(texto);
    }

    public int actualizarMedicamento(Medicamento medicamento) throws  SQLException{
        return medicamentoRepository.update(medicamento);
    }
}