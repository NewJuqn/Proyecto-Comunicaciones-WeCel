package edu.uptc.Entidades;

import java.time.LocalDate;

public abstract class PQRS {
    private int idPQRS;
    private static int contadorId = 100;
    private LocalDate fechaRegistro;
    private String descripcion;

    public PQRS(LocalDate fechaRegistro, String descripcion) {
        this.idPQRS = contadorId++;
        this.fechaRegistro = fechaRegistro;
        this.descripcion = descripcion;
    }

    public abstract String obtenerTipo();

    public abstract void procesar();

    public int getIdPQRS() {
        return idPQRS;
    }

    public void setIdPQRS(int idPQRS) {
        this.idPQRS = idPQRS;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "PQRS [ID=" + idPQRS + ", Fecha=" + fechaRegistro +
                ", Descripción=" + descripcion + "]";
    }

}
