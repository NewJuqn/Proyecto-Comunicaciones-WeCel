package edu.uptc.Entidades;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Usuario {
    private ArrayList<Plan> planes;
    private ArrayList<PQRS> PQRSs;

    public Cliente(String cedula,String nombre, String apellido, LocalDate fechaNacimiento, String pais, String departamento,
            String ciudad, String contrasena) {
        super(cedula, nombre, apellido, fechaNacimiento, pais, departamento, ciudad, contrasena);
        this.planes = new ArrayList<>();
        this.PQRSs = new ArrayList<>();
    }

    public ArrayList<Plan> getPlanes() {
        return planes;
    }

    public void setPlanes(ArrayList<Plan> planes) {
        this.planes = planes;
    }

    @Override
    public String toString() {
        return "Cliente [cedula=" + getCedula() + ", Nombre=" + getNombre() + " " + getApellido() +
                ", Planes=" + (planes != null ? planes.size() : 0) + "]";
    }

    public ArrayList<PQRS> getPQRSs() {
        return PQRSs;
    }

    public void setPQRSs(ArrayList<PQRS> pQRSs) {
        PQRSs = pQRSs;
    }

}
