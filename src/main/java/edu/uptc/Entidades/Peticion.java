package edu.uptc.Entidades;



public class Peticion extends PQRS {
    private boolean resuelta;
    private String conceptoSolucion;

    public Peticion(String descripcion, Plan planPQRS) {
        super(descripcion, planPQRS);
        this.resuelta = false;
        this.conceptoSolucion = "Sin solucion por ahora";
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
    public String toString() {
        return "Petición [ID=" + super.getIdPQRS() +
                ", Fecha=" + super.getFechaRegistro() +
                ", Descripción=" + super.getDescripcion() +
                ", Resuelta=" + resuelta +
                ", Solución=" + conceptoSolucion + "]";
    }

}
