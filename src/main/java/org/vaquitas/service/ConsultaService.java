package org.vaquitas.service;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Consulta;
import org.vaquitas.model.Receta;
import org.vaquitas.model.Recordatorio;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.ConsultaRepository;
import org.vaquitas.repository.RecetaRepository;
import org.vaquitas.repository.RecordatorioRepository;


public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final AnimalRepository animalRepository;

    public ConsultaService(ConsultaRepository consultaRepository, AnimalRepository animalRepository) {
        this.consultaRepository=consultaRepository;
        this.animalRepository = animalRepository;

    }


}