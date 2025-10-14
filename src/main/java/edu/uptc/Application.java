package edu.uptc;

import edu.uptc.Controlador.Controlador;
import edu.uptc.Gui.Gui;

public class Application {

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        Gui gui = new Gui(controlador);
        gui.iniciar();
    }
}
