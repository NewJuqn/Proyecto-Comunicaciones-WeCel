package edu.uptc.Entidades;

import java.time.LocalDate;
import java.util.ArrayList;

public class Asesor extends Usuario {
    private ArrayList<PQRS> solicitudesGestionadas;

    public Asesor(String nombre, String apellido, LocalDate fechaNacimiento, String pais, String estado,
            String ciudad, String contrasena) {
        super(nombre, apellido, fechaNacimiento, pais, estado, ciudad, contrasena);
        this.solicitudesGestionadas = new ArrayList<>();
    }
    public ArrayList<PQRS> getSolicitudesGestionadas() {
        return solicitudesGestionadas;
    }

    public void setSolicitudesGestionadas(ArrayList<PQRS> solicitudesGestionadas) {
        this.solicitudesGestionadas = solicitudesGestionadas;
    }
    @Override
    public String toString() {
        return "Asesor [solicitudesGestionadas=" + solicitudesGestionadas + "]";
    }

    

}
