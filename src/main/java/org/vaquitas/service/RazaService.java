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
//        if (razaRepository.findRaza(raza.getNombreRaza()))
//            throw new IllegalArgumentException("Raza existente");
        razaRepository.save(raza);
    }

    public List<Raza> verRazas() throws SQLException{
        return razaRepository.findAll();
    }

    public void renombrarRaza(Raza raza) throws  SQLException{
        razaRepository.update(raza);
    }
}