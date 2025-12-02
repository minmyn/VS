package org.vaquitas.service;

import org.vaquitas.model.Venta;
import org.vaquitas.model.Animal;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.VentaRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Venta}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class VentaService {
    private final VentaRepository ventaRepository;
    // AnimalRepository tiene los métodos necesarios (existsByIdArete y validateFechaBaja) para validaciones.
    private final AnimalRepository animalRepository;

    /**
     * Constructor que inyecta las dependencias de los repositorios.
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
     * <li>El animal debe existir.</li>
     * <li>El animal no debe haber sido vendido previamente (validación en tabla VENTA).</li>
     * <li>La fecha de baja no puede ser anterior a la fecha de nacimiento.</li>
     * </ul>
     * </p>
     *
     * @param venta El objeto {@link Venta} a registrar.
     * @throws SQLException Si ocurre un error de base de datos.
     * @throws IllegalArgumentException Si no se cumple alguna regla de negocio.
     */
    public void registrarVenta(Venta venta) throws SQLException {
        int idArete = venta.getGanado().getIdArete();
        Animal animalBD = new Animal();
        animalBD = animalRepository.validateVenta(idArete);
        // 1. Validación de existencia
        if (animalBD == null)
            throw new IllegalArgumentException("Ganado no encontrado con arete ID: " + idArete);

        // Regla 2: Validar que la fecha de baja no sea anterior a la de nacimiento
        if (venta.getFechaBaja().isBefore(animalBD.getFechaNacimiento()))
            throw new IllegalArgumentException("La fecha de baja no puede ser menor la fecha de nacimiento.");


        // 2. Validación de venta previa
        if (ventaRepository.findVendido(idArete)){
            throw new IllegalArgumentException("El ganado ya fue vendido previamente.");
        }

        // 3. Registrar Venta.
        ventaRepository.save(venta);
    }

    /**
     * Obtiene una lista de todos los registros de animales vendidos.
     *
     * @return Una lista de objetos {@link Venta}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Venta> verVentas()throws SQLException{
        return ventaRepository.findVendidos();
    }

}