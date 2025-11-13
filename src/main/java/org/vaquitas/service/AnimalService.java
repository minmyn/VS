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
//        Raza raza = razaRepository.findRaza(animal.getRaza());
//        if (raza == null) throw new IllegalArgumentException("Raza no encontrada"); // O crea la raza según necesites
//        animal.setRaza(raza); // Asignar la instancia completa con id correctamente seteado
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