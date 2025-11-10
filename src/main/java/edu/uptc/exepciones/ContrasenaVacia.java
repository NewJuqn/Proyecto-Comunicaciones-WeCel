package edu.uptc.exepciones;
/**
 * Exception thrown when a password (contraseña) is empty or invalid during authentication.
 * Used primarily in login operations to indicate authentication failures.
 */
public class ContrasenaVacia extends Exception {
    /**
     * Constructs a new ContrasenaVacia exception with the specified detail message.
     *
     * @param message The detail message explaining the password validation failure
     */
    public ContrasenaVacia(String message) {
        super(message);
    }
    
}
