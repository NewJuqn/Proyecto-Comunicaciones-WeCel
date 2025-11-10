package edu.uptc.Entidades;
/**
 * Represents a Petition type of PQRS.
 * A petition is a formal request that can be resolved with a solution concept.
 * Tracks whether the petition has been resolved and stores the solution provided.
 */
public class Peticion extends PQRS {
    /** Indicates whether the petition has been resolved */
    private boolean resuelta;
    
    /** Description of the solution provided for the petition */
    private String conceptoSolucion;

    /**
     * Constructs a new Peticion with the specified details.
     * Initializes the petition as unresolved with a default solution message.
     *
     * @param descripcion Description of the petition
     * @param planPQRS Plan associated with this petition
     */
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
    /**
     * Gets the type of this PQRS.
     *
     * @return "Petición" as the type identifier
     */
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
