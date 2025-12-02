package org.vaquitas.service;

import org.vaquitas.model.Animal;
import org.vaquitas.model.Raza;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.RazaRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Animal}.
 * <p>
 * Coordina las operaciones de validación, persistencia y recuperación de datos de ganado,
 * y maneja las reglas de negocio específicas (e.g., verificar la existencia de raza, arete duplicado).
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final RazaRepository razaRepository;

    /**
     * Constructor que inyecta las dependencias de los repositorios.
     *
     * @param animalRepository El repositorio para operaciones CRUD de Animal.
     * @param razaRepository El repositorio para consultar información de Raza.
     */
    public AnimalService(AnimalRepository animalRepository, RazaRepository razaRepository){
        this.animalRepository = animalRepository;
        this.razaRepository = razaRepository;
    }

    /**
     * Realiza el registro de un nuevo animal en la base de datos.
     * <p>
     * Incluye las siguientes reglas de negocio:
     * <ul>
     * <li>Verifica si la {@link Raza} proporcionada existe.</li>
     * <li>Verifica que el {@code idArete} no esté duplicado.</li>
     * </ul>
     * </p>
     *
     * @param animal El objeto {@link Animal} a registrar.
     * @throws SQLException Si ocurre un error de base de datos.
     * @throws IllegalArgumentException Si la raza no existe o si el arete está duplicado.
     */
    public void registrarGanado(Animal animal) throws SQLException {
        // Regla 1: Verificar existencia de la raza
        Raza raza = razaRepository.findRaza(animal.getRaza());
        if (raza == null)
            throw new IllegalArgumentException("Raza no encontrada: El ID o nombre de la raza no existe.");
        animal.setRaza(raza); // Asigna el objeto Raza completo

        // Regla 2: Verificar duplicidad del arete

        if (animalRepository.existsByIdArete(animal.getIdArete()))
            throw new IllegalArgumentException("Arete duplicado: El ganado con ID " + animal.getIdArete() + " ya existe.");
        animalRepository.save(animal);
    }


    /**
     * Recupera una lista de todos los animales registrados, independientemente de su estatus.
     *
     * @return Una lista de objetos {@link Animal}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Animal> visualizarGanado() throws SQLException{
        return animalRepository.findAll();
    }

    /**
     * Recupera una lista de todos los animales con estatus 'Muerto' o 'Vendido'.
     *
     * @return Una lista de objetos {@link Animal} inactivos.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Animal> visualizarGanadoNoActivo()throws SQLException{
        return animalRepository.findNoActivo();
    }

    /**
     * Recupera una lista de todos los animales con estatus 'Activo'.
     *
     * @return Una lista de objetos {@link Animal} activos.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Animal> visuaizarGanadoActivo()throws SQLException{
        return animalRepository.findActivo();
    }

    /**
     * Recupera una lista de todos los animales con estatus 'Vendido'.
     *
     * @return Una lista de objetos {@link Animal} vendidos.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Animal> visualizarGanadoVendido()throws SQLException{
        return animalRepository.findVendido();
    }

    /**
     * Busca y recupera un solo animal por su ID de arete.
     *
     * @param idArete El ID de arete del animal.
     * @return El objeto {@link Animal} encontrado, o {@code null} si no existe.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Animal verUnSoloGanado(int idArete) throws SQLException{
        return  animalRepository.findGanado(idArete);
    }

    /**
     * Procesa la baja de un animal, actualizando su estado a 'Muerto' y registrando la fecha de baja.
     * <p>
     * Incluye las siguientes reglas de negocio:
     * <ul>
     * <li>Verifica que el animal exista antes de intentar la baja.</li>
     * <li>Verifica que la {@code fechaBaja} no sea anterior a la {@code fechaNacimiento} del animal.</li>
     * </ul>
     * </p>
     *
     * @param animal El objeto {@link Animal} que contiene el {@code idArete} y la {@code fechaBaja}.
     * @throws SQLException Si ocurre un error de base de datos.
     * @throws IllegalArgumentException Si el animal no existe o si la fecha de baja es inválida.
     */
    public void darBajaGanado(Animal animal) throws SQLException{
        // Regla 1: Validar que el animal exista y obtener la fecha de nacimiento para comparación
        Animal animalBD = animalRepository.validateFechaBaja(animal.getIdArete());

        if (animalBD == null)
            throw new IllegalArgumentException("Ganado no encontrado con arete ID: " + animal.getIdArete());

        // Regla 2: Validar que la fecha de baja no sea anterior a la de nacimiento
        if (animal.getFechaBaja().isBefore(animalBD.getFechaNacimiento()))
            throw new IllegalArgumentException("La fecha de baja no puede ser menor la fecha de nacimiento.");

        animalRepository.update(animal);
    }

}