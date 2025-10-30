package edu.uptc.Servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import edu.uptc.Entidades.*;
import edu.uptc.exepciones.*;

public class Servicios {
    private TreeMap<Integer, Usuario> usuarios;

    private int contadorPlanes;
    private int contadorPQRS;

    public Servicios() {
        this.usuarios = new TreeMap<>();
        this.contadorPlanes = 0;
        this.contadorPQRS = 0;
    }

    public String registrarCliente(String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String estado, String ciudad, String contrasena) throws ClienteNocreado {

        if (nombre == null || apellido == null || fechaNacimiento == null ||
                pais == null || estado == null || ciudad == null || contrasena == null ||
                nombre.trim().isEmpty() || apellido.trim().isEmpty() ||
                pais.trim().isEmpty() || estado.trim().isEmpty() ||
                ciudad.trim().isEmpty() || contrasena.trim().isEmpty()) {
            throw new ClienteNocreado("vuelve a ingresar el cliente, algo salio mal");
        }

        Cliente nuevoCliente = new Cliente(nombre, apellido, fechaNacimiento,
                pais, estado, ciudad, contrasena);
        usuarios.put(nuevoCliente.getId(), nuevoCliente);
        return "Cliente registrado";
    }

    public String registrarAsesor(String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String estado, String ciudad, String contrasena) throws AsesorNocreado {

        if (nombre == null || apellido == null || fechaNacimiento == null ||
                pais == null || estado == null || ciudad == null || contrasena == null ||
                nombre.trim().isEmpty() || apellido.trim().isEmpty() ||
                pais.trim().isEmpty() || estado.trim().isEmpty() ||
                ciudad.trim().isEmpty() || contrasena.trim().isEmpty()) {
            throw new AsesorNocreado("vuelve a ingresar el asesor, algo salio mal");
        }

        Asesor nuevoAsesor = new Asesor(nombre, apellido, fechaNacimiento,
                pais, estado, ciudad, contrasena);
        usuarios.put(nuevoAsesor.getId(), nuevoAsesor);
        return "Asesor registrado";
    }

    public Usuario buscarPorIdUsuarios(int id) {
        return usuarios.get(id);
    }

    public Usuario login(int id, String contrasena) {
        Usuario usuarioEncontrado = buscarPorIdUsuarios(id);
        if (usuarioEncontrado != null && usuarioEncontrado.getContrasena().equalsIgnoreCase(contrasena)) {
            return usuarioEncontrado;
        }
        return null;
    }

    public String registrarPlanMovil(int idCliente, int minutos, double gigas, double valorServicio, double descuento)
            throws UsuarioNoencontrado, MinutosGigasnegativos {
        Usuario usuario = buscarPorIdUsuarios(idCliente);

        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("id o contraseña incorrectos, intenta volver a ingresar");
        }

        if (minutos <= 0 || gigas <= 0) {
            throw new MinutosGigasnegativos("ingrese numeros mayores a 0 en las opciones giga y minutos");
        }

        Cliente cliente = (Cliente) usuario;
        cliente.getPlanes().add(new PlanMovil(LocalDate.now(), valorServicio, descuento, minutos, gigas));
        contadorPlanes++;
        return "Plan móvil registrado exitosamente";
    }

    public String registrarPlanHogar(int idCliente, String tipoTV, int megasInternet, double valorServicio,
            double descuento)
            throws UsuarioNoencontrado, MegasNegativas, TipoTVincorrectos {
        Usuario usuario = buscarPorIdUsuarios(idCliente);

        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("id o contraseña incorrectos, intenta volver a ingresar");
        }

        if (megasInternet <= 0) {
            throw new MegasNegativas("ingrese un numero mayor a 0 en la megas");
        }

        if (tipoTV == null || (!tipoTV.equalsIgnoreCase("digital") && !tipoTV.equalsIgnoreCase("análoga"))) {
            throw new TipoTVincorrectos("tipo de television incorrectos, ingrese los que son correctos");
        }

        Cliente cliente = (Cliente) usuario;
        cliente.getPlanes().add(new PlanHogar(LocalDate.now(), valorServicio, descuento, tipoTV, megasInternet));
        contadorPlanes++;

        return "Plan hogar registrado exitosamente";
    }

    public String registrarPQRS(int idCliente, String tipo, String descripcion, Plan planPQRS)
            throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);

        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }

        Cliente cliente = (Cliente) usuario;
        PQRS nuevaPQRS;

        switch (tipo.toLowerCase()) {
            case "peticion":
                nuevaPQRS = new Peticion(descripcion, planPQRS);
                break;
            case "queja":
                nuevaPQRS = new Queja(descripcion, planPQRS);
                break;
            case "reclamo":
                nuevaPQRS = new Reclamo(descripcion, planPQRS);
                break;
            case "sugerencia":
                nuevaPQRS = new Sugerencia(descripcion,planPQRS);
                break;
            default:
                return "Tipo de PQRS no válido";
        }

        cliente.getPQRSs().add(nuevaPQRS);
        contadorPQRS++;
        return "PQRS registrada exitosamente";
    }

    public LinkedList<PQRS> obtenerTodasPQRSAsesor(int idAsesor) throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idAsesor);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Asesor asesor = (Asesor) usuario;
        return asesor.getSolicitudesGestionadas();
    }

    public ArrayList<PQRS> obtenerPQRSCliente(int idCliente) throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Cliente cliente = (Cliente) usuario;
        return cliente.getPQRSs();
    }

    public ArrayList<Plan> obtenerPlanesCliente(int idCliente) throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Cliente cliente = (Cliente) usuario;
        return cliente.getPlanes();
    }

    public String solucionarPQRS(int idAsesor, PQRS pqrs, String solucion, int nivelesAux)
            throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idAsesor);
        if (usuario == null || !(usuario instanceof Asesor)) {
            throw new UsuarioNoencontrado("Asesor no encontrado");
        }

        Asesor asesor = (Asesor) usuario;
        String tipo = pqrs.obtenerTipo().toLowerCase();
        switch (tipo) {
            case "peticion":
                Peticion peticion = (Peticion) pqrs;
                peticion.setResuelta(true);
                peticion.setConceptoSolucion(solucion);
                break;
            case "queja":
                Queja queja = (Queja) pqrs;
                queja.setNivelInconformismo(nivelesAux);
                queja.setRevisada(true);
                break;
            case "reclamo":
                Reclamo reclamo = (Reclamo) pqrs;
                reclamo.setRecursoCompensacion(solucion);
                reclamo.setResuelta(true);
                break;
            case "sugerencia":
                Sugerencia sugerencia = (Sugerencia) pqrs;
                sugerencia.setNivelImportancia(nivelesAux);
                break;
            default:
                return "Tipo de PQRS no válido";
        }
        asesor.getSolicitudesGestionadas().add(pqrs);
        return "PQRS solucionada exitosamente";
    }

    public String modificarPQRS(int idCliente, PQRS pqrs, String nuevaDescripcion)
            throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }

        Cliente cliente = (Cliente) usuario;
        if (!cliente.getPQRSs().contains(pqrs)) {
            return "PQRS no encontrada para este cliente";
        }

        pqrs.setDescripcion(nuevaDescripcion);
        return "PQRS modificada exitosamente";
    }

}
