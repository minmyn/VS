package org.vaquitas.service;

import org.vaquitas.model.Reporte;
import org.vaquitas.repository.ReportesRepository;

import java.sql.SQLException;
import java.util.List;

public class ReporteServicee {
    private final ReportesRepository reportesRepository;

    public ReporteServicee(ReportesRepository reportesRepository) {
        this.reportesRepository = reportesRepository;
    }

    public List<Reporte> reporteGanado() throws SQLException{
        return reportesRepository.findEstado();
    }
    public List<Reporte> reporteGanadoRaza() throws SQLException{
        return reportesRepository.findRaza();
    }

    public List<Reporte> reporteVenta() throws SQLException{
        return reportesRepository.findSexo();
    }

    public List<Reporte> reporteVentaRaza() throws SQLException{
        return reportesRepository.findVendidoByRaza();
    }

    public List<Reporte> reporteAlimento() throws SQLException{
        return reportesRepository.findTipo();
    }

    public List<Reporte> reporteAlimentoSemanal() throws SQLException{
        return reportesRepository.findTipoSemanal();
    }

    public List<Reporte> reporteAlimentoAnual() throws SQLException{
        return reportesRepository.findAlimentoAnualPorMes();
    }


}
