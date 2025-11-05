package org.vaquitas.service;

import org.vaquitas.model.Alimento;
import org.vaquitas.repository.AlimentoRepository;

import java.sql.SQLException;
import java.util.List;

public class AlimentoService {
    private final AlimentoRepository alimentoRepository;
    public AlimentoService(AlimentoRepository alimentoRepository) {
        this.alimentoRepository = alimentoRepository;
    }

    public void guardarAlimentos(Alimento alimento) throws SQLException{
        alimentoRepository.save(alimento);
    }

    public List<Alimento> verAlimentos()throws SQLException{
        return alimentoRepository.findAll();
    }

    public void editarAlimentos(Alimento alimento) throws SQLException{
        alimentoRepository.update(alimento);
    }
}
