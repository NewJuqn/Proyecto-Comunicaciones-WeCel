package edu.uptc.Entidades;

import java.time.LocalDate;

public class Peticion extends PQRS {
    private boolean resuelta;
    private String conceptoSolucion;

    public Peticion(LocalDate fechaRegistro, String descripcion, boolean resuelta,
            String conceptoSolucion) {
        super(fechaRegistro, descripcion);
        this.resuelta = resuelta;
        this.conceptoSolucion = conceptoSolucion;
    }

    public boolean isResuelta() {
        return resuelta;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }

    public String getConceptoSolucion() {
        return conceptoSolucion;
    }

    public void setConceptoSolucion(String conceptoSolucion) {
        this.conceptoSolucion = conceptoSolucion;
    }

    @Override
    public String obtenerTipo() {
        return "Petición";
    }

    @Override
    public void procesar() {
        resuelta = true;
        conceptoSolucion = "La petición fue atendida exitosamente.";
    }

    @Override
    public String toString() {
        return "Petición [ID=" + super.getIdPQRS() +
                ", Fecha=" + super.getFechaRegistro() +
                ", Descripción=" + super.getDescripcion() +
                ", Resuelta=" + resuelta +
                ", Solución=" + conceptoSolucion + "]";
    }

}
