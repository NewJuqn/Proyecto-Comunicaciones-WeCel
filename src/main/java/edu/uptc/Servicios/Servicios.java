package edu.uptc.Servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.uptc.Entidades.*;
import edu.uptc.exepciones.*;

public class Servicios {
    private TreeMap<String, Usuario> usuarios;
    private TreeSet<PQRS> PQRSlista;

    public Servicios() {
        this.usuarios = new TreeMap<>();
        this.PQRSlista = new TreeSet<>(Comparator.comparing(PQRS::getFechaRegistro).reversed());
    }

    public String registrarCliente(String cedula, String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String departamento, String ciudad, String contrasena) throws ClienteNocreado {

        if (nombre == null || apellido == null || fechaNacimiento == null ||
                pais == null || departamento == null || ciudad == null || contrasena == null ||
                nombre.trim().isEmpty() || apellido.trim().isEmpty() ||
                pais.trim().isEmpty() || departamento.trim().isEmpty() ||
                ciudad.trim().isEmpty() || contrasena.trim().isEmpty()) {
            throw new ClienteNocreado("vuelve a ingresar el cliente, algo salio mal");
        }

        Cliente nuevoCliente = new Cliente(cedula, nombre, apellido, fechaNacimiento,
                pais, departamento, ciudad, contrasena);
        usuarios.put(nuevoCliente.getCedula(), nuevoCliente);
        return "Cliente registrado";
    }

    public String registrarAsesor(String cedula,String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String departamento, String ciudad, String contrasena) throws AsesorNocreado {

        if (nombre == null || apellido == null || fechaNacimiento == null ||
                pais == null || departamento == null || ciudad == null || contrasena == null ||
                nombre.trim().isEmpty() || apellido.trim().isEmpty() ||
                pais.trim().isEmpty() || departamento.trim().isEmpty() ||
                ciudad.trim().isEmpty() || contrasena.trim().isEmpty()) {
            throw new AsesorNocreado("vuelve a ingresar el asesor, algo salio mal");
        }

        Asesor nuevoAsesor = new Asesor(cedula, nombre, apellido, fechaNacimiento,
                pais, departamento, ciudad, contrasena);
        usuarios.put(nuevoAsesor.getCedula(), nuevoAsesor);
        return "Asesor registrado";
    }

    public Usuario buscarPorIdUsuarios(String id){
        return usuarios.get(id);
    }

    public Usuario login(String id, String contrasena) throws ContrasenaVacia {
        Usuario usuarioEncontrado = buscarPorIdUsuarios(id);
        if (usuarioEncontrado != null && usuarioEncontrado.getContrasena().equalsIgnoreCase(contrasena)) {
            return usuarioEncontrado;
        }
        throw new ContrasenaVacia("Error en contraseña o cedula");
    }

    public String registrarPlanMovil(String idCliente, int minutos, double gigas, double valorServicio, double descuento)
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
        return "Plan móvil registrado exitosamente";
    }

    public String registrarPlanHogar(String idCliente, String tipoTV, int megasInternet, double valorServicio,
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

        return "Plan hogar registrado exitosamente";
    }

    public String registrarPQRS(String idCliente, int tipo, String descripcion, Plan planPQRS)
            throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);

        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }

        Cliente cliente = (Cliente) usuario;
        PQRS nuevaPQRS;

        switch (tipo) {
            case 1:
                nuevaPQRS = new Peticion(descripcion, planPQRS);
                break;
            case 2:
                nuevaPQRS = new Queja(descripcion, planPQRS);
                break;
            case 3:
                nuevaPQRS = new Reclamo(descripcion, planPQRS);
                break;
            case 4:
                nuevaPQRS = new Sugerencia(descripcion, planPQRS);
                break;
            default:
                return "Tipo de PQRS no válido";
        }

        cliente.getPQRSs().add(nuevaPQRS);
        PQRSlista.add(nuevaPQRS);
        return "PQRS registrada exitosamente";
    }

    public LinkedList<PQRS> obtenerTodasPQRSAsesor(String idAsesor) throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idAsesor);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Asesor asesor = (Asesor) usuario;
        return asesor.getSolicitudesGestionadas();
    }

    public ArrayList<PQRS> obtenerPQRSCliente(String idCliente) throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Cliente cliente = (Cliente) usuario;
        return cliente.getPQRSs();
    }

    public String obtenerTodosPQRS() throws PQRSNoEncontrada {
        if (PQRSlista == null || PQRSlista.isEmpty()) {
            throw new PQRSNoEncontrada("No hay PQRS registradas en el sistema.");
        }

        StringBuilder texto = new StringBuilder();

        for (PQRS pqrs : PQRSlista) {
            texto.append("ID: ").append(pqrs.getIdPQRS())
                    .append(" - Fecha: ").append(pqrs.getFechaRegistro())
                    .append(" - Tipo: ").append(pqrs.obtenerTipo())
                    .append(" - Descripción: ").append(pqrs.getDescripcion())
                    .append("\n");

            if (pqrs instanceof Peticion) {
                Peticion pet = (Peticion) pqrs;
                texto.append("   [Petición] Resuelta: ").append(pet.isResuelta())
                        .append(" | Concepto solución: ").append(pet.getConceptoSolucion())
                        .append("\n");
            } else if (pqrs instanceof Queja) {
                Queja queja = (Queja) pqrs;
                texto.append("   [Queja] Nivel inconformismo: ").append(queja.getNivelInconformismo())
                        .append(" | Revisada: ").append(queja.isRevisada())
                        .append("\n");
            } else if (pqrs instanceof Reclamo) {
                Reclamo reclamo = (Reclamo) pqrs;
                texto.append("   [Reclamo] Recurso compensación: ").append(reclamo.getRecursoCompensacion())
                        .append(" | Resuelta: ").append(reclamo.isResuelta())
                        .append("\n");
            } else if (pqrs instanceof Sugerencia) {
                Sugerencia sug = (Sugerencia) pqrs;
                texto.append("   [Sugerencia] Nivel importancia: ").append(sug.getNivelImportancia())
                        .append("\n");
            } else {
                texto.append("   [Tipo desconocido]\n");
            }

            texto.append("\n");
        }

        return texto.toString();
    }

    public ArrayList<Plan> obtenerPlanesCliente(String idCliente) throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Cliente cliente = (Cliente) usuario;
        return cliente.getPlanes();
    }

    public String solucionarPQRS(String idAsesor, PQRS pqrs, String solucion, int nivelesAux)
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

    public String modificarPQRS(String idCliente, PQRS pqrs, String nuevaDescripcion)
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
    public PQRS obtenerPQRSID(int idPQRS) throws PQRSNoEncontrada {
        if (PQRSlista==null || PQRSlista.isEmpty()) {
            throw new PQRSNoEncontrada("No hay PQRS");
        }
        for (PQRS pqrsAux : PQRSlista) {
            if (pqrsAux.getIdPQRS()==idPQRS) {
                return pqrsAux;
            }
        }
        return null;
    }
    public Plan obtenerPlanID(int idPlan, String idUsuario) throws NoExistePlan{
        Usuario usuario = buscarPorIdUsuarios(idUsuario);
        Cliente cliente = (Cliente) usuario;
        for (Plan planAux : cliente.getPlanes()) {
            if (planAux.getIdPlan()==idPlan) {
                return planAux;
            }
        }
        throw new NoExistePlan("No existe ese plan con ese id");
    }

    public String eliminarPQRSCliente(int idPQRS, String idUsuario) throws PQRSNoEncontrada{
        Cliente cliente = (Cliente) buscarPorIdUsuarios(idUsuario);
        for (PQRS pqrsAux : cliente.getPQRSs()) {
            if (pqrsAux.getIdPQRS()==idPQRS) {
                cliente.getPQRSs().remove(idPQRS);
            }
        }
        throw new PQRSNoEncontrada("PQRS no encontrada");
    }
}
