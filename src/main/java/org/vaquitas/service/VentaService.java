package org.vaquitas.service;

import org.vaquitas.model.Venta;
import org.vaquitas.model.Animal;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.VentaRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Venta}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Venta}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class VentaService {
    private final VentaRepository ventaRepository;
    private final AnimalRepository animalRepository;

    /**
     * Constructor que inyecta las dependencias de los repositorios.
     *
     * @param ventaRepository Repositorio para operaciones de Venta.
     * @param animalRepository Repositorio para operaciones de Animal.
     */
    public VentaService(VentaRepository ventaRepository, AnimalRepository animalRepository) {
        this.ventaRepository = ventaRepository;
        this.animalRepository = animalRepository;
    }

    /**
     * Registra la venta de un animal y realiza las validaciones de negocio correspondientes.
     * <p>
     * Reglas de negocio:
     * <ul>
     * <li>El animal debe existir y se obtiene su fecha de nacimiento.</li>
     * <li>El animal no debe haber sido vendido previamente (se verifica en la tabla VENTA).</li>
     * <li>La fecha de baja (venta) no puede ser anterior a la fecha de nacimiento.</li>
     * </ul>
     * </p>
     *
     * @param venta El objeto {@link Venta} a registrar.
     * @throws SQLException Si ocurre un error de base de datos.
     * @throws IllegalArgumentException Si no se cumple alguna regla de negocio.
     */
    public void registrarVenta(Venta venta) throws SQLException {
        int idArete = venta.getGanado().getIdArete();
        Animal animalBD = animalRepository.validateVenta(idArete);
        if (animalBD == null)
            throw new IllegalArgumentException("Ganado no encontrado con arete ID: " + idArete);
        if (ventaRepository.findVendido(idArete)){
            throw new IllegalArgumentException("El ganado ya fue vendido previamente.");
        }
        if (venta.getFechaBaja().isBefore(animalBD.getFechaNacimiento()))
            throw new IllegalArgumentException("La fecha de baja no puede ser menor la fecha de nacimiento.");
        ventaRepository.save(venta);
    }

    /**
     * Obtiene una lista de todos los registros de animales vendidos, con sus detalles consolidados.
     *
     * @return Una lista de objetos {@link Venta}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Venta> verVentas()throws SQLException{
        return ventaRepository.findVendidos();
    }

    /**
     * Actualiza el precio de venta y el peso final de una venta.
     *
     * @throws SQLException Si ocurre un error de base de datos.
     * */
    public void actualizarVenta(Venta venta)throws SQLException {
        ventaRepository.update(venta);
    }
}