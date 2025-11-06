package edu.uptc.Gui;

import edu.uptc.Controlador.Controlador;
import edu.uptc.Entidades.Usuario;
import edu.uptc.exepciones.*;

import javax.swing.JOptionPane;

import java.time.LocalDate;

public class Gui {
    private Controlador controlador;
    private Usuario usuarioLogueado;

    public Gui(Controlador controlador) {
        this.controlador = controlador;
    }

    public void iniciar() {
        int opcionPrincipal = 0;
        do {
            try {
                opcionPrincipal = Integer.parseInt(JOptionPane.showInputDialog(null, """
                         Seleccione una opcion:
                        1 - Login
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

    private void menuAsesor() {
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

    private void menuCliente() {
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

    private void opcionLogin() {
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

    private void opcionRegistrarCliente() {

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

    private void opcionRegistrarAsesor() {

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

    private void eliminarAsesor() {
        try {
            String cedula = JOptionPane.showInputDialog("Ingrese la cedula que desea eliminar:");
            JOptionPane.showMessageDialog(null, controlador.eliminarAsesor(cedula));

        } catch (AsesorNocreado as) {
            JOptionPane.showMessageDialog(null, "Error al registrar asesor: " + as.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verAsesores() {
        JOptionPane.showMessageDialog(null, controlador.obtenerAsesores());
    }

    private void verPQRSAtendidasAsesor() {
        try {
            String idAsesor = JOptionPane.showInputDialog("Cedula del asesor:");
            JOptionPane.showMessageDialog(null, controlador.obtenerTodasPQRSAsesor(idAsesor));
        } catch (UsuarioNoencontrado e) {
            JOptionPane.showMessageDialog(null, "Error al registrar asesor: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opcionRegistrarPlanMovil() {
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

    private void opcionRegistrarPlanHogar() {
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

    private void opcionRegistrarPQRS() {
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

    private void opcionVerTodasPQRS() {
        try {
            JOptionPane.showMessageDialog(null, controlador.obtenerTodosPQRS(), "Todas las PQRS",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (PQRSNoEncontrada ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opcionVerPQRSCliente() {
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

    private void opcionSolucionarPQRS() {
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

    private void opcionModificarPQRS() {
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

    private void opcionEliminarPQRSCliente() {
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

    private void menuAdmin() {
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

    private void crearPlanMovilAdmin() {
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

    private void crearPlanHogarAdmin() {
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

    private void mostrarPlanesDisponibles() {
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

    private void verMisPlanes() {
        try {
            String idCliente = usuarioLogueado.getCedula();
            JOptionPane.showMessageDialog(null, controlador.obtenerPlanesCliente(idCliente),
                    "Mis Planes", JOptionPane.INFORMATION_MESSAGE);

        } catch (UsuarioNoencontrado un) {
            JOptionPane.showMessageDialog(null, un.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void solicitarPlanPersonalizado() {
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
