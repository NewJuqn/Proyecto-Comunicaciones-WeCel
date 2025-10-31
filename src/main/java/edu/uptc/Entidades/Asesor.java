package edu.uptc.Entidades;

import java.time.LocalDate;
import java.util.LinkedList;

public class Asesor extends Usuario {
    private LinkedList<PQRS> solicitudesGestionadas;

    public Asesor(String cedula,String nombre, String apellido, LocalDate fechaNacimiento, String pais, String departamento,
            String ciudad, String contrasena) {
        super(cedula,nombre, apellido, fechaNacimiento, pais, departamento, ciudad, contrasena);
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
