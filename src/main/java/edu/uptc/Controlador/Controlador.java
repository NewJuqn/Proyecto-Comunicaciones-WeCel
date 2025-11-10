package edu.uptc.Controlador;

import java.time.LocalDate;

import edu.uptc.Entidades.*;
import edu.uptc.Servicios.Servicios;
import edu.uptc.exepciones.*;
/**
 * Controller class that acts as an intermediary between the GUI and the Service layer.
 * This class follows the MVC (Model-View-Controller) pattern, handling all requests
 * from the presentation layer and delegating business logic to the Servicios class.
 * 
 * The controller validates input from the GUI and manages the flow of data between
 * the view and the model, ensuring proper separation of concerns.
 * 
 * @author Juan Jose Molina Chaparro, Julian Andres Gomez Solano
 * @version 1.0
 * @see edu.uptc.Servicios.Servicios
 * @see edu.uptc.Gui.Gui
 */
public class Controlador {
    /** Service layer instance that handles business logic */
    private Servicios servicios;

    /**
     * Constructor that initializes the controller with a new Servicios instance.
     * The Servicios instance manages all business operations and data structures.
     */
    public Controlador() {
        this.servicios = new Servicios();
    }
    /**
     * Registers a new client in the system.
     * Delegates the registration process to the service layer after receiving
     * all necessary client information from the GUI.
     * 
     * @param cedula Client's ID number (unique identifier)
     * @param nombre Client's first name
     * @param apellido Client's last name
     * @param fechaNacimiento Client's birth date
     * @param pais Client's country of residence
     * @param departamento Client's department/state
     * @param ciudad Client's city
     * @param numeroCelular Client's mobile phone number
     * @param contrasena Client's password for authentication
     * @return Success message indicating the client was registered
     * @throws ClienteNocreado if any required field is null, empty, or registration fails
     */
    public String registrarCliente(String cedula,String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String departamento, String ciudad, String numeroCelular,String contrasena) throws ClienteNocreado {
        return servicios.registrarCliente(cedula, nombre, apellido, fechaNacimiento, pais, departamento, ciudad, numeroCelular,contrasena);
    }
    /**
     * Registers a new advisor in the system.
     * Delegates the registration process to the service layer after receiving
     * all necessary advisor information from the GUI.
     * 
     * @param cedula Advisor's ID number (unique identifier)
     * @param nombre Advisor's first name
     * @param apellido Advisor's last name
     * @param fechaNacimiento Advisor's birth date
     * @param pais Advisor's country of residence
     * @param departamento Advisor's department/state
     * @param ciudad Advisor's city
     * @param contrasena Advisor's password for authentication
     * @return Success message indicating the advisor was registered
     * @throws AsesorNocreado if any required field is null, empty, or registration fails
     */
    public String registrarAsesor(String cedula, String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String departamento, String ciudad, String contrasena) throws AsesorNocreado {
        return servicios.registrarAsesor(cedula, nombre, apellido, fechaNacimiento, pais, departamento, ciudad, contrasena);
    }
    /**
     * Searches for a user by their ID in the system.
     * Can return any type of user (Admin, Advisor, or Client).
     * 
     * @param id The user's ID (cedula) to search for
     * @return The Usuario object if found, null otherwise
     * @throws UsuarioNoencontrado if the user with the given ID does not exist
     */
    public Usuario buscarPorIdUsuarios(String id) throws UsuarioNoencontrado{
        return servicios.buscarPorIdUsuarios(id);
    }
    /**
     * Authenticates a user and determines their role in the system.
     * Validates the user's credentials and returns their type (ADMIN, ASESOR, or CLIENTE).
     * 
     * @param id User's ID (cedula)
     * @param contrasena User's password
     * @return String indicating the user type: "ADMIN", "ASESOR", or "CLIENTE"
     * @throws ContrasenaVacia if credentials are incorrect, user not found, or password is empty
     */
    public String login(String id, String contrasena) throws edu.uptc.exepciones.ContrasenaVacia {
        return servicios.login(id, contrasena);
    }
    /**
     * Retrieves the currently logged-in user information.
     * Used by the GUI to maintain user session data.
     * 
     * @param idCliente The user's ID (cedula)
     * @return The Usuario object corresponding to the given ID
     */
    public Usuario obtenerUsuarioActual(String idCliente) {
        return servicios.obtenerUsuarioActual(idCliente);
    }
    /**
     * Registers a new mobile plan for a specific client.
     * The plan is immediately added to the client's list of contracted plans.
     * 
     * @param idCliente Client's ID who will own the plan
     * @param minutos Number of voice minutes included in the plan
     * @param gigas Amount of mobile data in gigabytes
     * @param valorServicio Base monthly service cost
     * @param descuento Discount percentage to be applied (0-100)
     * @return Success message indicating the plan was registered
     * @throws UsuarioNoencontrado if the client with the given ID is not found
     * @throws MinutosGigasnegativos if minutes or gigas are less than or equal to zero
     */
    public String registrarPlanMovil(String idCliente, int minutos, double gigas, double valorServicio, double descuento)
            throws UsuarioNoencontrado, MinutosGigasnegativos {
        return servicios.registrarPlanMovil(idCliente, minutos, gigas, valorServicio, descuento);
    }
    /**
     * Registers a new home plan for a specific client.
     * The plan is immediately added to the client's list of contracted plans.
     * 
     * @param idCliente Client's ID who will own the plan
     * @param tipoTV Type of TV service: "digital" or "analoga"
     * @param megasInternet Internet speed in megabytes per second
     * @param valorServicio Base monthly service cost
     * @param descuento Discount percentage to be applied (0-100)
     * @return Success message indicating the plan was registered
     * @throws UsuarioNoencontrado if the client with the given ID is not found
     * @throws MegasNegativas if internet megas are less than or equal to zero
     * @throws TipoTVincorrectos if TV type is not "digital" or "analoga"
     */
    public String registrarPlanHogar(String idCliente, String tipoTV, int megasInternet, double valorServicio,
            double descuento) throws UsuarioNoencontrado, MegasNegativas, TipoTVincorrectos {
        return servicios.registrarPlanHogar(idCliente, tipoTV, megasInternet, valorServicio, descuento);
    }
    /**
     * Registers a new PQRS (Petition, Complaint, Claim, or Suggestion) for a client.
     * The PQRS must be associated with an existing plan owned by the client.
     * 
     * @param idCliente Client's ID who is submitting the PQRS
     * @param tipo Type of PQRS: 1=Peticion, 2=Queja, 3=Reclamo, 4=Sugerencia
     * @param descripcion Detailed description of the PQRS
     * @param idPlan ID of the plan this PQRS is related to
     * @return Success message indicating the PQRS was registered
     * @throws UsuarioNoencontrado if the client is not found
     * @throws NoExistePlan if the plan with the given ID does not exist or doesn't belong to the client
     */
    public String registrarPQRS(String idCliente, int tipo, String descripcion, int idPlan)
            throws UsuarioNoencontrado, NoExistePlan{
        return servicios.registrarPQRS(idCliente, tipo, descripcion, idPlan);
    }
    /**
     * Retrieves all PQRS that have been managed by a specific advisor.
     * Used by administrators to monitor advisor performance.
     * 
     * @param idAsesor Advisor's ID
     * @return Formatted string containing all PQRS managed by the advisor
     * @throws UsuarioNoencontrado if the advisor is not found
     */
    public String obtenerTodasPQRSAsesor(String idAsesor) throws UsuarioNoencontrado {
        return servicios.obtenerTodasPQRSAsesor(idAsesor);
    }
    /**
     * Retrieves all PQRS submitted by a specific client.
     * Used by clients to view their own requests and by advisors to view client history.
     * 
     * @param idCliente Client's ID
     * @return Formatted string containing all PQRS submitted by the client
     * @throws UsuarioNoencontrado if the client is not found
     */
    public String obtenerPQRSCliente(String idCliente) throws UsuarioNoencontrado {
        return servicios.obtenerPQRSCliente(idCliente);
    }
    /**
     * Retrieves all PQRS registered in the system with detailed information.
     * Includes type-specific fields for each PQRS (resolution status, importance levels, etc.).
     * Used primarily by advisors to view all pending and resolved requests.
     * 
     * @return Formatted string containing all PQRS in the system
     * @throws PQRSNoEncontrada if there are no PQRS registered in the system
     */

    public String obtenerTodosPQRS() throws PQRSNoEncontrada {
        return servicios.obtenerTodosPQRS();
    }
    /**
     * Retrieves all plans contracted by a specific client.
     * Shows detailed information for each plan including monthly payment and total cost.
     * 
     * @param idCliente Client's ID
     * @return Formatted string containing all client's plans with payment details
     * @throws UsuarioNoencontrado if the client is not found
     */
    public String obtenerPlanesCliente(String idCliente) throws UsuarioNoencontrado {
        return servicios.obtenerPlanesCliente(idCliente);
    }
    /**
     * Resolves a PQRS by recording the advisor's solution.
     * Different actions are taken based on the PQRS type:
     * - Peticion: marks as resolved and adds solution concept
     * - Queja: sets dissatisfaction level and marks as reviewed
     * - Reclamo: adds compensation resource and marks as resolved
     * - Sugerencia: sets importance level
     * 
     * The PQRS is added to the advisor's list of managed requests.
     * 
     * @param idAsesor Advisor's ID who is resolving the PQRS
     * @param idPQRS ID of the PQRS to be resolved
     * @param solucion Solution text or compensation details (used for Peticion and Reclamo)
     * @param nivelesAux Auxiliary level value (used for Queja and Sugerencia importance/dissatisfaction levels)
     * @return Success message indicating the PQRS was resolved
     * @throws UsuarioNoencontrado if the advisor is not found
     * @throws PQRSNoEncontrada if the PQRS with the given ID is not found
     */
    public String solucionarPQRS(String idAsesor, int idPQRS, String solucion, int nivelesAux) 
            throws UsuarioNoencontrado, PQRSNoEncontrada {
        return servicios.solucionarPQRS(idAsesor, idPQRS, solucion, nivelesAux);
    }
    /**
     * Modifies the description of an existing PQRS.
     * Only the client who owns the PQRS can modify it.
     * 
     * @param idCliente Client's ID who owns the PQRS
     * @param idPQRS ID of the PQRS to be modified
     * @param nuevaDescripcion New description text to replace the current one
     * @return Success message indicating the PQRS was modified
     * @throws UsuarioNoencontrado if the client is not found
     * @throws PQRSNoEncontrada if the PQRS is not found or doesn't belong to the client
     */
    public String modificarPQRS(String idCliente, int idPQRS, String nuevaDescripcion) 
            throws UsuarioNoencontrado, PQRSNoEncontrada {
        return servicios.modificarPQRS(idCliente, idPQRS, nuevaDescripcion);
    }
    /**
     * Deletes a PQRS from the system.
     * Removes the PQRS from both the client's personal list and the system's global list.
     * Only the client who owns the PQRS can delete it.
     * 
     * @param idPQRS ID of the PQRS to be deleted
     * @param idUsuario Client's ID who owns the PQRS
     * @return Success message indicating the PQRS was deleted
     * @throws PQRSNoEncontrada if the PQRS is not found or doesn't belong to the client
     */
    public String eliminarPQRSCliente(int idPQRS, String idUsuario) throws PQRSNoEncontrada{
        return servicios.eliminarPQRSCliente(idPQRS, idUsuario);
    }
    /**
     * Removes an advisor from the system.
     * An advisor can only be deleted if they have no managed requests.
     * This is an administrative function to maintain system integrity.
     * 
     * @param cedula ID of the advisor to be deleted
     * @return Success message or information message if deletion is not possible
     * @throws AsesorNocreado if the advisor is not found
     */
    public String eliminarAsesor(String cedula)throws AsesorNocreado{
        return servicios.eliminarAsesor(cedula);
    }
        /**
     * Retrieves all advisors registered in the system.
     * Used by administrators to view and manage the advisor workforce.
     * 
     * @return String containing all Asesor objects in the system
     */
    public String obtenerAsesores(){
        return servicios.obtenerAsesores();
    }
    /**
     * Creates a new mobile plan template in the available plans catalog.
     * This is an administrative function that populates the catalog of plans
     * that clients can choose to contract.
     * 
     * @param minutos Number of voice minutes included in the plan
     * @param gigas Amount of mobile data in gigabytes
     * @param valor Base monthly service cost
     * @param descuento Discount percentage to be applied (0-100)
     * @return Success message indicating the plan template was created
     * @throws MinutosGigasnegativos if minutes or gigas are less than or equal to zero
     */
    public String crearPlanMovilAdmin(int minutos, double gigas, double valor, double descuento) 
            throws MinutosGigasnegativos {
        return servicios.crearPlanMovilAdmin(minutos, gigas, valor, descuento);
    }
    /**
     * Creates a new home plan template in the available plans catalog.
     * This is an administrative function that populates the catalog of plans
     * that clients can choose to contract.
     * 
     * @param tipoTV Type of TV service: "digital" or "analoga"
     * @param megas Internet speed in megabytes per second
     * @param valor Base monthly service cost
     * @param descuento Discount percentage to be applied (0-100)
     * @return Success message indicating the plan template was created
     * @throws MegasNegativas if internet megas are less than or equal to zero
     * @throws TipoTVincorrectos if TV type is not "digital" or "analoga"
     */
    public String crearPlanHogarAdmin(String tipoTV, int megas, double valor, double descuento) 
            throws MegasNegativas, TipoTVincorrectos {
        return servicios.crearPlanHogarAdmin(tipoTV, megas, valor, descuento);
    }
    /**
     * Retrieves all available mobile plans from the catalog.
     * Shows plan details and monthly payment for each plan.
     * Used by clients to browse available plans before contracting.
     * 
     * @return Formatted string containing all available mobile plans with their details
     */
    public String obtenerPlanesMovilesDisponibles() {
        return servicios.obtenerPlanesMovilesDisponibles();
    }
    /**
     * Retrieves all available home plans from the catalog.
     * Shows plan details and monthly payment for each plan.
     * Used by clients to browse available plans before contracting.
     * 
     * @return Formatted string containing all available home plans with their details
     */
    public String obtenerPlanesHogarDisponibles() {
        return servicios.obtenerPlanesHogarDisponibles();
    }
    /**
     * Allows a client to select and contract an available plan from the catalog.
     * Creates a copy of the selected plan template and adds it to the client's contracted plans.
     * The plan is searched first in mobile plans, then in home plans.
     * 
     * @param idCliente Client's ID who is contracting the plan
     * @param idPlan ID of the plan template to be contracted
     * @return Success message or error message if plan is not found
     * @throws UsuarioNoencontrado if the client is not found
     */
    public String seleccionarPlanDisponible(String idCliente, int idPlan) 
            throws UsuarioNoencontrado {
        return servicios.seleccionarPlanDisponible(idCliente, idPlan);
    }
    /**
     * Allows a client to request a customized plan that doesn't match existing templates.
     * Creates a petition-type PQRS with the plan description for advisor review.
     * Advisors can then create a tailored plan based on the client's specific needs.
     * 
     * @param idCliente Client's ID who is requesting the customized plan
     * @param descripcion Detailed description of the desired customized plan features
     * @return Success message indicating the request was submitted
     * @throws UsuarioNoencontrado if the client is not found
     */
    public String solicitarPlanPersonalizado(String idCliente, String descripcion) 
            throws UsuarioNoencontrado {
        return servicios.solicitarPlanPersonalizado(idCliente, descripcion);
    }
    /**
     * Calculates the monthly payment for a specific plan.
     * Searches for the plan in both available mobile and home plan catalogs.
     * Applies the discount percentage to the base service value.
     * 
     * @param idPlan ID of the plan to calculate payment for
     * @return The monthly payment amount after discount, or 0 if plan is not found
     */
    public double calcularPagoMensualPlan(int idPlan) {
        return servicios.calcularPagoMensualPlan(idPlan);
    }
}
