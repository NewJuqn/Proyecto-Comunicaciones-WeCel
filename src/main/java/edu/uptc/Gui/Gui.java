package edu.uptc.Gui;

import edu.uptc.Controlador.Controlador;
import edu.uptc.Entidades.Plan;
import edu.uptc.Entidades.Usuario;
import edu.uptc.Entidades.Cliente;
import edu.uptc.Entidades.Asesor;
import edu.uptc.Entidades.PQRS;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.ArrayList;

public class Gui {
    private Controlador controlador;
    private Usuario usuarioLogueado;
    private Usuario admin;

    public Gui(Controlador controlador) {
        this.controlador = controlador;
        this.admin = new Usuario("1","admin", "" , LocalDate.now() , "Colombia", "Boyaca", "Sogamoso", "123");
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
                        if (usuarioLogueado instanceof Asesor) {
                            menuAsesor();
                        } else if (usuarioLogueado instanceof Cliente) {
                            menuCliente();
                        } else if (usuarioLogueado==admin){
                            menuAdmin();
                        }
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
        } while (opcionMenuAsesor != 7);
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
                        5 - Cerrar sesion
                        """, "Cliente", JOptionPane.PLAIN_MESSAGE));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un numero valido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {
                switch (opcionMenuCliente) {
                    case 1:
                        opcionRegistrarPQRS();
                        break;
                    case 2:
                        opcionVerMisPQRS();
                        break;
                    case 3:
                        opcionModificarPQRS();
                        break;
                    case 4:
                        opcionEliminarPQRSCliente();
                        break;
                    case 5:
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
        } while (opcionMenuCliente != 5);
    }

    private void opcionLogin() {
        try {
            String id = JOptionPane.showInputDialog("Cedula:");
            String contrasena = JOptionPane.showInputDialog("Contraseña:");
            Usuario usuario = controlador.login(id, contrasena);
            usuarioLogueado = usuario;
            if (usuarioLogueado==admin) {
                usuarioLogueado = admin;
            }
            if (usuario != null) {
                JOptionPane.showMessageDialog(null, "Login exitoso. Bienvenido " + usuario.getNombre(), "Login",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en login: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
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
            String contrasena = JOptionPane.showInputDialog("Contraseña:");
            LocalDate fechaN = LocalDate.parse(fecha);
            JOptionPane.showMessageDialog(null, controlador.registrarCliente(cedula,nombre, apellido, fechaN, pais,
                    departamento, ciudad, contrasena), "Registrar Cliente", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar cliente: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opcionRegistrarAsesor() { //En menu de admin

        try {
            String cedula = JOptionPane.showInputDialog("Cedula:");
            String nombre = JOptionPane.showInputDialog("Nombre:");
            String apellido = JOptionPane.showInputDialog("Apellido:");
            String fecha = JOptionPane.showInputDialog("Fecha de nacimiento (yyyy-MM-dd):");
            String pais = JOptionPane.showInputDialog("Pais:");
            String departamento = JOptionPane.showInputDialog("Departamento:");
            String ciudad = JOptionPane.showInputDialog("Ciudad:");
            String contrasena = JOptionPane.showInputDialog("Contraseña:");
            LocalDate fechaN = LocalDate.parse(fecha);
            JOptionPane.showMessageDialog(null, controlador.registrarAsesor(cedula, nombre, apellido, fechaN, pais,
                    departamento, ciudad, contrasena), "Registrar Asesor", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar asesor: " + ex.getMessage(), "Error",
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
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Debe ingresar valores numericos validos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Debe ingresar valores numericos validos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opcionRegistrarPQRS() {
        try {
            String id = JOptionPane.showInputDialog("Cedula del cliente:");
            int tipo = Integer.parseInt(JOptionPane.showInputDialog("""
                    Tipo PQRS
                    1-Peticion
                    2-Queja
                    3-Reclamo
                    4-Sugerencia
                    """));
            String descripcion = JOptionPane.showInputDialog("Descripcion:");
            int planId = Integer.parseInt(JOptionPane.showInputDialog("ID del plan asociado:"));
            Plan plan = controlador.obtenerPlanID(planId, id);
            JOptionPane.showMessageDialog(null, controlador.registrarPQRS(id, tipo, descripcion, plan),
                    "Registrar PQRS", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Debe ingresar valores numericos validos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opcionVerTodasPQRS() {
        try {
            JOptionPane.showMessageDialog(null, controlador.obtenerTodosPQRS(), "Todas las PQRS",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opcionVerPQRSCliente() {
        try {
            String id = JOptionPane.showInputDialog("ID del cliente:");
            ArrayList<PQRS> lista = controlador.obtenerPQRSCliente(id);
            StringBuilder sb = new StringBuilder();
            for (PQRS p : lista) {
                sb.append(p.toString()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "PQRS del Cliente", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opcionSolucionarPQRS() {
        try {
            String idAsesor =JOptionPane.showInputDialog("ID del asesor:");
            int idPQRS = 0;
            do {
                idPQRS = Integer.parseInt(JOptionPane.showInputDialog("ID de la PQRS a solucionar (-1 para terminar):"));
                PQRS pqrs = controlador.obtenerPQRSID(idPQRS);
                JOptionPane.showMessageDialog(null, pqrs.toString());
                String solucion = JOptionPane.showInputDialog("Descripción de la solucion:");
                int nivelesAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nivel de importancia (Si es necesario, si no coloque 0):"));
                JOptionPane.showMessageDialog(null, controlador.solucionarPQRS(idAsesor, pqrs, solucion, nivelesAux), "Solucionar PQRS", JOptionPane.INFORMATION_MESSAGE);
            } while (idPQRS >= 0);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Valores numericos invalidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opcionModificarPQRS() {
        try {
            String idUsuario = JOptionPane.showInputDialog("ID del usuario:");
            int idPQRS = Integer.parseInt(JOptionPane.showInputDialog("ID de la PQRS a modificar:"));
            String nuevaDesc = JOptionPane.showInputDialog("Nueva descripcion:");
            PQRS pqrs = controlador.obtenerPQRSID(idPQRS);
            JOptionPane.showMessageDialog(null, controlador.modificarPQRS(idUsuario, pqrs, nuevaDesc), "Modificar PQRS", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Valores numericos invalidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opcionVerMisPQRS() {
        try {
            String id;
            if (usuarioLogueado instanceof Cliente) {
                id = ((Cliente) usuarioLogueado).getCedula();
            } else {
                id = JOptionPane.showInputDialog("ID del cliente:");
            }
            ArrayList<PQRS> lista = controlador.obtenerPQRSCliente(id);
            StringBuilder sb = new StringBuilder();
            for (PQRS p : lista)
                sb.append(p.toString()).append("\n\n");
            JOptionPane.showMessageDialog(null, sb.toString(), "Mis PQRS", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void menuAdmin(){
        //menu de admin
    }

    private void opcionEliminarPQRSCliente(){
        try {
            String idUsuario = JOptionPane.showInputDialog("ID del usuario:");
            int idPQRS = Integer.parseInt(JOptionPane.showInputDialog("ID de la PQRS a eliminar:"));
            JOptionPane.showMessageDialog(null, controlador.eliminarPQRSCliente(idPQRS, idUsuario));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
