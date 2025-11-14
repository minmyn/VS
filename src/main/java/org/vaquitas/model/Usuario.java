package org.vaquitas.model;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String telefono;
    private String sexo;
    private int edad;
    private String email;
    private String clave;
    private Rancho idRancho;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Rancho getIdRancho() {
        return idRancho;
    }

    public void setIdRancho(Rancho idRancho) {
        this.idRancho = idRancho;
    }

    public Usuario(){}
    public Usuario(String email, String clave){
        this.email = email;
        this.clave = clave;
    }
}