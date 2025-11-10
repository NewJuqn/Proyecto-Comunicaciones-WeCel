package edu.uptc.Entidades;

import java.time.LocalDate;
import java.util.LinkedList;
/**
 * Represents an advisor in the system.
 * Extends Usuario and manages PQRS (Petitions, Complaints, Claims, Suggestions) resolution.
 * Advisors are responsible for handling and solving client requests.
 */
public class Asesor extends Usuario {
    /** List of PQRS that have been managed by this advisor */
    private LinkedList<PQRS> solicitudesGestionadas;

    /**
     * Constructs a new Asesor with the specified details.
     * Initializes an empty list for managed requests.
     *
     * @param cedula Advisor's identification number
     * @param nombre Advisor's first name
     * @param apellido Advisor's last name
     * @param fechaNacimiento Advisor's date of birth
     * @param pais Advisor's country
     * @param departamento Advisor's department/state
     * @param ciudad Advisor's city
     * @param contrasena Advisor's password
     */

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
