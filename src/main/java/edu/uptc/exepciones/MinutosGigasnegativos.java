package edu.uptc.exepciones;
/**
 * Exception thrown when negative or invalid values are provided for minutes or gigabytes in mobile plans.
 * Used to validate that mobile plan specifications contain only positive values.
 */
public class MinutosGigasnegativos extends Exception {
    /**
     * Constructs a new MinutosGigasnegativos exception with the specified detail message.
     *
     * @param message The detail message explaining the invalid minute or gigabyte value
     */
    public MinutosGigasnegativos(String message){
        super(message);
    }
}
