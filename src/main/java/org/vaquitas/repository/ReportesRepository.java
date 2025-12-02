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

    //REPORTE DE ANIMAL
    //1. GENERAL Ó PRINCIPAL, XD
    public List<Reporte> findEstado() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql =
                "SELECT estado, COUNT(*) as cantidad " +
                "FROM ANIMAL WHERE estado = 'Muerto' OR estado = 'Vendido' " +
                "GROUP BY estado";
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
                double porcentajeCompleto = (total > 0) ? (rpt.getNumero() * 100.0 / total) : 0;
                rpt.setPorcentaje(redondearDosDecimales(porcentajeCompleto));
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

    //2.¿REPORTE DE QUE RAZA HAY MAS EN EL SISTEMA?
    public List<Reporte> findRaza() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql =
                "SELECT r.nombre AS raza, " +
                "COUNT(a.arete_id) AS cantidad " +
                "FROM ANIMAL a " +
                "INNER JOIN RAZA r ON a.raza_id = r.raza_id " +
                "WHERE a.estado = 'Activo' " +
                "GROUP BY r.nombre " +
                "ORDER BY cantidad DESC";

        double total = 0;
        List<Reporte> temporales = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()){
            while (rs.next()) {
                String estado = rs.getString("raza");
                double cantidad = rs.getInt("cantidad");
                total += cantidad;
                Reporte rpt = new Reporte();
                rpt.setColumna(estado);
                rpt.setNumero(cantidad);
                temporales.add(rpt);
            }
            for (Reporte rpt : temporales) {
                double porcentajeCompleto = (total > 0) ? (rpt.getNumero() * 100.0 / total) : 0;
                rpt.setPorcentaje(redondearDosDecimales(porcentajeCompleto));
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

    //3. ANUAL POR MES

    //REPORTE DE VENTA
    //1. GENERAL Ó PRINCIPAL, XD
    public List<Reporte> findSexo() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql =
                "SELECT sexo, COUNT(*) as cantidad " +
                "FROM ANIMAL WHERE (sexo = 'Hembra' OR sexo = 'Macho') " +
                "AND estado = 'Vendido' GROUP BY sexo";
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
                double porcentajeCompleto = (total > 0) ? (rpt.getNumero() * 100.0 / total) : 0;
                rpt.setPorcentaje(redondearDosDecimales(porcentajeCompleto));
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
    //2.SEMANAL

    //3. ANUAL POR MES

    //¿que raza fue mas vendida?

    public List<Reporte> findVendidoByRaza() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql =
                "SELECT r.nombre AS raza, " +
                        "COUNT(a.arete_id) AS cantidad " +
                        "FROM ANIMAL a " +
                        "INNER JOIN RAZA r ON a.raza_id = r.raza_id " +
                        "WHERE a.estado = 'Vendido' " +
                        "GROUP BY r.nombre " +
                        "ORDER BY cantidad DESC";

        double total = 0;
        List<Reporte> temporales = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()){
            while (rs.next()) {
                String estado = rs.getString("raza");
                double cantidad = rs.getInt("cantidad");
                total += cantidad;
                Reporte rpt = new Reporte();
                rpt.setColumna(estado);
                rpt.setNumero(cantidad);
                temporales.add(rpt);
            }
            for (Reporte rpt : temporales) {
                double porcentajeCompleto = (total > 0) ? (rpt.getNumero() * 100.0 / total) : 0;
                rpt.setPorcentaje(redondearDosDecimales(porcentajeCompleto));
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

    //REPORTE DE ALIMENTO
    //1.GENERAL Ó PRINCIPAL, XD
    public List<Reporte> findTipo() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql =
                "SELECT tipo, SUM(precio) as sumaPrecio " +
                "FROM ALIMENTO GROUP BY tipo";
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
                double porcentajeCompleto = (total > 0) ? (rpt.getNumero() * 100.0 / total) : 0;
                rpt.setPorcentaje(redondearDosDecimales(porcentajeCompleto));
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

    //2. SEMANAL
    public List<Reporte> findTipoSemanal() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql =
                "SELECT tipo, SUM(precio) as sumaPrecio " +
                "FROM ALIMENTO WHERE fecha_compra " +
                "BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE() " +
                "GROUP BY tipo";
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
                double porcentajeCompleto = (total > 0) ? (rpt.getNumero() * 100.0 / total) : 0;
                rpt.setPorcentaje(redondearDosDecimales(porcentajeCompleto));
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

    //3. ANUAL POR MES
    public List<Reporte> findAlimentoAnualPorMes() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql =
                "SELECT MONTH(a.fecha_compra) AS mes, " +
                "SUM(a.precio) AS sumaPrecio, " +
                    "(SELECT tipo " +
                    "FROM ALIMENTO alim " +
                    "WHERE MONTH(alim.fecha_compra) = mes " +
                    "AND YEAR(alim.fecha_compra) = YEAR(CURDATE()) " +
                    "GROUP BY alim.tipo " +
                    "ORDER BY COUNT(*) DESC, " +
                    "SUM(alim.precio) DESC LIMIT 1) " +
                "AS alimento_mas_comprado " +
                "FROM ALIMENTO a WHERE YEAR(a.fecha_compra) = YEAR(CURDATE()) " +
                "GROUP BY mes ORDER BY mes";
        double total = 0;
        List<Reporte> temporales = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int mes = rs.getInt("mes");
                double sumaPrecio = rs.getDouble("sumaPrecio");
                String masAlgo = rs.getString("alimento_mas_comprado");
                total += sumaPrecio;
                Reporte rpt = new Reporte();
                rpt.setColumna(String.valueOf(mes));
                rpt.setNumero(sumaPrecio);
                rpt.setNota((masAlgo != null ? masAlgo : "N/A"));
                temporales.add(rpt);
            }
            for (Reporte rpt : temporales) {
                double porcentajeCompleto = (total > 0)
                        ? (rpt.getNumero() * 100.0 / total)
                        : 0;
                rpt.setPorcentaje(redondearDosDecimales(porcentajeCompleto));
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

    //MÉTODOS DE AYUDA VRO

    private double redondearDosDecimales(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}