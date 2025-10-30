package org.vaquitas.service;

import org.vaquitas.model.Venta;
import org.vaquitas.model.Animal;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.VentaRepository;

import java.sql.SQLException;
import java.util.List;

public class VentaService {
    private final VentaRepository ventaRepository;
    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository=ventaRepository;
    }

    public void registrarVenta(Venta venta) throws SQLException {
        ventaRepository.save(venta);
    }

    public List<Venta> verVentas()throws SQLException{
        return ventaRepository.findVendido();
    }
}