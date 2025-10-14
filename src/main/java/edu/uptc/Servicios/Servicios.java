package edu.uptc.Servicios;

import java.util.ArrayList;

import edu.uptc.Entidades.*;

public class Servicios {
    private ArrayList<Cliente> clientes;
    private ArrayList<Asesor> asesores;
    private int contadorPlanes;
    private int contadorPQRS;
    public Servicios() {
        this.clientes = new ArrayList<>();
        this.asesores = new ArrayList<>();
        this.contadorPlanes = 0;
        this.contadorPQRS = 0;
    }
    
}
