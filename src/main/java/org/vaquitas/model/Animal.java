package org.vaquitas.model;

import java.time.LocalDate;

public class Animal {
    private int idArete;
    private String nombre;
    private LocalDate fechaNacimiento;
    private double peso;
    private String sexo;
    private String estatus;
    private LocalDate fechaBaja;
    private int idRancho;
    private int idRaza;

    public int getIdArete() {
        return idArete;
    }

    public void setIdArete(int idArete) {
        this.idArete = idArete;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(int idRaza) {
        this.idRaza = idRaza;
    }

    public int getIdRancho() {
        return idRancho;
    }

    public void setIdRancho(int idRancho) {
        this.idRancho = idRancho;
    }
}