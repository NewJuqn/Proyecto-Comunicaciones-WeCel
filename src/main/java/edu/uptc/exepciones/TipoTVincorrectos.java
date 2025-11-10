package edu.uptc.exepciones;
/**
 * Exception thrown when an invalid TV type is specified for home plans.
 * Valid TV types are typically "digital" or "análoga" (analog).
 */
public class TipoTVincorrectos extends Exception {
    /**
     * Constructs a new TipoTVincorrectos exception with the specified detail message.
     *
     * @param message The detail message explaining the invalid TV type
     */
    public TipoTVincorrectos(String message){
        super(message);
    }
    
}
