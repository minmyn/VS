package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Reporte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportesRepository {

    public List<Reporte> findEstado() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql = "SELECT estado, COUNT(*) as cantidad FROM ANIMAL WHERE estado = 'Muerto' OR estado = 'Vendido' GROUP BY estado";
        double total = 0;
        List<Reporte> temporales = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()){
            while (rs.next()) {
                String estado = rs.getString("estado");
                double cantidad = rs.getInt("cantidad");
                total += cantidad;

                Reporte rpt = new Reporte();
                rpt.setColumna(estado);
                rpt.setNumero(cantidad);
                temporales.add(rpt);
            }
            for (Reporte rpt : temporales) {
                rpt.setPorcentaje((total > 0) ? (rpt.getNumero() * 100.0 / total) : 0);
                reportes.add(rpt);
            }
            if (total > 0) {
                Reporte totalRpt = new Reporte();
                totalRpt.setColumna("Total");
                totalRpt.setNumero(total);
                totalRpt.setPorcentaje(100);
                reportes.add(totalRpt);
            }
        }
        return reportes;
    }

    public List<Reporte> findSexo() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql = "SELECT sexo, COUNT(*) as cantidad FROM ANIMAL WHERE (sexo = 'Hembra' OR sexo = 'Macho') AND estado = 'Vendido' GROUP BY sexo";
        double total = 0;
        List<Reporte> temporales = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                String sexo = rs.getString("sexo");
                double cantidad = rs.getInt("cantidad");
                total += cantidad;

                Reporte rpt = new Reporte();
                rpt.setColumna(sexo);
                rpt.setNumero(cantidad);
                temporales.add(rpt);
            }
            for (Reporte rpt : temporales) {
                rpt.setPorcentaje((total > 0) ? (rpt.getNumero() * 100.0 / total) : 0);
                reportes.add(rpt);
            }
            if (total > 0) {
                Reporte totalRpt = new Reporte();
                totalRpt.setColumna("Total");
                totalRpt.setNumero(total);
                totalRpt.setPorcentaje(100);
                reportes.add(totalRpt);
            }
        }
        return reportes;
    }

    public List<Reporte> findTipo() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql = "SELECT tipo, SUM(precio) as sumaPrecio FROM ALIMENTO GROUP BY tipo";
        double total = 0;
        List<Reporte> temporales = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                double sumaPrecio = rs.getDouble("sumaPrecio");
                total += sumaPrecio;

                Reporte rpt = new Reporte();
                rpt.setColumna(tipo);
                rpt.setNumero(sumaPrecio);
                temporales.add(rpt);
            }
            for (Reporte rpt : temporales) {
                rpt.setPorcentaje((total > 0) ? (rpt.getNumero() * 100.0 / total) : 0);
                reportes.add(rpt);
            }
            if (total > 0) {
                Reporte totalRpt = new Reporte();
                totalRpt.setColumna("Total");
                totalRpt.setNumero(total);
                totalRpt.setPorcentaje(100);
                reportes.add(totalRpt);
            }
        }
        return reportes;
    }


}