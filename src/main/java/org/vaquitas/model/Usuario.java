package org.vaquitas.model;

/**
 * Representa la entidad de dominio {@link Usuario} utilizada para la gestión de acceso y registro en el sistema.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Usuario {
    /**
     * Identificador único del usuario (PK). Se autogenera en la base de datos.
     */
    private int idUsuario;
    /**
     * Nombre completo o de pila del usuario. Utilizado para fines de identificación.
     */
    private String nombre;
    /**
     * Número de teléfono del usuario. Debe ser único y se utiliza para contacto.
     */
    private String telefono;
    /**
     * Sexo del usuario (e.g., "Masculino", "Femenino").
     */
    private String sexo;
    /**
     * Edad del usuario en años.
     */
    private int edad;
    /**
     * Correo electrónico único del usuario. Utilizado para login y debe ser validado contra duplicados.
     */
    private String email;
    /**
     * Contraseña hasheada (utilizando Bcrypt) para el acceso seguro al sistema.
     */
    private String clave;
    /**
     * El rancho al que pertenece el usuario (Foreign Key). Representado por un objeto {@link Rancho}.
     */
    private Rancho idRancho;

    /**
     * Constructor por defecto requerido para el mapeo JSON/clase (e.g., Javalin).
     */
    public Usuario(){}

//    /**
//     * Constructor utilizado específicamente para la autenticación (login).
//     *
//     * @param email El correo electrónico del usuario.
//     * @param clave La contraseña (sin hashear) proporcionada por el usuario.
//     */
//    public Usuario(String email, String clave){
//        this.email = email;
//        this.clave = clave;
//    }

    // --- Getters y Setters ---

    /** Obtiene el ID del usuario. */
    public int getIdUsuario() {
        return idUsuario;
    }

    /** Establece el ID del usuario. */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /** Obtiene el nombre del usuario. */
    public String getNombre() {
        return nombre;
    }

    /** Establece el nombre del usuario. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** Obtiene el teléfono del usuario. */
    public String getTelefono() {
        return telefono;
    }

    /** Establece el teléfono del usuario. */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /** Obtiene el sexo del usuario. */
    public String getSexo() {
        return sexo;
    }

    /** Establece el sexo del usuario. */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /** Obtiene la edad del usuario. */
    public int getEdad() {
        return edad;
    }

    /** Establece la edad del usuario. */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /** Obtiene el email del usuario. */
    public String getEmail() {
        return email;
    }

    /** Establece el email del usuario. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Obtiene la clave (contraseña) del usuario. */
    public String getClave() {
        return clave;
    }

    /** Establece la clave (contraseña) del usuario. */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /** Obtiene el rancho asociado al usuario. */
    public Rancho getIdRancho() {return idRancho;}

    /** Establece el rancho asociado al usuario. */
    public void setIdRancho(Rancho idRancho) {this.idRancho = idRancho;}
}