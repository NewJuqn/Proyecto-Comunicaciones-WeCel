package edu.uptc.Entidades;

import java.time.LocalDate;

public abstract class PQRS {
    private int idPQRS;
    private static int contadorId = 100;
    private LocalDate fechaRegistro;
    private String descripcion;
    private Plan planPQRS;

    public PQRS(LocalDate fechaRegistro, String descripcion, Plan planPQRS) {
        this.idPQRS = contadorId++;
        this.fechaRegistro = fechaRegistro;
        this.descripcion = descripcion;
        this.planPQRS = planPQRS;
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

    public Plan getPlanPQRS() {
        return planPQRS;
    }

    public void setPlanPQRS(Plan planPQRS) {
        this.planPQRS = planPQRS;
    }

    @Override
    public String toString() {
        return "PQRS [ID=" + idPQRS + ", Fecha=" + fechaRegistro +
                ", Descripción=" + descripcion + "]";
    }

}
