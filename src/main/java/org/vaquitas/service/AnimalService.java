package org.vaquitas.service;

import org.vaquitas.model.Animal;
import org.vaquitas.repository.AnimalRepository;
import java.sql.SQLException;
import java.util.List;

public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public void registrarGanado(Animal animal) throws SQLException {
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
        animalRepository.update(animal);
    }

}