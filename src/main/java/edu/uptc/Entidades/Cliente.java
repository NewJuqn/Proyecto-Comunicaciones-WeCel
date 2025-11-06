package edu.uptc.Entidades;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Usuario {
    private String numeroCelular;
    private ArrayList<Plan> planes;
    private ArrayList<PQRS> PQRSs;

    public Cliente(String cedula,String nombre, String apellido, LocalDate fechaNacimiento, String pais, String departamento,
            String ciudad, String numeroCelular,String contrasena) {
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

    public ArrayList<PQRS> getPQRSs() {
        return PQRSs;
    }

    public void setPQRSs(ArrayList<PQRS> pQRSs) {
        PQRSs = pQRSs;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente{");
        sb.append("numeroCelular=").append(numeroCelular);
        sb.append(", planes=").append(planes);
        sb.append(", PQRSs=").append(PQRSs);
        sb.append('}');
        return sb.toString();
    }

}
