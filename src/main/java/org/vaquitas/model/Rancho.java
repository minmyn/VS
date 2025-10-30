package org.vaquitas.model;

public class Rancho {
    private int idRancho;
    private String nombre, ubicacion;

    public int getIdRancho() {
        return idRancho;
    }

    public void setIdRancho(int idRancho) {
        this.idRancho = idRancho;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}