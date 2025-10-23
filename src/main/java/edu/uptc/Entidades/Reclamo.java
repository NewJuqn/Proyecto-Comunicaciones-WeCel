package edu.uptc.Entidades;

import java.time.LocalDate;

public class Reclamo extends PQRS {
    private String recursoCompensacion;

    public Reclamo(LocalDate fechaRegistro, String descripcion, String recursoCompensacion, Plan planPQRS) {
        super(fechaRegistro, descripcion, planPQRS);
        this.recursoCompensacion = recursoCompensacion;
    }

    public String getRecursoCompensacion() {
        return recursoCompensacion;
    }

    public void setRecursoCompensacion(String recursoCompensacion) {
        this.recursoCompensacion = recursoCompensacion;
    }

    @Override
    public String obtenerTipo() {
        return "Reclamo";
    }

    @Override
    public void procesar() {
        this.recursoCompensacion = "Compensación procesada.";
    }

    @Override
    public String toString() {
        return "Reclamo [ID=" + super.getIdPQRS() +
                ", Fecha=" + super.getFechaRegistro() +
                ", Descripción=" + super.getDescripcion() +
                ", Recurso=" + recursoCompensacion + "]";
    }

    
}
