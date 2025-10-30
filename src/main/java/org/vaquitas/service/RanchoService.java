package org.vaquitas.service;

import org.vaquitas.model.Rancho;
import org.vaquitas.repository.RanchoRepository;

import java.sql.SQLException;
import java.util.List;

public class RanchoService {
    private final RanchoRepository ranchoRepository;

    public RanchoService(RanchoRepository ranchoRepository) {
        this.ranchoRepository=ranchoRepository;
    }

    public void agregarRancho(Rancho rancho) throws SQLException{
        ranchoRepository.save(rancho);
    }

    public List<Rancho> verRanchos()throws SQLException{
        return ranchoRepository.findAll();
    }

    public void editarRancho(Rancho rancho) throws SQLException{
        ranchoRepository.update(rancho);
    }
}