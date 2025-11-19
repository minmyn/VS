package org.vaquitas.service;

import org.vaquitas.model.Animal;
import org.vaquitas.model.Raza;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.RazaRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AnimalService {
    private final AnimalRepository animalRepository;
    private final RazaRepository razaRepository;

    public AnimalService(AnimalRepository animalRepository, RazaRepository razaRepository){
        this.animalRepository = animalRepository;
        this.razaRepository = razaRepository;
    }

    public void registrarGanado(Animal animal) throws SQLException {
        Raza raza = razaRepository.findRaza(animal.getRaza());
        if (raza == null)
            throw new IllegalArgumentException("Raza no encontrada: El ID o nombre de la raza no existe.");
        animal.setRaza(raza);
        if (animalRepository.existsByIdArete(animal.getIdArete()))
            throw new IllegalArgumentException("Arete duplicado: El ganado con ID " + animal.getIdArete() + " ya existe.");
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

    public Animal verUnSoloGanado(int idArete) throws SQLException{
        return  animalRepository.findGanado(idArete);
    }

    public void darBajaGanado(Animal animal) throws SQLException{
        Animal animalBD = animalRepository.validateFechaBaja(animal.getIdArete());
        if (animalBD == null){
            throw new IllegalArgumentException("Ganado no encontrado con arete ID: " + animal.getIdArete());
        }
        LocalDate fechaNac = animalBD.getFechaNacimiento();
        LocalDate fechBaja = animal.getFechaBaja();

        if (fechBaja.isBefore(fechaNac))
            throw new IllegalArgumentException("La fecha de baja no puede ser anterior a la fecha de nacimiento.");

        animalRepository.update(animal);
    }

}