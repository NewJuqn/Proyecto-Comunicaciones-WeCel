package edu.uptc.Entidades;

import java.time.LocalDate;
/**
 * Represents a user in the system.
 * This is the base class for all user types including clients and advisors.
 * Contains common information such as personal identification, location, and authentication credentials.
 */
public class Usuario {
    /** User's identification number (cedula) */
    private String cedula;
    
    /** User's first name */
    private String nombre;
    
    /** User's last name */
    private String apellido;
    
    /** User's date of birth */
    private LocalDate fechaNacimiento;
    
    /** User's country of residence */
    private String pais;
    
    /** User's department/state of residence */
    private String departamento;
    
    /** User's city of residence */
    private String ciudad;
    
    /** User's password for authentication */
    private String contrasena;

    /**
     * Constructs a new Usuario with the specified details.
     *
     * @param cedula User's identification number
     * @param nombre User's first name
     * @param apellido User's last name
     * @param fechaNacimiento User's date of birth
     * @param pais User's country
     * @param departamento User's department/state
     * @param ciudad User's city
     * @param contrasena User's password
     */
    public Usuario(String cedula, String nombre, String apellido, LocalDate fechaNacimiento, String pais, String departamento,
            String ciudad, String contrasena) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario [cedula=" + cedula + ", nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento="
                + fechaNacimiento + ", pais=" + pais + ", departamento=" + departamento + ", ciudad=" + ciudad + ", contrasena="
                + contrasena + "]";
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    


}
