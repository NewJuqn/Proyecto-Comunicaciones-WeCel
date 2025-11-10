package edu.uptc.exepciones;
/**
 * Exception thrown when attempting to access or reference a plan that does not exist in the system.
 * Used during plan lookups and PQRS associations.
 */
public class NoExistePlan extends Exception {
    /**
     * Constructs a new NoExistePlan exception with the specified detail message.
     *
     * @param message The detail message explaining which plan was not found
     */
    public NoExistePlan(String message) {
        super(message);
    }
}
