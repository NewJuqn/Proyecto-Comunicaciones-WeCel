package edu.uptc.Entidades;

import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Represents a client in the system.
 * Extends Usuario and adds client-specific functionality including plan management and PQRS tracking.
 * Clients can subscribe to multiple plans and submit PQRS (Petitions, Complaints, Claims, Suggestions).
 */
public class Cliente extends Usuario {
    /** Client's mobile phone number */
    private String numeroCelular;
    
    /** List of plans subscribed by the client */
    private ArrayList<Plan> planes;
    
    /** List of PQRS submitted by the client */
    private ArrayList<PQRS> PQRSs;

    /**
     * Constructs a new Cliente with the specified details.
     * Initializes empty lists for plans and PQRS.
     *
     * @param cedula Client's identification number
     * @param nombre Client's first name
     * @param apellido Client's last name
     * @param fechaNacimiento Client's date of birth
     * @param pais Client's country
     * @param departamento Client's department/state
     * @param ciudad Client's city
     * @param numeroCelular Client's mobile phone number
     * @param contrasena Client's password
     */
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
