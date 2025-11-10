package edu.uptc.Entidades;

import java.time.LocalDate;
/**
 * Abstract base class representing a PQRS (Petition, Complaint, Claim, Suggestion) in the system.
 * Contains common attributes for all PQRS types including registration date, description, and associated plan.
 * Subclasses must implement the obtenerTipo method to specify the PQRS type.
 */
public abstract class PQRS {
    /** Unique identifier for the PQRS */
    private int idPQRS;
    
    /** Static counter for generating unique PQRS IDs */
    private static int contadorId = 100;
    
    /** Date when the PQRS was registered */
    private LocalDate fechaRegistro;
    
    /** Description of the issue or request */
    private String descripcion;
    
    /** Plan associated with this PQRS */
    private Plan planPQRS;

    /**
     * Constructs a new PQRS with the specified details.
     * Automatically assigns a unique ID and sets the registration date to current date.
     *
     * @param descripcion Description of the PQRS
     * @param planPQRS Plan associated with this PQRS
     */
    public PQRS(String descripcion, Plan planPQRS) {
        this.idPQRS = contadorId++;
        this.fechaRegistro = LocalDate.now();
        this.descripcion = descripcion;
        this.planPQRS = planPQRS;
    }
    /**
     * Gets the type of PQRS.
     * Must be implemented by subclasses to return the specific type.
     *
     * @return String representing the PQRS type
     */
    public abstract String obtenerTipo();

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
