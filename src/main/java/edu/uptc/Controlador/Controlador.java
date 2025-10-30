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

    public String registrarCliente(String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String estado, String ciudad, String contrasena) throws ClienteNocreado {
        return servicios.registrarCliente(nombre, apellido, fechaNacimiento, pais, estado, ciudad, contrasena);
    }

    public String registrarAsesor(String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String estado, String ciudad, String contrasena) throws AsesorNocreado {
        return servicios.registrarAsesor(nombre, apellido, fechaNacimiento, pais, estado, ciudad, contrasena);
    }

    public Usuario buscarPorIdUsuarios(int id) {
        return servicios.buscarPorIdUsuarios(id);
    }

    public Usuario login(int id, String contrasena) {
        return servicios.login(id, contrasena);
    }

    public String registrarPlanMovil(int idCliente, int minutos, double gigas, double valorServicio, double descuento)
            throws UsuarioNoencontrado, MinutosGigasnegativos {
        return servicios.registrarPlanMovil(idCliente, minutos, gigas, valorServicio, descuento);
    }

    public String registrarPlanHogar(int idCliente, String tipoTV, int megasInternet, double valorServicio,
            double descuento) throws UsuarioNoencontrado, MegasNegativas, TipoTVincorrectos {
        return servicios.registrarPlanHogar(idCliente, tipoTV, megasInternet, valorServicio, descuento);
    }

    public String registrarPQRS(int idCliente, int tipo, String descripcion, Plan planPQRS)
            throws UsuarioNoencontrado {
        return servicios.registrarPQRS(idCliente, tipo, descripcion, planPQRS);
    }

    public LinkedList<PQRS> obtenerTodasPQRSAsesor(int idAsesor) throws UsuarioNoencontrado {
        return servicios.obtenerTodasPQRSAsesor(idAsesor);
    }

    public ArrayList<PQRS> obtenerPQRSCliente(int idCliente) throws UsuarioNoencontrado {
        return servicios.obtenerPQRSCliente(idCliente);
    }

    public String obtenerTodosPQRS() throws PQRSNoEncontrada {
        return servicios.obtenerTodosPQRS();
    }

    public ArrayList<Plan> obtenerPlanesCliente(int idCliente) throws UsuarioNoencontrado {
        return servicios.obtenerPlanesCliente(idCliente);
    }

    public String solucionarPQRS(int idAsesor, PQRS pqrs, String solucion, int nivelesAux)
            throws UsuarioNoencontrado {
        return servicios.solucionarPQRS(idAsesor, pqrs, solucion, nivelesAux);
    }

    public String modificarPQRS(int idCliente, PQRS pqrs, String nuevaDescripcion)
            throws UsuarioNoencontrado {
        return servicios.modificarPQRS(idCliente, pqrs, nuevaDescripcion);
    }
}
