package edu.uptc.Entidades;

/**
 * Represents a Suggestion (Sugerencia) type of PQRS.
 * A suggestion is a recommendation or idea for service improvement.
 * Tracks the importance level assigned to the suggestion.
 */
public class Sugerencia extends PQRS {
    /** Level of importance assigned to this suggestion (0-10 scale) */
    private int nivelImportancia;

    /**
     * Constructs a new Sugerencia with the specified details.
     * Initializes the suggestion with zero importance level.
     *
     * @param descripcion Description of the suggestion
     * @param planPQRS Plan associated with this suggestion
     */
    public Sugerencia(String descripcion, Plan planPQRS) {
        super(descripcion, planPQRS);
        this.nivelImportancia = 0;
    }

    public int getNivelImportancia() {
        return nivelImportancia;
    }

    public void setNivelImportancia(int nivelImportancia) {
        this.nivelImportancia = nivelImportancia;
    }
    /**
     * Gets the type of this PQRS.
     *
     * @return "Sugerencia" as the type identifier
     */
    @Override
    public String obtenerTipo() {
        return "Sugerencia";
    }

    /**
     * Processes the suggestion by updating its importance level.
     *
     * @param nuevaImportancia The new importance level to assign
     */
    public void procesar(int nuevaImportancia) {
        setNivelImportancia(nuevaImportancia);
    }

    @Override
    public String toString() {
        return "Sugerencia [ID=" + super.getIdPQRS() +
                ", Fecha=" + super.getFechaRegistro() +
                ", Descripción=" + super.getDescripcion() +
                ", Nivel de importancia=" + nivelImportancia + "]";
    }

}
