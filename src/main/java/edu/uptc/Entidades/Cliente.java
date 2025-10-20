package edu.uptc.Entidades;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Usuario {
    private ArrayList<Plan> planes;

    public Cliente(String nombre, String apellido, LocalDate fechaNacimiento, String pais, String estado,
            String ciudad, String contrasena) {
        super(nombre, apellido, fechaNacimiento, pais, estado, ciudad, contrasena);
        this.planes = new ArrayList<>();
    }

    public ArrayList<Plan> getPlanes() {
        return planes;
    }

    public void setPlanes(ArrayList<Plan> planes) {
        this.planes = planes;
    }

    @Override
    public String toString() {
        return "Cliente [ID=" + getId() + ", Nombre=" + getNombre() + " " + getApellido() +
                ", Planes=" + (planes != null ? planes.size() : 0) + "]";
    }

}
