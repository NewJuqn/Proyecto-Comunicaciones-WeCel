package edu.uptc.Entidades;

import java.time.LocalDate;
import java.util.LinkedList;

public class Asesor extends Usuario {
    private LinkedList<PQRS> solicitudesGestionadas;

    public Asesor(String nombre, String apellido, LocalDate fechaNacimiento, String pais, String estado,
            String ciudad, String contrasena) {
        super(nombre, apellido, fechaNacimiento, pais, estado, ciudad, contrasena);
        this.solicitudesGestionadas = new LinkedList<>();
    }
    
    @Override
    public String toString() {
        return "Asesor [solicitudesGestionadas=" + solicitudesGestionadas + "]";
    }

    public LinkedList<PQRS> getSolicitudesGestionadas() {
        return solicitudesGestionadas;
    }

    public void setSolicitudesGestionadas(LinkedList<PQRS> solicitudesGestionadas) {
        this.solicitudesGestionadas = solicitudesGestionadas;
    }

    

}
