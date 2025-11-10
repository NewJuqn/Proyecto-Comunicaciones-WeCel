package edu.uptc.exepciones;
/**
 * Exception thrown when a PQRS record cannot be found in the system.
 * Used during PQRS retrieval, modification, and deletion operations.
 */
public class PQRSNoEncontrada extends Exception {
    /**
     * Constructs a new PQRSNoEncontrada exception with the specified detail message.
     *
     * @param message The detail message explaining which PQRS was not found
     */
    public PQRSNoEncontrada(String message) {
        super(message);
    }
}
