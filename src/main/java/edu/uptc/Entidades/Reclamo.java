package edu.uptc.Entidades;

/**
 * Represents a Claim (Reclamo) type of PQRS.
 * A claim is a formal demand for compensation or correction of a service issue.
 * Tracks compensation resources and resolution status.
 */
public class Reclamo extends PQRS {
    /** Description of the compensation resource provided for the claim */
    private String recursoCompensacion;
    
    /** Indicates whether the claim has been resolved */
    private boolean resuelta;

    /**
     * Constructs a new Reclamo with the specified details.
     * Initializes the claim as unresolved with a default compensation message.
     *
     * @param descripcion Description of the claim
     * @param planPQRS Plan associated with this claim
     */
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
    /**
     * Gets the type of this PQRS.
     *
     * @return "Reclamo" as the type identifier
     */
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
