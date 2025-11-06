package org.vaquitas.service;

import org.vaquitas.model.Receta;
import org.vaquitas.repository.RecetaRepository;

import java.sql.SQLException;
import java.util.List;

public class RecetaService {
    private final RecetaRepository recetaRepository;
    public RecetaService(RecetaRepository recetaRepository) {
        this.recetaRepository=recetaRepository;
    }

    public List<Receta> verRecetas() throws SQLException{
        return recetaRepository.findAll();
    }
}
