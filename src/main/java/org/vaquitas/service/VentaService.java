package org.vaquitas.service;

import org.vaquitas.model.Venta;
import org.vaquitas.model.Animal;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.VentaRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VentaService {
    private final VentaRepository ventaRepository;
    private final AnimalRepository animalRepository;
    public VentaService(VentaRepository ventaRepository, AnimalRepository animalRepository) {
        this.ventaRepository=ventaRepository;
        this.animalRepository =animalRepository;
    }

    public void registrarVenta(Venta venta) throws SQLException {
        int idArete = venta.getGanado().getIdArete();

        if (!animalRepository.existsByIdArete(idArete)) {
            throw new IllegalArgumentException("Ganado inexistente con arete ID: " + idArete);
        }

        if (ventaRepository.findVendido(idArete)){
            throw new IllegalArgumentException("El ganado vendido previamente.");
        }
        Animal animalBD = animalRepository.validateFechaBaja(venta.getGanado().getIdArete());
        LocalDate fechaNac = animalBD.getFechaNacimiento();
        LocalDate fechBaja = venta.getFechaBaja();
        LocalDate hoy = LocalDate.now();
        if (fechBaja.isBefore(fechaNac))
            throw new IllegalArgumentException("La fecha de baja no puede ser anterior a la fecha de nacimiento.");
        if(fechBaja.isAfter(hoy))
            throw new IllegalArgumentException("La fecha de baja no puede una fecha que no ha pasado");
        ventaRepository.save(venta);
    }

    public List<Venta> verVentas()throws SQLException{
        return ventaRepository.findVendidos();
    }

}