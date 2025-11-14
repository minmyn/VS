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

// RanchoService.java (Método agregarRancho corregido)

    public void agregarRancho(Rancho rancho) throws SQLException{
        boolean validar;
        validar = ranchoRepository.duplicateNombre(rancho.getNombre());
        if (validar) throw new IllegalArgumentException("Nombre duplicado");
        int idGenerado = ranchoRepository.save(rancho);
        rancho.setIdRancho(idGenerado);
    }

    public List<Rancho> verRanchos()throws SQLException{
        return ranchoRepository.findAll();
    }

    public void editarRancho(Rancho rancho) throws SQLException{
        ranchoRepository.update(rancho);
    }
}