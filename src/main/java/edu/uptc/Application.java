package edu.uptc;

import edu.uptc.Controlador.Controlador;
import edu.uptc.Gui.Gui;
/**
 * Main application entry point for the WeCel communications system.
 * This class initializes the GUI and starts the application.
 * @author Juan Jose Molina Chaparro, Julian Andres Gomez Solano
 * @version 1.0
 */
public class Application {

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        Gui gui = new Gui(controlador);
        gui.iniciar();
    }
}
