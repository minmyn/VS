package org.vaquitas.service;

import org.vaquitas.model.Animal;
import org.vaquitas.repository.AnimalRepository;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public void registrarGanado(Animal animal) throws SQLException {
        boolean validar;
        validar= animalRepository.existsByIdArete(animal.getIdArete());
        if (validar){
            throw new IllegalArgumentException("Arete duplicado");
        }
        animalRepository.save(animal);
    }

    public List<Animal> visualizarGanado() throws SQLException{
        return animalRepository.findAll();
    }

    public List<Animal> visualizarGanadoNoActivo()throws SQLException{
        return animalRepository.findNoActivo();
    }

    public List<Animal> visuaizarGanadoActivo()throws SQLException{
        return animalRepository.findActivo();
    }

    public List<Animal> visualizarGanadoVendido()throws SQLException{
        return animalRepository.findVendido();
    }

    public void darBajaGanado(Animal animal) throws SQLException{
        Animal animalBD = animalRepository.validateFechaBaja(animal.getIdArete());
        if (animalBD == null){
            throw new IllegalArgumentException();
        }
        LocalDate fechaNac = animalBD.getFechaNacimiento();
        LocalDate fechBaja = animal.getFechaBaja();
        if (fechBaja.isBefore(fechaNac))
            throw new IllegalArgumentException();
        animalRepository.update(animal);
    }

}