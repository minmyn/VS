package org.vaquitas.service;

import org.vaquitas.model.Venta;
import org.vaquitas.model.Animal;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.VentaRepository;

import java.sql.SQLException;
import java.util.List;

public class VentaService {
    private final VentaRepository ventaRepository;
    private final AnimalRepository animalRepository;
    public VentaService(VentaRepository ventaRepository, AnimalRepository animalRepository) {
        this.ventaRepository=ventaRepository;
        this.animalRepository =animalRepository;
    }

    public void registrarVenta(Venta venta) throws SQLException {
        ventaRepository.save(venta);
    }

    public List<Venta> verVentas()throws SQLException{
        return ventaRepository.findVendidos();
    }

    public boolean encontrarVacaVendida(int idArete) throws SQLException{
        return ventaRepository.findVendido(idArete);
    }
    public boolean encontrarVaca (int idArete) throws SQLException{
        return animalRepository.existsByIdArete(idArete);
    }
}