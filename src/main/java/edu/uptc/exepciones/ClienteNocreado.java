package edu.uptc.exepciones;
/**
 * Exception thrown when a client (Cliente) cannot be created in the system.
 * This typically occurs when validation fails for required fields or data constraints.
 */
public class ClienteNocreado extends Exception {
    /**
     * Constructs a new ClienteNocreado exception with the specified detail message.
     *
     * @param message The detail message explaining why the client was not created
     */
    public ClienteNocreado(String message){
        super(message);
    } 
}
