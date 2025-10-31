package edu.uptc.Controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.uptc.Entidades.*;
import edu.uptc.Servicios.Servicios;
import edu.uptc.exepciones.*;

public class Controlador {
    private Servicios servicios;

    public Controlador() {
        this.servicios = new Servicios();
    }

    public String registrarCliente(String cedula,String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String departamento, String ciudad, String contrasena) throws ClienteNocreado {
        return servicios.registrarCliente(cedula, nombre, apellido, fechaNacimiento, pais, departamento, ciudad, contrasena);
    }

    public String registrarAsesor(String cedula, String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String departamento, String ciudad, String contrasena) throws AsesorNocreado {
        return servicios.registrarAsesor(cedula, nombre, apellido, fechaNacimiento, pais, departamento, ciudad, contrasena);
    }

    public Usuario buscarPorIdUsuarios(String id) throws UsuarioNoencontrado{
        return servicios.buscarPorIdUsuarios(id);
    }

    public Usuario login(String id, String contrasena) throws ContrasenaVacia{
        return servicios.login(id, contrasena);
    }

    public String registrarPlanMovil(String idCliente, int minutos, double gigas, double valorServicio, double descuento)
            throws UsuarioNoencontrado, MinutosGigasnegativos {
        return servicios.registrarPlanMovil(idCliente, minutos, gigas, valorServicio, descuento);
    }

    public String registrarPlanHogar(String idCliente, String tipoTV, int megasInternet, double valorServicio,
            double descuento) throws UsuarioNoencontrado, MegasNegativas, TipoTVincorrectos {
        return servicios.registrarPlanHogar(idCliente, tipoTV, megasInternet, valorServicio, descuento);
    }

    public String registrarPQRS(String idCliente, int tipo, String descripcion, Plan planPQRS)
            throws UsuarioNoencontrado {
        return servicios.registrarPQRS(idCliente, tipo, descripcion, planPQRS);
    }

    public LinkedList<PQRS> obtenerTodasPQRSAsesor(String idAsesor) throws UsuarioNoencontrado {
        return servicios.obtenerTodasPQRSAsesor(idAsesor);
    }

    public ArrayList<PQRS> obtenerPQRSCliente(String idCliente) throws UsuarioNoencontrado {
        return servicios.obtenerPQRSCliente(idCliente);
    }

    public String obtenerTodosPQRS() throws PQRSNoEncontrada {
        return servicios.obtenerTodosPQRS();
    }

    public ArrayList<Plan> obtenerPlanesCliente(String idCliente) throws UsuarioNoencontrado {
        return servicios.obtenerPlanesCliente(idCliente);
    }

    public String solucionarPQRS(String idAsesor, PQRS pqrs, String solucion, int nivelesAux)
            throws UsuarioNoencontrado {
        return servicios.solucionarPQRS(idAsesor, pqrs, solucion, nivelesAux);
    }

    public String modificarPQRS(String idCliente, PQRS pqrs, String nuevaDescripcion)
            throws UsuarioNoencontrado {
        return servicios.modificarPQRS(idCliente, pqrs, nuevaDescripcion);
    }

    public PQRS obtenerPQRSID(int idPQRS) throws PQRSNoEncontrada {
        return servicios.obtenerPQRSID(idPQRS);
    }

    public Plan obtenerPlanID(int idPlan, String idUsuario) throws NoExistePlan{
        return servicios.obtenerPlanID(idPlan, idUsuario);
    }

    public String eliminarPQRSCliente(int idPQRS, String idUsuario) throws PQRSNoEncontrada{
        return servicios.eliminarPQRSCliente(idPQRS, idUsuario);
    }
}
