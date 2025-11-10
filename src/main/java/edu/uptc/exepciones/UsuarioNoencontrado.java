package edu.uptc.exepciones;
/**
 * Exception thrown when a user cannot be found in the system.
 * Used during user lookups, authentication, and operations requiring user validation.
 */
public class UsuarioNoencontrado extends Exception {
    /**
     * Constructs a new UsuarioNoencontrado exception with the specified detail message.
     *
     * @param message The detail message explaining which user was not found
     */
    public UsuarioNoencontrado(String message){
        super(message);
    }
    
}
