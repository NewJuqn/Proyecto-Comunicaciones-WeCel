
package edu.uptc.Servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.uptc.Entidades.*;
import edu.uptc.exepciones.*;

/**
 * Service class that handles the business logic for the telecommunications company system.
 * Manages users (clients and advisors), plans (mobile and home), and PQRS (Petitions, Complaints, Claims, and Suggestions).
 * 
 * This class acts as an intermediary between the controller and the data structures,
 * implementing all CRUD operations and business rules.
 * 
 * @author Juan Jose Molina Chaparro, Julian Andres Gomez Solano
 * @version 1.0
 */
public class Servicios {
    /** Map storing all users indexed by their ID (cedula) */
    private TreeMap<String, Usuario> usuarios;
    
    /** Set containing all PQRS sorted by registration date and ID */
    private TreeSet<PQRS> PQRSlista;
    
    /** List of available mobile plans that can be contracted by clients */
    private ArrayList<PlanMovil> planesMovilesDisponibles;
    
    /** List of available home plans that can be contracted by clients */
    private ArrayList<PlanHogar> planesHogarDisponibles;

    /**
     * Constructor that initializes all data structures and creates a default admin user.
     * The admin user has credentials: ID="0", password="012"
     */
    public Servicios() {
        this.usuarios = new TreeMap<>();
        this.PQRSlista = new TreeSet<>(Comparator.comparing(PQRS::getFechaRegistro).thenComparingInt(PQRS::getIdPQRS));
        this.planesMovilesDisponibles = new ArrayList<>();
        this.planesHogarDisponibles = new ArrayList<>();
        usuarios.put("0", new Usuario("0", "admin", "", LocalDate.now(), "Colombia", "Boyaca", "Sogamoso", "012"));
    }

    /**
     * Registers a new client in the system.
     * Validates that all required fields are not null or empty before creating the client.
     * 
     * @param cedula Client's ID number
     * @param nombre Client's first name
     * @param apellido Client's last name
     * @param fechaNacimiento Client's birth date
     * @param pais Client's country
     * @param departamento Client's department/state
     * @param ciudad Client's city
     * @param numeroCelular Client's mobile phone number
     * @param contrasena Client's password
     * @return Success message indicating the client was registered
     * @throws ClienteNocreado if any required field is null or empty
     */
    public String registrarCliente(String cedula, String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String departamento, String ciudad, String numeroCelular, String contrasena)
            throws ClienteNocreado {

        if (nombre == null || apellido == null || fechaNacimiento == null ||
                pais == null || departamento == null || ciudad == null || contrasena == null || numeroCelular == null ||
                nombre.trim().isEmpty() || apellido.trim().isEmpty() ||
                pais.trim().isEmpty() || departamento.trim().isEmpty() ||
                ciudad.trim().isEmpty() || numeroCelular.trim().isEmpty() || contrasena.trim().isEmpty()) {
            throw new ClienteNocreado("vuelve a ingresar el cliente, algo salio mal");
        }

        Cliente nuevoCliente = new Cliente(cedula, nombre, apellido, fechaNacimiento, pais, departamento, ciudad,
                numeroCelular, contrasena);
        usuarios.put(nuevoCliente.getCedula(), nuevoCliente);
        return "Cliente registrado";
    }

    /**
     * Registers a new advisor in the system.
     * Validates that all required fields are not null or empty before creating the advisor.
     * 
     * @param cedula Advisor's ID number
     * @param nombre Advisor's first name
     * @param apellido Advisor's last name
     * @param fechaNacimiento Advisor's birth date
     * @param pais Advisor's country
     * @param departamento Advisor's department/state
     * @param ciudad Advisor's city
     * @param contrasena Advisor's password
     * @return Success message indicating the advisor was registered
     * @throws AsesorNocreado if any required field is null or empty
     */
    public String registrarAsesor(String cedula, String nombre, String apellido, LocalDate fechaNacimiento,
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

    /**
     * Removes an advisor from the system.
     * An advisor can only be deleted if they have no managed requests.
     * 
     * @param cedula The ID of the advisor to be deleted
     * @return Success message or information message if deletion is not possible
     * @throws AsesorNocreado if the advisor is not found
     */
    public String eliminarAsesor(String cedula) throws AsesorNocreado {
        Usuario usuario = buscarPorIdUsuarios(cedula);

        if (usuario == null || !(usuario instanceof Asesor)) {
            throw new AsesorNocreado("Asesor no encontrado");
        }

        Asesor asesor = (Asesor) usuario;

        if (!asesor.getSolicitudesGestionadas().isEmpty()) {
            return "No se puede eliminar el asesor porque tiene solicitudes gestionadas";
        }

        usuarios.remove(cedula);
        return "Asesor eliminado exitosamente";
    }

    /**
     * Retrieves all advisors registered in the system.
     * 
     * @return String of StringBuilder that containing all Asesor objects
     */
    public String obtenerAsesores() {
        ArrayList<Asesor> listaAsesores = new ArrayList<>();

        for (Usuario usuario : usuarios.values()) {
            if (usuario instanceof Asesor) {
                listaAsesores.add((Asesor) usuario);
            }
        }
        StringBuilder sb =new StringBuilder();
        for (Asesor asesor : listaAsesores) {
            sb.append(asesor).append("\n");
        }

        return sb.toString();
    }

    /**
     * Searches for a user by their ID.
     * 
     * @param id The user's ID (cedula)
     * @return The Usuario object if found, null otherwise
     */
    public Usuario buscarPorIdUsuarios(String id) {
        return usuarios.get(id);
    }

    /**
     * Authenticates a user and determines their role.
     * 
     * @param id User's ID (cedula)
     * @param contrasena User's password
     * @return String indicating the user type: "ADMIN", "ASESOR", or "CLIENTE"
     * @throws ContrasenaVacia if credentials are incorrect or user not found
     */
    public String login(String id, String contrasena) throws ContrasenaVacia {
        Usuario usuarioEncontrado = buscarPorIdUsuarios(id);
        if (usuarioEncontrado != null && usuarioEncontrado.getContrasena() != null) {
            if (usuarioEncontrado.getContrasena().equals(contrasena)) {
                if (usuarioEncontrado.getNombre().equalsIgnoreCase("admin")) {
                    return "ADMIN";
                }
                if (usuarioEncontrado instanceof Asesor) {
                    return "ASESOR";
                } else if (usuarioEncontrado instanceof Cliente) {
                    return "CLIENTE";
                }
            }
        }
        throw new ContrasenaVacia("Error en contraseña o cedula");
    }

    /**
     * Registers a mobile plan for a specific client.
     * The plan is immediately added to the client's list of contracted plans.
     * 
     * @param idCliente Client's ID
     * @param minutos Number of minutes included in the plan
     * @param gigas Amount of data in gigabytes
     * @param valorServicio Base service value
     * @param descuento Discount percentage to be applied
     * @return Success message
     * @throws UsuarioNoencontrado if the client is not found
     * @throws MinutosGigasnegativos if minutes or gigas are less than or equal to 0
     */
    public String registrarPlanMovil(String idCliente, int minutos, double gigas, double valorServicio,
            double descuento)
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

    /**
     * Registers a home plan for a specific client.
     * The plan is immediately added to the client's list of contracted plans.
     * 
     * @param idCliente Client's ID
     * @param tipoTV Type of TV service ("digital" or "analoga")
     * @param megasInternet Internet speed in megabytes
     * @param valorServicio Base service value
     * @param descuento Discount percentage to be applied
     * @return Success message
     * @throws UsuarioNoencontrado if the client is not found
     * @throws MegasNegativas if internet megas are less than or equal to 0
     * @throws TipoTVincorrectos if TV type is not "digital" or "analoga"
     */
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

        if (tipoTV == null || (!tipoTV.equalsIgnoreCase("digital") && !tipoTV.equalsIgnoreCase("analoga"))) {
            throw new TipoTVincorrectos("tipo de television incorrectos, ingrese los que son correctos");
        }

        Cliente cliente = (Cliente) usuario;
        cliente.getPlanes().add(new PlanHogar(LocalDate.now(), valorServicio, descuento, tipoTV, megasInternet));

        return "Plan hogar registrado exitosamente";
    }

    /**
     * Registers a new PQRS (Petition, Complaint, Claim, or Suggestion) for a client.
     * The PQRS must be associated with an existing plan owned by the client.
     * 
     * @param idCliente Client's ID
     * @param tipo Type of PQRS: 1=Petition, 2=Complaint, 3=Claim, 4=Suggestion
     * @param descripcion Description of the PQRS
     * @param idPlan ID of the plan associated with this PQRS
     * @return Success message
     * @throws UsuarioNoencontrado if the client is not found
     * @throws NoExistePlan if the plan is not found
     */
    public String registrarPQRS(String idCliente, int tipo, String descripcion, int idPlan)
            throws UsuarioNoencontrado, NoExistePlan {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }

        Plan planAsociado = obtenerPlanID(idPlan, idCliente);
        Cliente cliente = (Cliente) usuario;
        PQRS nuevaPQRS;

        switch (tipo) {
            case 1:
                nuevaPQRS = new Peticion(descripcion, planAsociado);
                break;
            case 2:
                nuevaPQRS = new Queja(descripcion, planAsociado);
                break;
            case 3:
                nuevaPQRS = new Reclamo(descripcion, planAsociado);
                break;
            case 4:
                nuevaPQRS = new Sugerencia(descripcion, planAsociado);
                break;
            default:
                return "Tipo de PQRS no válido";
        }

        cliente.getPQRSs().add(nuevaPQRS);
        PQRSlista.add(nuevaPQRS);
        return "PQRS registrada exitosamente";
    }

    /**
     * Retrieves all PQRS managed by a specific advisor.
     * 
     * @param idAsesor Advisor's ID
     * @return String containing the formatted list of managed PQRS
     * @throws UsuarioNoencontrado if the advisor is not found
     */
    public String obtenerTodasPQRSAsesor(String idAsesor) throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idAsesor);
        if (usuario == null || !(usuario instanceof Asesor)) {
            throw new UsuarioNoencontrado("Asesor no encontrado");
        }
        Asesor asesor = (Asesor) usuario;
        if (asesor.getSolicitudesGestionadas() == null || asesor.getSolicitudesGestionadas().isEmpty()) {
            return "No hay PQRS atendidas por este asesor.";
        }
        StringBuilder sb = new StringBuilder();
        for (PQRS pqrs : asesor.getSolicitudesGestionadas()) {
            sb.append(pqrs.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Retrieves all PQRS submitted by a specific client.
     * 
     * @param idCliente Client's ID
     * @return String containing the formatted list of client's PQRS
     * @throws UsuarioNoencontrado if the client is not found
     */
    public String obtenerPQRSCliente(String idCliente) throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Cliente cliente = (Cliente) usuario;
        StringBuilder sb = new StringBuilder();
        for (PQRS pqrsAux : cliente.getPQRSs()) {
            sb.append(pqrsAux.toString()).append("\n\n");
        }
        return sb.toString();
    }

    /**
     * Retrieves all PQRS registered in the system with detailed information.
     * Includes specific fields for each PQRS type (Petition, Complaint, Claim, Suggestion).
     * 
     * @return String containing all PQRS with their details
     * @throws PQRSNoEncontrada if there are no PQRS registered
     */
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

            if (pqrs instanceof Peticion pet) {
                texto.append("   [Petición] Resuelta: ").append(pet.isResuelta())
                        .append(" | Concepto solución: ").append(pet.getConceptoSolucion())
                        .append("\n");
            } else if (pqrs instanceof Queja queja) {
                texto.append("   [Queja] Nivel inconformismo: ").append(queja.getNivelInconformismo())
                        .append(" | Revisada: ").append(queja.isRevisada())
                        .append("\n");
            } else if (pqrs instanceof Reclamo reclamo) {
                texto.append("   [Reclamo] Recurso compensación: ").append(reclamo.getRecursoCompensacion())
                        .append(" | Resuelta: ").append(reclamo.isResuelta())
                        .append("\n");
            } else if (pqrs instanceof Sugerencia sug) {
                texto.append("   [Sugerencia] Nivel importancia: ").append(sug.getNivelImportancia())
                        .append("\n");
            } else {
                texto.append("   [Tipo desconocido]\n");
            }

            texto.append("\n");
        }

        return texto.toString();
    }

    /**
     * Retrieves all plans contracted by a specific client with their monthly payment details.
     * 
     * @param idCliente Client's ID
     * @return String containing all client's plans and total monthly payment
     * @throws UsuarioNoencontrado if the client is not found
     */
    public String obtenerPlanesCliente(String idCliente) throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Cliente cliente = (Cliente) usuario;
        StringBuilder sb = new StringBuilder();
        double totalPagoMensual = 0;
        for (Plan planAux : cliente.getPlanes()) {
            totalPagoMensual += planAux.calcularPagoMensual();
            sb.append(planAux.mostrarDetallesPlan()).append(" - Pago mensual: ").append(planAux.calcularPagoMensual())
                    .append("\n");
        }
        sb.append("Pago total: ").append(totalPagoMensual);
        return sb.toString();
    }

    /**
     * Resolves a PQRS by an advisor.
     * Different actions are taken based on the PQRS type:
     * - Petition: marks as resolved and adds solution concept
     * - Complaint: sets dissatisfaction level and marks as reviewed
     * - Claim: adds compensation resource and marks as resolved
     * - Suggestion: sets importance level
     * 
     * @param idAsesor Advisor's ID who is resolving the PQRS
     * @param idPQRS PQRS ID to be resolved
     * @param solucion Solution text (used for Petitions and Claims)
     * @param nivelesAux Auxiliary level value (used for Complaints and Suggestions)
     * @return Success message
     * @throws UsuarioNoencontrado if the advisor is not found
     * @throws PQRSNoEncontrada if the PQRS is not found
     */
    public String solucionarPQRS(String idAsesor, int idPQRS, String solucion, int nivelesAux)
            throws UsuarioNoencontrado, PQRSNoEncontrada {
        Usuario usuario = buscarPorIdUsuarios(idAsesor);
        if (usuario == null || !(usuario instanceof Asesor)) {
            throw new UsuarioNoencontrado("Asesor no encontrado");
        }
        PQRS pqrs = obtenerPQRSID(idPQRS);
        if (pqrs == null) {
            throw new PQRSNoEncontrada("PQRS no encontrada");
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

    /**
     * Modifies the description of an existing PQRS.
     * Only the client who owns the PQRS can modify it.
     * 
     * @param idCliente Client's ID
     * @param idPQRS PQRS ID to be modified
     * @param nuevaDescripcion New description for the PQRS
     * @return Success message
     * @throws UsuarioNoencontrado if the client is not found
     * @throws PQRSNoEncontrada if the PQRS is not found or doesn't belong to the client
     */
    public String modificarPQRS(String idCliente, int idPQRS, String nuevaDescripcion)
            throws UsuarioNoencontrado, PQRSNoEncontrada {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }

        PQRS pqrs = obtenerPQRSID(idPQRS);
        if (pqrs == null) {
            throw new PQRSNoEncontrada("PQRS no encontrada");
        }

        Cliente cliente = (Cliente) usuario;
        if (!cliente.getPQRSs().contains(pqrs)) {
            throw new PQRSNoEncontrada("Esta PQRS no pertenece al cliente");
        }

        pqrs.setDescripcion(nuevaDescripcion);
        return "PQRS modificada exitosamente";
    }

    /**
     * Searches for a PQRS by its ID in the system's PQRS list.
     * 
     * @param idPQRS The PQRS ID to search for
     * @return The PQRS object if found, null otherwise
     * @throws PQRSNoEncontrada if the PQRS list is empty
     */
    private PQRS obtenerPQRSID(int idPQRS) throws PQRSNoEncontrada {
        if (PQRSlista == null || PQRSlista.isEmpty()) {
            throw new PQRSNoEncontrada("No hay PQRS");
        }
        for (PQRS pqrsAux : PQRSlista) {
            if (pqrsAux.getIdPQRS() == idPQRS) {
                return pqrsAux;
            }
        }
        return null;
    }

    /**
     * Searches for a plan by its ID in a specific client's plan list.
     * 
     * @param idPlan The plan ID to search for
     * @param idUsuario The client's ID who owns the plan
     * @return The Plan object if found
     * @throws NoExistePlan if the plan is not found in the client's list
     */
    private Plan obtenerPlanID(int idPlan, String idUsuario) throws NoExistePlan {
        Usuario usuario = buscarPorIdUsuarios(idUsuario);
        Cliente cliente = (Cliente) usuario;
        for (Plan planAux : cliente.getPlanes()) {
            if (planAux.getIdPlan() == idPlan) {
                return planAux;
            }
        }
        throw new NoExistePlan("No existe ese plan con ese id");
    }

    /**
     * Deletes a PQRS from both the client's list and the system's global list.
     * Only the client who owns the PQRS can delete it.
     * 
     * @param idPQRS PQRS ID to be deleted
     * @param idUsuario Client's ID who owns the PQRS
     * @return Success message
     * @throws PQRSNoEncontrada if the PQRS is not found or client is not found
     */
    public String eliminarPQRSCliente(int idPQRS, String idUsuario) throws PQRSNoEncontrada {
        Usuario usuario = buscarPorIdUsuarios(idUsuario);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new PQRSNoEncontrada("Cliente no encontrado");
        }
        Cliente cliente = (Cliente) usuario;
        PQRS pqrsAEliminar = obtenerPQRSID(idPQRS);
        if (pqrsAEliminar != null && cliente.getPQRSs().remove(pqrsAEliminar)) {
            PQRSlista.remove(pqrsAEliminar);
            return "PQRS eliminada exitosamente";
        }
        throw new PQRSNoEncontrada("PQRS no encontrada");
    }
    /**
     * Creates a new mobile plan template available for clients to contract.
     * This is an admin function to populate the catalog of available plans.
     * 
     * @param minutos Number of minutes in the plan
     * @param gigas Amount of data in gigabytes
     * @param valorServicio Base service value
     * @param descuento Discount percentage
     * @return Success message
     * @throws MinutosGigasnegativos if minutes or gigas are less than or equal to 0
     */
    public String crearPlanMovilAdmin(int minutos, double gigas, double valorServicio, double descuento)
            throws MinutosGigasnegativos {
        if (minutos <= 0 || gigas <= 0) {
            throw new MinutosGigasnegativos("Los minutos y gigas deben ser mayores a 0");
        }
        PlanMovil nuevoPlan = new PlanMovil(LocalDate.now(), valorServicio, descuento, minutos, gigas);
        planesMovilesDisponibles.add(nuevoPlan);
        return "Plan móvil creado exitosamente";
    }
    /**
     * Creates a new home plan template available for clients to contract.
     * This is an admin function to populate the catalog of available plans.
     * 
     * @param tipoTV Type of TV service ("digital" or "análoga")
     * @param megasInternet Internet speed in megabytes
     * @param valorServicio Base service value
     * @param descuento Discount percentage
     * @return Success message
     * @throws MegasNegativas if internet megas are less than or equal to 0
     * @throws TipoTVincorrectos if TV type is invalid
     */
    public String crearPlanHogarAdmin(String tipoTV, int megasInternet, double valorServicio, double descuento)
            throws MegasNegativas, TipoTVincorrectos {
        if (megasInternet <= 0) {
            throw new MegasNegativas("Las megas deben ser mayores a 0");
        }
        if (!tipoTV.equalsIgnoreCase("digital") && !tipoTV.equalsIgnoreCase("análoga")) {
            throw new TipoTVincorrectos("Tipo de TV debe ser digital o análoga");
        }
        PlanHogar nuevoPlan = new PlanHogar(LocalDate.now(), valorServicio, descuento, tipoTV, megasInternet);
        planesHogarDisponibles.add(nuevoPlan);
        return "Plan hogar creado exitosamente";
    }
    /**
     * Retrieves all available mobile plans with their details and monthly payment.
     * 
     * @return Formatted string containing all available mobile plans
     */
    public String obtenerPlanesMovilesDisponibles() {
        StringBuilder mensaje = new StringBuilder("=== PLANES MOVILES DISPONIBLES ===\n");
        for (Plan planAux : planesMovilesDisponibles) {
            mensaje.append(planAux.mostrarDetallesPlan())
                    .append(" - Pago mensual: $")
                    .append(planAux.calcularPagoMensual())
                    .append("\n");
        }
        return mensaje.toString();
    }
    /**
     * Retrieves all available home plans with their details and monthly payment.
     * 
     * @return Formatted string containing all available home plans
     */
    public String obtenerPlanesHogarDisponibles() {
        StringBuilder mensaje = new StringBuilder("=== PLANES HOGAR DISPONIBLES ===\n");
        for (Plan planAux : planesHogarDisponibles) {
            mensaje.append(planAux.mostrarDetallesPlan())
                    .append(" - Pago mensual: $")
                    .append(planAux.calcularPagoMensual())
                    .append("\n");
        }
        return mensaje.toString();
    }
    /**
     * Allows a client to select and contract an available plan from the catalog.
     * Creates a copy of the selected plan and adds it to the client's plans.
     * The plan is searched first in mobile plans, then in home plans.
     * 
     * @param idCliente Client's ID
     * @param idPlan ID of the plan to be contracted
     * @return Success message or error if plan not found
     * @throws UsuarioNoencontrado if the client is not found
     */
    public String seleccionarPlanDisponible(String idCliente, int idPlan)
            throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Cliente cliente = (Cliente) usuario;
        Plan planSeleccionado = null;
        for (Plan planAux : planesMovilesDisponibles) {
            if (planAux.getIdPlan() == idPlan) {
                planSeleccionado = planAux;
                break;
            }
        }
        if (planSeleccionado == null) {
            for (Plan planAux : planesHogarDisponibles) {
                if (planAux.getIdPlan() == idPlan) {
                    planSeleccionado = planAux;
                    break;
                }
            }
        }
        if (planSeleccionado == null) {
            return "Plan no encontrado";
        }
        if (planSeleccionado instanceof PlanMovil original) {
            PlanMovil copia = new PlanMovil(LocalDate.now(), original.getValorServicio(), original.getDescuento(),
                    original.getMinutos(), original.getGigas());
            cliente.getPlanes().add(copia);
        } else if (planSeleccionado instanceof PlanHogar original) {
            PlanHogar copia = new PlanHogar(LocalDate.now(), original.getValorServicio(), original.getDescuento(),
                    original.getTipoTV(), original.getMegas());
            cliente.getPlanes().add(copia);
        }
        return "Plan contratado exitosamente";
    }
    /**
     * Allows a client to request a customized plan.
     * Creates a petition-type PQRS with the plan description and associates it
     * with a temporary plan. This request will be reviewed by an advisor.
     * 
     * @param idCliente Client's ID who is requesting the customized plan
     * @param descripcion Description of the desired customized plan
     * @return Success message
     * @throws UsuarioNoencontrado if the client is not found
     */
    public String solicitarPlanPersonalizado(String idCliente, String descripcion)
            throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Cliente cliente = (Cliente) usuario;
        Plan planTemporal = new PlanMovil(LocalDate.now(), 0, 0, 0, 0);
        Peticion solicitud = new Peticion("Solicitud de plan personalizado: " + descripcion, planTemporal);
        cliente.getPQRSs().add(solicitud);
        PQRSlista.add(solicitud);

        return "Solicitud de plan personalizado enviada exitosamente";
    }
    /**
     * Calculates the monthly payment for a specific plan.
     * Searches for the plan in both available mobile and home plan catalogs.
     * 
     * @param idPlan ID of the plan to calculate payment for
     * @return The monthly payment amount, or 0 if plan is not found
     */
    public double calcularPagoMensualPlan(int idPlan) {
        Plan plan = null;
        for (Plan planAux : planesMovilesDisponibles) {
            if (planAux.getIdPlan() == idPlan) {
                plan = planAux;
            }
        }
        if (plan == null) {
            for (Plan planAux : planesHogarDisponibles) {
                if (planAux.getIdPlan() == idPlan) {
                    plan = planAux;
                    break;
                }
            }
        }
        return plan != null ? plan.calcularPagoMensual() : 0;
    }
    /**
     * Retrieves the current logged-in user by their ID.
     * This method is used to maintain the user session in the GUI.
     * 
     * @param idCliente The user's ID (cedula)
     * @return The Usuario object corresponding to the given ID
     */
    public Usuario obtenerUsuarioActual(String idCliente) {
        return usuarios.get(idCliente);
    }
}
