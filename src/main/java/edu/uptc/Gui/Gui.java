package edu.uptc.Gui;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import edu.uptc.Controlador.Controlador;
import edu.uptc.Entidades.Usuario;
import edu.uptc.exepciones.AsesorNocreado;
import edu.uptc.exepciones.ClienteNocreado;
import edu.uptc.exepciones.ContrasenaVacia;
import edu.uptc.exepciones.MegasNegativas;
import edu.uptc.exepciones.MinutosGigasnegativos;
import edu.uptc.exepciones.NoExistePlan;
import edu.uptc.exepciones.PQRSNoEncontrada;
import edu.uptc.exepciones.TipoTVincorrectos;
import edu.uptc.exepciones.UsuarioNoencontrado;
/**
 * Graphical User Interface class that handles all user interactions through dialog boxes.
 * This class manages the presentation layer of the application, displaying menus
 * and handling user input for different types of users (Admin, Advisor, Client).
 * 
 * Uses Java Swing's JOptionPane for displaying menus and collecting user input.
 * All business logic is delegated to the Controlador class.
 * 
 * @author Juan Jose Molina Chaparro, Julian Andres Gomez Solano
 * @version 1.0
 */
public class Gui {
    /** Controller instance that handles business logic */
    private Controlador controlador;
    
    /** Currently logged-in user */
    private Usuario usuarioLogueado;

    /**
    * Constructor that initializes the GUI with a controller.
    * 
    * @param controlador The controller instance to handle business operations
    */

    public Gui(Controlador controlador) {
        this.controlador = controlador;
    }
    /**
     * Starts the main application loop.
     * Displays the principal menu with options to login, register as client, or exit.
     * Continues running until the user selects the exit option.
     */
    public void iniciar() {
        int opcionPrincipal = 0;
        do {
            try {
                opcionPrincipal = Integer.parseInt(JOptionPane.showInputDialog(null, """
                         Seleccione una opcion:
                        1 - Login administrador
                        2 - Registrar Cliente
                        3 - Salir
                         """, "Menu Principal", JOptionPane.PLAIN_MESSAGE));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un numero valido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                switch (opcionPrincipal) {
                    case 1:
                        opcionLogin();
                        break;
                    case 2:
                        opcionRegistrarCliente();
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(null, "Saliendo...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opcion no valida.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Excepcion",
                        JOptionPane.ERROR_MESSAGE);
            }
        } while (opcionPrincipal != 3);
    }
    /**
     * Displays the advisor menu with available operations.
     * Includes options to view PQRS, solve PQRS, register plans for clients, and logout.
     * Continues running until the advisor selects the logout option.
     */
    public void menuAsesor() {
        int opcionMenuAsesor = 0;
        do {
            try {
                opcionMenuAsesor = Integer.parseInt(JOptionPane.showInputDialog(null, """
                        Menú Asesor:
                        1 - Ver todas PQRS
                        2 - Ver PQRS de un cliente
                        3 - Solucionar PQRS
                        4 - Registrar plan hogar a cliente
                        5 - Registrar plan movil a cliente
                        6 - Cerrar sesion
                        """, "Asesor", JOptionPane.PLAIN_MESSAGE));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un numero valido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                switch (opcionMenuAsesor) {
                    case 1:
                        opcionVerTodasPQRS();
                        break;
                    case 2:
                        opcionVerPQRSCliente();
                        break;
                    case 3:
                        opcionSolucionarPQRS();
                        break;
                    case 4:
                        opcionRegistrarPlanHogar();
                        break;
                    case 5:
                        opcionRegistrarPlanMovil();
                        break;
                    case 6:
                        JOptionPane.showMessageDialog(null, "Saliendo...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opcion no valida.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Excepcion",
                        JOptionPane.ERROR_MESSAGE);
            }
        } while (opcionMenuAsesor != 6);
    }
    /**
     * Displays the client menu with available operations.
     * Includes options to register/view/modify/delete PQRS, view available plans,
     * view contracted plans, request customized plans, and logout.
     * Continues running until the client selects the logout option.
     */
    public void menuCliente() {
        int opcionMenuCliente = 0;
        do {
            try {
                opcionMenuCliente = Integer.parseInt(JOptionPane.showInputDialog(null, """
                        Menú Cliente:
                        1 - Registrar PQRS
                        2 - Ver mis PQRS
                        3 - Modificar PQRS
                        4 - Eliminar PQRS
                        5 - Ver planes disponibles
                        6 - Ver mis planes contratados
                        7 - Solicitar plan personalizado
                        8 - Cerrar sesion
                        """, "Cliente", JOptionPane.PLAIN_MESSAGE));

                switch (opcionMenuCliente) {
                    case 1:
                        opcionRegistrarPQRS();
                        break;
                    case 2:
                        opcionVerPQRSCliente();
                        break;
                    case 3:
                        opcionModificarPQRS();
                        break;
                    case 4:
                        opcionEliminarPQRSCliente();
                        break;
                    case 5:
                        mostrarPlanesDisponibles();
                        break;
                    case 6:
                        verMisPlanes();
                        break;
                    case 7:
                        solicitarPlanPersonalizado();
                        break;
                    case 8:
                        JOptionPane.showMessageDialog(null, "Saliendo...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opcion no valida.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (opcionMenuCliente != 8);
    }
    /**
     * Handles the login process.
     * Prompts for user ID and password, validates credentials through the controller,
     * and redirects to the appropriate menu based on user type (Admin, Advisor, or Client).
     * Sets the usuarioLogueado field upon successful login.
     */
    public void opcionLogin() {
        try {
            String id = JOptionPane.showInputDialog("Cedula:");
            String contrasena = JOptionPane.showInputDialog("Contraseña:");
            String tipoUsuario = controlador.login(id, contrasena); 
            usuarioLogueado = controlador.obtenerUsuarioActual(id);

            if ("ADMIN".equals(tipoUsuario)) {
                JOptionPane.showMessageDialog(null, "Login exitoso. Bienvenido admin", "Login",
                        JOptionPane.INFORMATION_MESSAGE);
                menuAdmin();
            } else if ("ASESOR".equals(tipoUsuario)) {
                JOptionPane.showMessageDialog(null, "Login exitoso. Bienvenido " + usuarioLogueado.getNombre(), "Login",
                        JOptionPane.INFORMATION_MESSAGE);
                menuAsesor();
            } else if ("CLIENTE".equals(tipoUsuario)) {
                JOptionPane.showMessageDialog(null, "Login exitoso. Bienvenido " + usuarioLogueado.getNombre(), "Login",
                        JOptionPane.INFORMATION_MESSAGE);
                menuCliente();
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ContrasenaVacia cv) {
            JOptionPane.showMessageDialog(null, cv.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles client registration.
     * Prompts for all required client information (ID, name, birth date, location, phone, password)
     * and registers the client through the controller.
     * Displays success or error messages accordingly.
     */
    public void opcionRegistrarCliente() {

        try {
            String cedula = JOptionPane.showInputDialog("Cedula:");
            String nombre = JOptionPane.showInputDialog("Nombre:");
            String apellido = JOptionPane.showInputDialog("Apellido:");
            String fecha = JOptionPane.showInputDialog("Fecha de nacimiento (yyyy-MM-dd):");
            String pais = JOptionPane.showInputDialog("País:");
            String departamento = JOptionPane.showInputDialog("Departamento:");
            String ciudad = JOptionPane.showInputDialog("Ciudad:");
            String numeroCelular = JOptionPane.showInputDialog("Numero de celular:");
            String contrasena = JOptionPane.showInputDialog("Contraseña:");
            JOptionPane.showMessageDialog(null, controlador.registrarCliente(cedula, nombre, apellido, LocalDate.parse(fecha), pais,
                    departamento, ciudad, numeroCelular, contrasena), "Registrar Cliente",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (ClienteNocreado cn) {
            JOptionPane.showMessageDialog(null, cn.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles advisor registration (Admin only).
     * Prompts for all required advisor information (ID, name, birth date, location, password)
     * and registers the advisor through the controller.
     * Displays success or error messages accordingly.
     */
    public void opcionRegistrarAsesor() {

        try {
            String cedula = JOptionPane.showInputDialog("Cedula:");
            String nombre = JOptionPane.showInputDialog("Nombre:");
            String apellido = JOptionPane.showInputDialog("Apellido:");
            String fecha = JOptionPane.showInputDialog("Fecha de nacimiento (yyyy-MM-dd):");
            String pais = JOptionPane.showInputDialog("Pais:");
            String departamento = JOptionPane.showInputDialog("Departamento:");
            String ciudad = JOptionPane.showInputDialog("Ciudad:");
            String contrasena = JOptionPane.showInputDialog("Contraseña:");
            JOptionPane.showMessageDialog(null, controlador.registrarAsesor(cedula, nombre, apellido, LocalDate.parse(fecha), pais,
                    departamento, ciudad, contrasena), "Registrar Asesor", JOptionPane.INFORMATION_MESSAGE);
        } catch (AsesorNocreado an) {
            JOptionPane.showMessageDialog(null, an.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles advisor deletion (Admin only).
     * Prompts for the advisor's ID and attempts to delete them through the controller.
     * An advisor can only be deleted if they have no managed requests.
     * Displays success or error messages accordingly.
     */
    public void eliminarAsesor() {
        try {
            String cedula = JOptionPane.showInputDialog("Ingrese la cedula que desea eliminar:");
            JOptionPane.showMessageDialog(null, controlador.eliminarAsesor(cedula));

        } catch (AsesorNocreado as) {
            JOptionPane.showMessageDialog(null, "Error al registrar asesor: " + as.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Displays all registered advisors in the system (Admin only).
     * Shows the list of advisors obtained from the controller.
     */
    public void verAsesores() {
        JOptionPane.showMessageDialog(null, controlador.obtenerAsesores());
    }
    /**
     * Displays all PQRS managed by a specific advisor (Admin only).
     * Prompts for the advisor's ID and shows their managed requests.
     * Displays error message if advisor is not found.
     */
    public void verPQRSAtendidasAsesor() {
        try {
            String idAsesor = JOptionPane.showInputDialog("Cedula del asesor:");
            JOptionPane.showMessageDialog(null, controlador.obtenerTodasPQRSAsesor(idAsesor));
        } catch (UsuarioNoencontrado e) {
            JOptionPane.showMessageDialog(null, "Error al registrar asesor: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles mobile plan registration for a client (Advisor only).
     * Prompts for client ID and plan details (minutes, data, price, discount)
     * and registers the plan through the controller.
     * Validates that all numeric inputs are correct and greater than zero.
     */
    public void opcionRegistrarPlanMovil() {
        try {
            String id = JOptionPane.showInputDialog("Cedula:");
            int minutos = Integer.parseInt(JOptionPane.showInputDialog("Minutos:"));
            double gigas = Double.parseDouble(JOptionPane.showInputDialog("Gigas:"));
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor del servicio:"));
            double descuento = Double.parseDouble(JOptionPane.showInputDialog("Descuento:"));
            JOptionPane.showMessageDialog(null, controlador.registrarPlanMovil(id, minutos, gigas, valor, descuento),
                    "Registrar Plan Movil", JOptionPane.INFORMATION_MESSAGE);
        } catch (MinutosGigasnegativos mg) {
            JOptionPane.showMessageDialog(null, mg.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (UsuarioNoencontrado un) {
            JOptionPane.showMessageDialog(null, un.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Debe ingresar valores numericos validos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles home plan registration for a client (Advisor only).
     * Prompts for client ID and plan details (TV type, internet speed, price, discount)
     * and registers the plan through the controller.
     * Validates TV type (digital/analoga) and that numeric inputs are correct and greater than zero.
     */
    public void opcionRegistrarPlanHogar() {
        try {
            String id = JOptionPane.showInputDialog("ID del cliente:");
            String tipoTV = JOptionPane.showInputDialog("Tipo TV (digital/análoga):");
            int megas = Integer.parseInt(JOptionPane.showInputDialog("Megas de internet:"));
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor del servicio:"));
            double descuento = Double.parseDouble(JOptionPane.showInputDialog("Descuento:"));
            JOptionPane.showMessageDialog(null, controlador.registrarPlanHogar(id, tipoTV, megas, valor, descuento),
                    "Registrar Plan Hogar", JOptionPane.INFORMATION_MESSAGE);
        } catch (MegasNegativas mn) {
            JOptionPane.showMessageDialog(null, mn.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (TipoTVincorrectos tt) {
            JOptionPane.showMessageDialog(null, tt.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (UsuarioNoencontrado un) {
            JOptionPane.showMessageDialog(null, un.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Debe ingresar valores numericos validos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles PQRS registration for the logged-in client.
     * Prompts for PQRS type (1=Petition, 2=Complaint, 3=Claim, 4=Suggestion),
     * description, and associated plan ID.
     * Creates the PQRS through the controller using the current logged-in client's ID.
     */
    public void opcionRegistrarPQRS() {
        try {
            String descripcion = JOptionPane.showInputDialog("Descripción de la PQRS:");
            int tipo = Integer.parseInt(JOptionPane.showInputDialog("""
                    Tipo de PQRS
                    1-Peticion
                    2-Queja
                    3-Reclamo
                    4-Sugerencia
                    """));
            int idPlan = Integer.parseInt(JOptionPane.showInputDialog("ID del plan asociado:"));

            JOptionPane.showMessageDialog(null,
                    controlador.registrarPQRS(usuarioLogueado.getCedula(), tipo, descripcion, idPlan));
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Formato de número inválido", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (UsuarioNoencontrado ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NoExistePlan np) {
            JOptionPane.showMessageDialog(null, np.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Displays all PQRS registered in the system (Advisor only).
     * Shows detailed information for each PQRS including type-specific fields.
     * Displays error message if no PQRS are found.
     */
    public void opcionVerTodasPQRS() {
        try {
            JOptionPane.showMessageDialog(null, controlador.obtenerTodosPQRS(), "Todas las PQRS",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (PQRSNoEncontrada ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Displays all PQRS for a specific client.
     * For advisors: prompts for client ID
     * For clients: automatically uses the logged-in client's ID
     * Shows all PQRS with their details or error message if client not found.
     */
    public void opcionVerPQRSCliente() {
        try {
            String id = JOptionPane.showInputDialog("ID del cliente:");
            JOptionPane.showMessageDialog(null, controlador.obtenerPQRSCliente(id), "PQRS del Cliente",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (UsuarioNoencontrado un) {
            JOptionPane.showMessageDialog(null, un.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles PQRS resolution by an advisor.
     * Prompts for advisor ID, then allows solving multiple PQRS in a loop (until -1 is entered).
     * For each PQRS, requests: PQRS ID, solution description, and importance/dissatisfaction level.
     * Different actions are taken based on PQRS type:
     * - Petition: marks as resolved with solution concept
     * - Complaint: sets dissatisfaction level
     * - Claim: adds compensation resource
     * - Suggestion: sets importance level
     */
    public void opcionSolucionarPQRS() {
        try {
            String idAsesor = JOptionPane.showInputDialog("ID del asesor:");
            int idPQRS;
            do {
                idPQRS = Integer
                        .parseInt(JOptionPane.showInputDialog("ID de la PQRS a solucionar (-1 para terminar):"));
                if (idPQRS >= 0) {
                    String solucion = JOptionPane.showInputDialog("Descripción de la solucion:");
                    int nivelesAux = Integer.parseInt(JOptionPane.showInputDialog(
                            "Ingrese el nivel de importancia (Si es necesario, si no coloque 0):"));
                    JOptionPane.showMessageDialog(null,
                            controlador.solucionarPQRS(idAsesor, idPQRS, solucion, nivelesAux),
                            "Solucionar PQRS",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } while (idPQRS >= 0);
        } catch (PQRSNoEncontrada pqrsn) {
            JOptionPane.showMessageDialog(null, pqrsn.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (UsuarioNoencontrado un) {
            JOptionPane.showMessageDialog(null, un.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Valores numericos invalidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles PQRS modification for a client.
     * Prompts for user ID, PQRS ID, and new description.
     * Only the client who owns the PQRS can modify it.
     * Updates the PQRS description through the controller.
     */
    public void opcionModificarPQRS() {
        try {
            String idUsuario = JOptionPane.showInputDialog("ID del usuario:");
            int idPQRS = Integer.parseInt(JOptionPane.showInputDialog("ID de la PQRS a modificar:"));
            String nuevaDesc = JOptionPane.showInputDialog("Nueva descripcion:");

            JOptionPane.showMessageDialog(null,
                    controlador.modificarPQRS(idUsuario, idPQRS, nuevaDesc),
                    "Modificar PQRS",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (PQRSNoEncontrada pn) {
            JOptionPane.showMessageDialog(null, pn.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (UsuarioNoencontrado un) {
            JOptionPane.showMessageDialog(null, un.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Valores numericos invalidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Handles PQRS deletion for the logged-in client.
     * Prompts for the PQRS ID to be deleted.
     * Only the client who owns the PQRS can delete it.
     * Removes the PQRS from both the client's list and the system's global list.
     */
    public void opcionEliminarPQRSCliente() {
        try {
            int idPQRS = Integer.parseInt(JOptionPane.showInputDialog("ID de la PQRS a eliminar:"));
            JOptionPane.showMessageDialog(null,
                    controlador.eliminarPQRSCliente(idPQRS, usuarioLogueado.getCedula()));
        } catch (PQRSNoEncontrada ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID inválido", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Displays the administrator menu with available operations.
     * Includes options to manage advisors, view PQRS, create plan templates, and logout.
     * Continues running until the admin selects the logout option.
     */
    public void menuAdmin() {
        int opcionmenuAdmin = 0;
        do {
            try {
                opcionmenuAdmin = Integer.parseInt(JOptionPane.showInputDialog(null, """
                        Menú Administrador:
                        1 - Registrar Asesor
                        2 - Eliminar Asesor
                        3 - Ver Asesores
                        4 - Ver PQRS atentidas por asesor
                        5 - Crear Plan Móvil
                        6 - Crear Plan Hogar
                        7 - Cerrar sesión
                        """, "Administrador", JOptionPane.PLAIN_MESSAGE));

                switch (opcionmenuAdmin) {
                    case 1:
                        opcionRegistrarAsesor();
                        break;
                    case 2:
                        eliminarAsesor();
                        break;
                    case 3:
                        verAsesores();
                        break;
                    case 4:
                        verPQRSAtendidasAsesor();
                        break;
                    case 5:
                        crearPlanMovilAdmin();
                        break;
                    case 6:
                        crearPlanHogarAdmin();
                        break;
                    case 7:
                        JOptionPane.showMessageDialog(null, "Saliendo...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opcion no valida.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Excepcion",
                        JOptionPane.ERROR_MESSAGE);
            }
        } while (opcionmenuAdmin != 7);
    }
    /**
     * Creates a mobile plan template in the available plans catalog (Admin only).
     * Prompts for plan details: minutes, data, price, and discount.
     * This plan becomes available for clients to contract.
     * Validates that minutes and data are greater than zero.
     */
    public void crearPlanMovilAdmin() {
        try {
            int minutos = Integer.parseInt(JOptionPane.showInputDialog("Minutos:"));
            double gigas = Double.parseDouble(JOptionPane.showInputDialog("Gigas:"));
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor del servicio:"));
            double descuento = Double.parseDouble(JOptionPane.showInputDialog("Descuento:"));

            JOptionPane.showMessageDialog(null, controlador.crearPlanMovilAdmin(minutos, gigas, valor, descuento));
        } catch (MinutosGigasnegativos ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Creates a home plan template in the available plans catalog (Admin only).
     * Prompts for plan details: TV type, internet speed, price, and discount.
     * This plan becomes available for clients to contract.
     * Validates TV type and that internet megas are greater than zero.
     */
    public void crearPlanHogarAdmin() {
        try {
            String tipoTV = JOptionPane.showInputDialog("Tipo TV (digital/análoga):");
            int megas = Integer.parseInt(JOptionPane.showInputDialog("Megas de internet:"));
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor del servicio:"));
            double descuento = Double.parseDouble(JOptionPane.showInputDialog("Descuento:"));

            JOptionPane.showMessageDialog(null, controlador.crearPlanHogarAdmin(tipoTV, megas, valor, descuento));
        } catch (MegasNegativas ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (TipoTVincorrectos ti) {
            JOptionPane.showMessageDialog(null, ti.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Displays all available plans (mobile and home) for the logged-in client to contract.
     * Shows plan details and monthly payment for each plan.
     * Allows the client to select a plan by entering its ID (or 0 to cancel).
     * Upon selection, creates a copy of the plan and adds it to the client's contracted plans,
     * then displays the monthly payment amount.
     */
    public void mostrarPlanesDisponibles() {
        try {

            int opcionID = Integer.parseInt(JOptionPane.showInputDialog(
                    controlador.obtenerPlanesHogarDisponibles() + "\n" + controlador.obtenerPlanesMovilesDisponibles() +
                            "\nIngrese el ID del plan que desea contratar (0 para cancelar):"));

            if (opcionID != 0) {
                String resultado = controlador.seleccionarPlanDisponible(usuarioLogueado.getCedula(), opcionID);
                JOptionPane.showMessageDialog(null, resultado + "\n" +
                        "El pago mensual será: $" + controlador.calcularPagoMensualPlan(opcionID));
            }
        } catch (UsuarioNoencontrado un) {
            JOptionPane.showMessageDialog(null, un.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Displays all plans contracted by the logged-in client.
     * Shows detailed information for each plan including monthly payment.
     * Also displays the total monthly payment for all plans combined.
     */
    public void verMisPlanes() {
        try {
            String idCliente = usuarioLogueado.getCedula();
            JOptionPane.showMessageDialog(null, controlador.obtenerPlanesCliente(idCliente),
                    "Mis Planes", JOptionPane.INFORMATION_MESSAGE);

        } catch (UsuarioNoencontrado un) {
            JOptionPane.showMessageDialog(null, un.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
    * Allows the logged-in client to request a customized plan.
    * Prompts for a description of the desired plan.
    * Creates a petition-type PQRS with the plan description for advisor review.
    * Displays success.
    */
    public void solicitarPlanPersonalizado() {
        try {
            String descripcion = JOptionPane.showInputDialog("Describa el plan que desea:");
            if (descripcion != null && !descripcion.isEmpty()) {
                controlador.solicitarPlanPersonalizado(usuarioLogueado.getCedula(), descripcion);
                JOptionPane.showMessageDialog(null, "Solicitud enviada exitosamente");
            }
        } catch (UsuarioNoencontrado ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
