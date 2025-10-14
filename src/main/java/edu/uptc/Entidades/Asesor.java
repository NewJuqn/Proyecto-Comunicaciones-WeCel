package edu.uptc.Entidades;

import java.time.LocalDate;
import java.util.ArrayList;

public class Asesor extends Usuario {
    private int idAsesor;
    private ArrayList<PQRS> solicitudesGestionadas;

    public Asesor(int id, String nombre, String apellido, LocalDate fechaNacimiento, String pais, String estado,
            String ciudad, String contrasena, int idAsesor) {
        super(id, nombre, apellido, fechaNacimiento, pais, estado, ciudad, contrasena);
        this.idAsesor = idAsesor;
        this.solicitudesGestionadas = new ArrayList<>();
    }

    public int getIdAsesor() {
        return idAsesor;
    }

    public void setIdAsesor(int idAsesor) {
        this.idAsesor = idAsesor;
    }

    public ArrayList<PQRS> getSolicitudesGestionadas() {
        return solicitudesGestionadas;
    }

    public void setSolicitudesGestionadas(ArrayList<PQRS> solicitudesGestionadas) {
        this.solicitudesGestionadas = solicitudesGestionadas;
    }

    @Override
    public String toString() {
        return "Asesor [ID=" + getIdAsesor() + ", Nombre=" + getNombre() + " " + getApellido() +
                ", Solicitudes gestionadas=" + (solicitudesGestionadas != null ? solicitudesGestionadas.size() : 0)
                + "]";
    }

}
