package edu.uptc.exepciones;
/**
 * Exception thrown when an advisor (Asesor) cannot be created or found in the system.
 * This can occur due to validation failures, duplicate entries, or when attempting
 * to retrieve a non-existent advisor.
 */
public class AsesorNocreado extends Exception {
    /**
     * Constructs a new AsesorNocreado exception with the specified detail message.
     *
     * @param message The detail message explaining why the advisor was not created
     */
    public AsesorNocreado(String message){
        super(message);
    }
    
}
