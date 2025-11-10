package edu.uptc.Entidades;
/**
 * Represents a Complaint (Queja) type of PQRS.
 * A complaint expresses dissatisfaction with a service or product.
 * Tracks the level of dissatisfaction and whether it has been reviewed.
 */
public class Queja extends PQRS {
        /** Level of dissatisfaction expressed in the complaint (0-10 scale) */
    private int nivelInconformismo;
    
    /** Indicates whether the complaint has been reviewed */
    private boolean revisada;

    /**
     * Constructs a new Queja with the specified details.
     * Initializes the complaint with zero dissatisfaction level and as not reviewed.
     *
     * @param descripcion Description of the complaint
     * @param planPQRS Plan associated with this complaint
     */
    public Queja(String descripcion, Plan planPQRS) {
        super(descripcion, planPQRS);
        this.nivelInconformismo = 0;
        this.revisada = false;
    }

    public int getNivelInconformismo() {
        return nivelInconformismo;
    }

    public void setNivelInconformismo(int nivelInconformismo) {
        this.nivelInconformismo = nivelInconformismo;
    }

    public boolean isRevisada() {
        return revisada;
    }

    public void setRevisada(boolean revisada) {
        this.revisada = revisada;
    }
    /**
     * Gets the type of this PQRS.
     *
     * @return "Queja" as the type identifier
     */

    @Override
    public String obtenerTipo() {
        return "Queja";
    }


    @Override
    public String toString() {
        return "Queja [ID=" + super.getIdPQRS() +
                ", Fecha=" + super.getFechaRegistro() +
                ", Descripción=" + super.getDescripcion() +
                ", Nivel inconformismo=" + nivelInconformismo +
                ", Revisada=" + revisada + "]";
    }

}
