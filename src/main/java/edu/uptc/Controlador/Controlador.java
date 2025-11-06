package edu.uptc.Controlador;

import java.time.LocalDate;
import java.util.ArrayList;

import edu.uptc.Entidades.*;
import edu.uptc.Servicios.Servicios;
import edu.uptc.exepciones.*;

public class Controlador {
    private Servicios servicios;

    public Controlador() {
        this.servicios = new Servicios();
    }

    public String registrarCliente(String cedula,String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String departamento, String ciudad, String numeroCelular,String contrasena) throws ClienteNocreado {
        return servicios.registrarCliente(cedula, nombre, apellido, fechaNacimiento, pais, departamento, ciudad, numeroCelular,contrasena);
    }

    public String registrarAsesor(String cedula, String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String departamento, String ciudad, String contrasena) throws AsesorNocreado {
        return servicios.registrarAsesor(cedula, nombre, apellido, fechaNacimiento, pais, departamento, ciudad, contrasena);
    }

    public Usuario buscarPorIdUsuarios(String id) throws UsuarioNoencontrado{
        return servicios.buscarPorIdUsuarios(id);
    }

    public String login(String id, String contrasena) throws edu.uptc.exepciones.ContrasenaVacia {
        return servicios.login(id, contrasena);
    }

    public Usuario obtenerUsuarioActual(String idCliente) {
        return servicios.obtenerUsuarioActual(idCliente);
    }

    public String registrarPlanMovil(String idCliente, int minutos, double gigas, double valorServicio, double descuento)
            throws UsuarioNoencontrado, MinutosGigasnegativos {
        return servicios.registrarPlanMovil(idCliente, minutos, gigas, valorServicio, descuento);
    }

    public String registrarPlanHogar(String idCliente, String tipoTV, int megasInternet, double valorServicio,
            double descuento) throws UsuarioNoencontrado, MegasNegativas, TipoTVincorrectos {
        return servicios.registrarPlanHogar(idCliente, tipoTV, megasInternet, valorServicio, descuento);
    }

    public String registrarPQRS(String idCliente, int tipo, String descripcion, int idPlan)
            throws UsuarioNoencontrado, NoExistePlan{
        return servicios.registrarPQRS(idCliente, tipo, descripcion, idPlan);
    }

    public String obtenerTodasPQRSAsesor(String idAsesor) throws UsuarioNoencontrado {
        return servicios.obtenerTodasPQRSAsesor(idAsesor);
    }

    public String obtenerPQRSCliente(String idCliente) throws UsuarioNoencontrado {
        return servicios.obtenerPQRSCliente(idCliente);
    }

    public String obtenerTodosPQRS() throws PQRSNoEncontrada {
        return servicios.obtenerTodosPQRS();
    }

    public String obtenerPlanesCliente(String idCliente) throws UsuarioNoencontrado {
        return servicios.obtenerPlanesCliente(idCliente);
    }

    public String solucionarPQRS(String idAsesor, int idPQRS, String solucion, int nivelesAux) 
            throws UsuarioNoencontrado, PQRSNoEncontrada {
        return servicios.solucionarPQRS(idAsesor, idPQRS, solucion, nivelesAux);
    }

    public String modificarPQRS(String idCliente, int idPQRS, String nuevaDescripcion) 
            throws UsuarioNoencontrado, PQRSNoEncontrada {
        return servicios.modificarPQRS(idCliente, idPQRS, nuevaDescripcion);
    }


    public String eliminarPQRSCliente(int idPQRS, String idUsuario) throws PQRSNoEncontrada{
        return servicios.eliminarPQRSCliente(idPQRS, idUsuario);
    }

    public String eliminarAsesor(String cedula)throws AsesorNocreado{
        return servicios.eliminarAsesor(cedula);
    }
    public ArrayList<Asesor> obtenerAsesores(){
        return servicios.obtenerAsesores();
    }

    public String crearPlanMovilAdmin(int minutos, double gigas, double valor, double descuento) 
            throws MinutosGigasnegativos {
        return servicios.crearPlanMovilAdmin(minutos, gigas, valor, descuento);
    }

    public String crearPlanHogarAdmin(String tipoTV, int megas, double valor, double descuento) 
            throws MegasNegativas, TipoTVincorrectos {
        return servicios.crearPlanHogarAdmin(tipoTV, megas, valor, descuento);
    }

    public String obtenerPlanesMovilesDisponibles() {
        return servicios.obtenerPlanesMovilesDisponibles();
    }

    public String obtenerPlanesHogarDisponibles() {
        return servicios.obtenerPlanesHogarDisponibles();
    }

    public String seleccionarPlanDisponible(String idCliente, int idPlan) 
            throws UsuarioNoencontrado {
        return servicios.seleccionarPlanDisponible(idCliente, idPlan);
    }

    public String solicitarPlanPersonalizado(String idCliente, String descripcion) 
            throws UsuarioNoencontrado {
        return servicios.solicitarPlanPersonalizado(idCliente, descripcion);
    }

    public double calcularPagoMensualPlan(int idPlan) {
        return servicios.calcularPagoMensualPlan(idPlan);
    }
}
