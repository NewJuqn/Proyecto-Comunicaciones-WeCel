package edu.uptc.Servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.uptc.Entidades.*;
import edu.uptc.exepciones.*;

public class Servicios {
    private TreeMap<String, Usuario> usuarios;
    private TreeSet<PQRS> PQRSlista;
    private ArrayList<PlanMovil> planesMovilesDisponibles;
    private ArrayList<PlanHogar> planesHogarDisponibles;

    public Servicios() {
        this.usuarios = new TreeMap<>();
        this.PQRSlista = new TreeSet<>(Comparator.comparing(PQRS::getFechaRegistro).thenComparingInt(PQRS::getIdPQRS));
        this.planesMovilesDisponibles = new ArrayList<>();
        this.planesHogarDisponibles = new ArrayList<>();
        usuarios.put("0", new Usuario("0", "admin", "", LocalDate.now(), "Colombia", "Boyaca", "Sogamoso", "012"));
    }

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

    public ArrayList<Asesor> obtenerAsesores() {
        ArrayList<Asesor> listaAsesores = new ArrayList<>();

        for (Usuario usuario : usuarios.values()) {
            if (usuario instanceof Asesor) {
                listaAsesores.add((Asesor) usuario);
            }
        }

        return listaAsesores;
    }

    public Usuario buscarPorIdUsuarios(String id) {
        return usuarios.get(id);
    }

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

    public String crearPlanMovilAdmin(int minutos, double gigas, double valorServicio, double descuento)
            throws MinutosGigasnegativos {
        if (minutos <= 0 || gigas <= 0) {
            throw new MinutosGigasnegativos("Los minutos y gigas deben ser mayores a 0");
        }
        PlanMovil nuevoPlan = new PlanMovil(LocalDate.now(), valorServicio, descuento, minutos, gigas);
        planesMovilesDisponibles.add(nuevoPlan);
        return "Plan móvil creado exitosamente";
    }

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

    public String solicitarPlanPersonalizado(String idCliente, String descripcion)
            throws UsuarioNoencontrado {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("Cliente no encontrado");
        }
        Cliente cliente = (Cliente) usuario;
        Plan planTemporal = new PlanMovil(LocalDate.now(), 0, 0, 0, 0);
        Peticion solicitud = new Peticion("Solicitud de plan personalizado: " + descripcion,planTemporal);
        cliente.getPQRSs().add(solicitud);
        PQRSlista.add(solicitud);

        return "Solicitud de plan personalizado enviada exitosamente";
    }

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

    public Usuario obtenerUsuarioActual(String idCliente) {
        return usuarios.get(idCliente);
    }
}
