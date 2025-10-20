package edu.uptc.Entidades;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private static int contadorID = 1000000;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String pais;
    private String estado;
    private String ciudad;
    private String contrasena;
    public Usuario(String nombre, String apellido, LocalDate fechaNacimiento, String pais, String estado,
            String ciudad, String contrasena) {
        this.id = contadorID++;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
        this.estado = estado;
        this.ciudad = ciudad;
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento="
                + fechaNacimiento + ", pais=" + pais + ", estado=" + estado + ", ciudad=" + ciudad + ", contrasena="
                + contrasena + "]";
    }

    


}
