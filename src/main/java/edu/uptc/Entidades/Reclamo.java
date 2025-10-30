package edu.uptc.Entidades;


public class Reclamo extends PQRS {
    private String recursoCompensacion;
    private boolean resuelta;

    public Reclamo(String descripcion, Plan planPQRS) {
        super(descripcion, planPQRS);
        this.recursoCompensacion = "Por insertar";
        this.resuelta = false;
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
    public String toString() {
        return "Reclamo [ID=" + super.getIdPQRS() +
                ", Fecha=" + super.getFechaRegistro() +
                ", Descripción=" + super.getDescripcion() +
                ", Recurso=" + recursoCompensacion + "]";
    }

    public boolean isResuelta() {
        return resuelta;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }

    
}
