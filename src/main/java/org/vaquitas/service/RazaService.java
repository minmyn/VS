package org.vaquitas.service;

import org.vaquitas.model.Raza;
import org.vaquitas.repository.RazaRepository;

import java.sql.SQLException;
import java.util.List;

public class RazaService {

    private final RazaRepository razaRepository;
    public RazaService(RazaRepository razaRepository) {
        this.razaRepository = razaRepository;
    }

    public void registrarRaza(Raza raza) throws SQLException{

        if (razaRepository.existsByName(raza.getNombreRaza())) {
            throw new IllegalArgumentException("Raza ya existe. No se permiten nombres duplicados.");
        }

        int idGenerado = razaRepository.save(raza);
        raza.setIdRaza(idGenerado);
    }

    public List<Raza> verRazas() throws SQLException{
        return razaRepository.findAll();
    }

    public void renombrarRaza(Raza raza) throws  SQLException{
        try {
            razaRepository.update(raza);
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("no afectó ninguna fila")) {
                throw new IllegalArgumentException("La raza con ID " + raza.getIdRaza() + " no fue encontrada para renombrar.");
            }
            throw e;
        }
    }
}