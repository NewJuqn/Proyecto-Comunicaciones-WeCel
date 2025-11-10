package edu.uptc.exepciones;
/**
 * Exception thrown when negative or invalid megabyte values are provided for internet plans.
 * Used to validate that internet speed specifications are positive numbers.
 */
public class MegasNegativas extends Exception {
    /**
     * Constructs a new MegasNegativas exception with the specified detail message.
     *
     * @param Message The detail message explaining the invalid megabyte value
     */
    public MegasNegativas(String Message){
        super(Message);
    }
}
