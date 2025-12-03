package org.vaquitas.service;

import org.vaquitas.model.Raza;
import org.vaquitas.repository.RazaRepository;

import java.sql.SQLException;
import java.util.List;
/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Raza}.
 * <p>
 * Maneja las validaciones de nombres duplicados y la asignación del ID generado tras la persistencia.
 * </p>
 */
public class RazaService {

    private final RazaRepository razaRepository;
    /**
     * Constructor que inyecta la dependencia del repositorio.
     *
     * @param razaRepository El repositorio para operaciones CRUD de Raza.
     */
    public RazaService(RazaRepository razaRepository) {
        this.razaRepository = razaRepository;
    }

    /**
     * Registra una nueva raza, verificando que no exista un nombre duplicado.
     *
     * @param raza El objeto {@link Raza} a registrar.
     * @throws SQLException Si ocurre un error de base de datos.
     * @throws IllegalArgumentException Si el nombre de la raza ya existe.
     */
    public void registrarRaza(Raza raza) throws SQLException{

        if (razaRepository.existsByName(raza.getNombreRaza())) {
            throw new IllegalArgumentException("Raza ya existe. No se permiten nombres duplicados.");
        }

        int idGenerado = razaRepository.save(raza);
        raza.setIdRaza(idGenerado);
    }

    /**
     * Obtiene la lista completa de todas las razas registradas.
     *
     * @return Una lista de objetos {@link Raza}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Raza> verRazas() throws SQLException{
        return razaRepository.findAll();
    }

    /**
     * Renombra una raza existente.
     *
     * @param raza El objeto {@link Raza} con el ID y el nuevo nombre.
     * @throws SQLException Si ocurre un error de base de datos.
     * @throws IllegalArgumentException Si la raza con el ID especificado no fue encontrada.
     */
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