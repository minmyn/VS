package org.vaquitas.service;

import org.vaquitas.model.Rancho;
import org.vaquitas.repository.RanchoRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Rancho}.
 * <p>
 * Se encarga de coordinar la validación de duplicados y la persistencia de datos.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RanchoService {
    private final RanchoRepository ranchoRepository;

    /**
     * Constructor que inyecta la dependencia del repositorio.
     */
    public RanchoService(RanchoRepository ranchoRepository) {
        this.ranchoRepository=ranchoRepository;
    }

    /**
     * Agrega un nuevo rancho después de verificar si el nombre está duplicado.
     * <p>
     * Lanza {@code IllegalArgumentException} si el nombre ya existe.
     * </p>
     *
     * @param rancho El objeto {@link Rancho} a registrar.
     * @throws SQLException Si ocurre un error de base de datos.
     * @throws IllegalArgumentException Si el nombre del rancho está duplicado.
     */
    public void agregarRancho(Rancho rancho) throws SQLException{
        boolean validar;
        validar = ranchoRepository.duplicateNombre(rancho.getNombre());
        if (validar) throw new IllegalArgumentException("Nombre duplicado");
        int idGenerado = ranchoRepository.save(rancho);
        rancho.setIdRancho(idGenerado);
    }

    /**
     * Obtiene la lista completa de todos los ranchos.
     *
     * @return Una lista de objetos {@link Rancho}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Rancho> verRanchos()throws SQLException{
        return ranchoRepository.findAll();
    }

    /**
     * Actualiza el nombre y/o la ubicación de un rancho existente.
     *
     * @param rancho El objeto {@link Rancho} con el ID y los campos a actualizar.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public void editarRancho(Rancho rancho) throws SQLException{
        ranchoRepository.update(rancho);
    }
}