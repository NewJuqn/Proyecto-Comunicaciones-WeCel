package edu.uptc.Servicios;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import edu.uptc.Entidades.Asesor;
import edu.uptc.Entidades.Cliente;
import edu.uptc.Entidades.PQRS;
import edu.uptc.exepciones.AsesorNocreado;
import edu.uptc.exepciones.ClienteNocreado;
import edu.uptc.exepciones.ContrasenaVacia;
import edu.uptc.exepciones.MegasNegativas;
import edu.uptc.exepciones.MinutosGigasnegativos;
import edu.uptc.exepciones.PQRSNoEncontrada;
import edu.uptc.exepciones.TipoTVincorrectos;
import edu.uptc.exepciones.UsuarioNoencontrado;

public class ServiciosTest {
    private Servicios servicios;
    @BeforeEach
    void setUp(){
        servicios = new Servicios();
    }
    @Test
    void testBuscarPorIdUsuarios() throws ClienteNocreado {
        LocalDate fecha = LocalDate.of(1995, 5, 15);
        servicios.registrarCliente("123", "juan", "garcia", fecha, "colombia", "boyaca", "sogamoso", "3001234567", "pass123");
        assertNotNull(servicios.buscarPorIdUsuarios("123"), "debe encontrar el usuario registrado");
        assertEquals(null, servicios.buscarPorIdUsuarios("234"), "No debe encontrar usuario inexistente");
    }

    @Test
    void testCalcularPagoMensualPlan() throws ClienteNocreado, UsuarioNoencontrado, MinutosGigasnegativos {
        LocalDate fecha = LocalDate.of(1995, 5, 15);
        servicios.registrarCliente("123", "pedro", "martinez", fecha, "colombia", "boyaca", "duitama", "3009876543", "pass456");
        servicios.registrarPlanMovil("123", 100, 5.0, 50000, 10);
        String resultado = servicios.obtenerPlanesCliente("123");
        assertTrue(resultado.contains("Pago"), "debe contener informacion de pago");
    }

    @ParameterizedTest
    @MethodSource("datosValidosPlanHogar")
    void testCrearPlanHogarAdmin(String cedula, String nombre, String apellido, LocalDate fecha, String tipoTV, int megas, double valor, double descuento, boolean debeExitoso) throws ClienteNocreado, UsuarioNoencontrado, MegasNegativas, TipoTVincorrectos {
        servicios.registrarCliente(cedula, nombre, apellido, fecha, "colombia", "cundinamarca", "bogota", "3105551234", "123");
        if (debeExitoso) {
            String resultado = servicios.registrarPlanHogar(cedula, tipoTV, megas, valor, descuento);
            assertTrue(resultado.contains("exitosamente"), "Debe registrar plan hogar exitosamente");
        } else {
            assertThrows(TipoTVincorrectos.class, () -> servicios.registrarPlanHogar(cedula, tipoTV, megas, valor, descuento), "Debe lanzar excepción para tipo TV inválido");
        }
    }

    static Stream<Arguments> datosValidosPlanHogar() {
        LocalDate fecha = LocalDate.of(1988, 3, 20);
        return Stream.of(
                Arguments.of("123", "ana", "lopez", fecha, "digital", 100, 80000, 15, true),
                Arguments.of("124", "carlos", "perez", fecha, "analoga", 50, 70000, 10, true),
                Arguments.of("125", "maria", "garcia", fecha, "DIGITAL", 150, 90000, 20, true),
                Arguments.of("126", "juan", "rodriguez", fecha, "invalida", 100, 80000, 15, false)
        );
    }

    @ParameterizedTest
    @MethodSource("datosValidosPlanMovil")
    void testCrearPlanMovilAdmin(String cedula, String nombre, String apellido, LocalDate fecha, int minutos, double gigas, double valor, double descuento, boolean debeExitoso) throws ClienteNocreado, UsuarioNoencontrado, MinutosGigasnegativos {
        servicios.registrarCliente(cedula, nombre, apellido, fecha, "colombia", "atlantico", "barranquilla", "3115551234", "luispass");
        if (debeExitoso) {
            String resultado = servicios.registrarPlanMovil(cedula, minutos, gigas, valor, descuento);
            assertTrue(resultado.contains("exitosamente"), "Debe registrar plan móvil exitosamente");
        } else {
            assertThrows(MinutosGigasnegativos.class, () -> servicios.registrarPlanMovil(cedula, minutos, gigas, valor, descuento), "Debe lanzar excepción para minutos o gigas negativos");
        }
    }

    static Stream<Arguments> datosValidosPlanMovil() {
        LocalDate fecha = LocalDate.of(1992, 7, 10);
        return Stream.of(
                Arguments.of("103", "luis", "rodriguez", fecha, 150, 10.0, 60000, 20, true),
                Arguments.of("1030", "ana", "lopez", fecha, 200, 15.0, 65000, 18, true),
                Arguments.of("1031", "pedro", "martinez", fecha, 100, 5.0, 50000, 10, true),
                Arguments.of("1032", "maria", "garcia", fecha, -50, 10.0, 60000, 20, false),
                Arguments.of("1033", "carlos", "perez", fecha, 100, -5.0, 60000, 20, false)
        );
    }

    @ParameterizedTest
    @MethodSource("cedulasNoExistentes")
    void testEliminarAsesor(String cedula) {
        assertThrows(AsesorNocreado.class, () -> servicios.eliminarAsesor(cedula));
    }

    static Stream<Arguments> cedulasNoExistentes() {
        return Stream.of(
                Arguments.of("999"),
                Arguments.of("888"),
                Arguments.of("777")
        );
    }

    @Test
    void testEliminarPQRSCliente() {
        try {
            
            LocalDate fecha = LocalDate.of(1997, 9, 3);
            servicios.registrarCliente("300", "andres", "lopez", fecha, "colombia", "cundinamarca", "bogota", "3100000000", "andres123");
            servicios.registrarPlanMovil("300", 80, 3.0, 40000, 5);
            Cliente cliente = (Cliente) servicios.buscarPorIdUsuarios("300");
            int idPlan = cliente.getPlanes().get(0).getIdPlan();
            servicios.registrarPQRS("300", 1, "necesito ayuda", idPlan);
            PQRS pqrs = cliente.getPQRSs().get(0);
            int idPQRS = pqrs.getIdPQRS();            
            String resultado = servicios.eliminarPQRSCliente(idPQRS, "300");
            assertEquals("PQRS eliminada exitosamente", resultado);            
            assertThrows(PQRSNoEncontrada.class, () -> servicios.eliminarPQRSCliente(idPQRS, "300"));
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLogin() throws AsesorNocreado, ContrasenaVacia {
        servicios.registrarAsesor("123", "carlos", "sanchez", LocalDate.of(1985, 4, 8), "colombia", "boyaca", "tunja", "asesor123");
        String resultado = servicios.login("123", "asesor123");
        assertEquals("ASESOR", resultado, "debe retornar tipo ASESOR para credenciales correctas");
    }

    @Test
    void testLoginInvalido() {
        assertThrows(ContrasenaVacia.class, () -> servicios.login("999", "999"), "Debe lanzar excepción para usuario inexistente");
    }

    @Test
    void testModificarPQRS() {
        try {
            LocalDate fecha = LocalDate.of(1990, 1, 1);
            servicios.registrarCliente("400", "Luis", "Morales", fecha, "Colombia", "Boyaca", "Tunja", "300111222", "pass");
            servicios.registrarPlanMovil("400", 50, 2.0, 30000, 5);
            Cliente cliente = (Cliente) servicios.buscarPorIdUsuarios("400");
            int idPlan = cliente.getPlanes().get(0).getIdPlan();
            servicios.registrarPQRS("400", 1, "Descripcion inicial", idPlan);
            PQRS pqrs = cliente.getPQRSs().get(0);
            int idPQRS = pqrs.getIdPQRS();
            String resultado = servicios.modificarPQRS("400", idPQRS, "Descripcion nueva");
            assertEquals("PQRS modificada exitosamente", resultado);
            assertEquals("Descripcion nueva", cliente.getPQRSs().get(0).getDescripcion());
            assertThrows(UsuarioNoencontrado.class, () -> servicios.modificarPQRS("999", idPQRS, "x"));
            assertThrows(PQRSNoEncontrada.class, () -> servicios.modificarPQRS("400", 99999, "x"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testObtenerAsesores() throws AsesorNocreado {
        LocalDate fecha = LocalDate.of(1990, 1, 1);
        servicios.registrarAsesor("501", "Carlos", "Pérez", fecha, "Colombia", "Boyaca", "Tunja", "pass123");
        servicios.registrarAsesor("502", "María", "López", fecha, "Colombia", "Cundinamarca", "Bogotá", "pass456");
        Asesor a1 = (Asesor) servicios.buscarPorIdUsuarios("501");
        Asesor a2 = (Asesor) servicios.buscarPorIdUsuarios("502");
        assertNotNull(a1, "El asesor 501 debe existir");
        assertNotNull(a2, "El asesor 502 debe existir");
        assertEquals("Carlos", a1.getNombre(), "Nombre del asesor 501 debe ser Carlos");
        assertEquals("Pérez", a1.getApellido(), "Apellido del asesor 501 debe ser Pérez");
        assertEquals("María", a2.getNombre(), "Nombre del asesor 502 debe ser María");
        assertEquals("López", a2.getApellido(), "Apellido del asesor 502 debe ser López");
    }

    @Test
    void testObtenerPQRSCliente() throws ClienteNocreado, UsuarioNoencontrado {
        LocalDate fecha = LocalDate.of(1990, 2, 14);
        servicios.registrarCliente("104", "Sofia", "Gómez", fecha, "Colombia", "Valle", "Cali", "3125551234", "sofia123");
        String resultado = servicios.obtenerPQRSCliente("104");
        assertNotNull(resultado, "Debe retornar lista de PQRS del cliente");
    }

    @Test
    void testObtenerPQRSClienteNoExistente() {
        assertThrows(UsuarioNoencontrado.class, () -> servicios.obtenerPQRSCliente("999"), "Debe lanzar excepción para cliente inexistente");
    }

    @Test
    void testObtenerPlanesCliente() throws ClienteNocreado, UsuarioNoencontrado, MinutosGigasnegativos {
        LocalDate fecha = LocalDate.of(1993, 8, 25);
        servicios.registrarCliente("105", "roberto", "fernandez", fecha, "colombia", "nariño", "Pasto", "3135551234", "robert123");
        servicios.registrarPlanMovil("105", 120, 8.0, 55000, 12);
        String resultado = servicios.obtenerPlanesCliente("105");
        assertTrue(resultado.contains("Pago"), "Debe contener información de planes y pagos");
    }

    @Test
    void testObtenerPlanesHogarDisponibles() {
        String resultado = servicios.obtenerPlanesHogarDisponibles();
        assertNotNull(resultado, "debe retornar lista de planes hogar disponibles");
    }

    @Test
    void testObtenerPlanesMovilesDisponibles() {
        String resultado = servicios.obtenerPlanesMovilesDisponibles();
        assertNotNull(resultado, "Debe retornar lista de planes móviles disponibles");
    }

    @Test
    void testObtenerTodasPQRSAsesor() throws AsesorNocreado, UsuarioNoencontrado {
        servicios.registrarAsesor("202", "Diana", "Álvarez", LocalDate.of(1989, 6, 12), "Colombia", "Boyaca", "Sogamoso", "diana123");
        String resultado = servicios.obtenerTodasPQRSAsesor("202");
        assertNotNull(resultado, "Debe retornar lista de PQRS del asesor");
    }

    @Test
    void testObtenerTodasPQRSAsesorNoExistente() {
        assertThrows(UsuarioNoencontrado.class, () -> servicios.obtenerTodasPQRSAsesor("999"), "Debe lanzar excepción para asesor inexistente");
    }

    @Test
    void testObtenerTodosPQRS() {
        assertThrows(PQRSNoEncontrada.class, () -> servicios.obtenerTodosPQRS(), "Debe lanzar excepción si no hay PQRS");
    }

    @Test
    void testObtenerUsuarioActual() throws ClienteNocreado {
        LocalDate fecha = LocalDate.of(1996, 11, 5);
        servicios.registrarCliente("106", "marta", "castro", fecha, "Colombia", "cauca", "popayan", "3145551234", "marta123");
        assertNotNull(servicios.buscarPorIdUsuarios("106"), "Debe obtener el usuario actual registrado");
    }

    @ParameterizedTest
    @MethodSource("datosInvalidos")
    void testRegistrarAsesor(String cedula, String nombre, String apellido, LocalDate fechaNacimiento, String pais,
            String departamento, String ciudad, String contrasena) {
        assertThrows(AsesorNocreado.class,
                () -> servicios.registrarAsesor(cedula, nombre, apellido, fechaNacimiento, pais, departamento, ciudad,
                        contrasena));
    }

    static Stream<Arguments> datosInvalidos() {
        LocalDate fechaValida = LocalDate.of(1990, 1, 1);
        return Stream.of(
                Arguments.of("123", "", "Apellido", fechaValida, "pais", "departamento", "ciudad", "contrasena"),
                Arguments.of("124", "Nombre", "", fechaValida, "pais", "departamento", "ciudad", "contrasena"),
                Arguments.of("125", "Nombre", "Apellido", null, "pais", "departamento", "ciudad", "contrasena"),
                Arguments.of("126", "Nombre", "Apellido", fechaValida, "", "departamento", "ciudad", "contrasena"),
                Arguments.of("127", "Nombre", "Apellido", fechaValida, "pais", "", "ciudad", "contrasena"),
                Arguments.of("128", "Nombre", "Apellido", fechaValida, "pais", "departamento", "", "contrasena"),
                Arguments.of("129", "Nombre", "Apellido", fechaValida, "pais", "departamento", "ciudad", "")
        );
    }

    @ParameterizedTest
    @MethodSource("datosinvalidosCliente")
    void testRegistrarCliente(String cedula, String nombre, String apellido, LocalDate fechaNacimiento, String pais,
            String departamento, String ciudad, String numeroCelular, String contrasena) {
        assertThrows(ClienteNocreado.class,
                () -> servicios.registrarCliente(cedula, nombre, apellido, fechaNacimiento, pais, departamento, ciudad,
                        numeroCelular, contrasena));
    }

    static Stream<Arguments> datosinvalidosCliente() {
        LocalDate fechaValida = LocalDate.of(2000, 2, 3);
        return Stream.of(
                Arguments.of("123", "", "Apellido", fechaValida, "pais", "departamento", "ciudad", "3005551234", "contrasena"),
                Arguments.of("124", "Nombre", "", fechaValida, "pais", "departamento", "ciudad", "3005551234", "contrasena"),
                Arguments.of("125", "Nombre", "Apellido", null, "pais", "departamento", "ciudad", "3005551234", "contrasena"),
                Arguments.of("126", "Nombre", "Apellido", fechaValida, "", "departamento", "ciudad", "3005551234", "contrasena"),
                Arguments.of("127", "Nombre", "Apellido", fechaValida, "pais", "", "ciudad", "3005551234", "contrasena"),
                Arguments.of("128", "Nombre", "Apellido", fechaValida, "pais", "departamento", "", "3005551234", "contrasena"),
                Arguments.of("129", "Nombre", "Apellido", fechaValida, "pais", "departamento", "ciudad", "", "contrasena"),
                Arguments.of("130", "Nombre", "Apellido", fechaValida, "pais", "departamento", "ciudad", "3005551234", "")
        );
    }

    @Test
    void testRegistrarPQRS() throws ClienteNocreado, UsuarioNoencontrado, MinutosGigasnegativos {
        LocalDate fecha = LocalDate.of(1997, 9, 3);
        servicios.registrarCliente("107", "Fernando", "Torres", fecha, "Colombia", "Tolima", "Ibagué", "3155551234", "fernando123");
        servicios.registrarPlanMovil("107", 100, 5.0, 50000, 10);
        
    }

    @ParameterizedTest
    @MethodSource("datosValidosPlanHogar")
    void testRegistrarPlanHogar(String cedula, String nombre, String apellido, LocalDate fecha, String tipoTV, int megas, double valor, double descuento, boolean debeExitoso) throws ClienteNocreado, UsuarioNoencontrado, MegasNegativas, TipoTVincorrectos {
        servicios.registrarCliente(cedula, nombre, apellido, fecha, "Colombia", "Meta", "Villavicencio", "3165551234", "passCliente");
        if (debeExitoso) {
            String resultado = servicios.registrarPlanHogar(cedula, tipoTV, megas, valor, descuento);
            assertEquals("Plan hogar registrado exitosamente", resultado, "Debe registrar plan hogar correctamente");
        } else {
            assertThrows(TipoTVincorrectos.class, () -> servicios.registrarPlanHogar(cedula, tipoTV, megas, valor, descuento));
        }
    }

    

    @ParameterizedTest
    @MethodSource("datosValidosPlanMovil")
    void testRegistrarPlanMovil(String cedula, String nombre, String apellido, LocalDate fecha, int minutos, double gigas, double valor, double descuento, boolean debeExitoso) throws ClienteNocreado, UsuarioNoencontrado, MinutosGigasnegativos {
        servicios.registrarCliente(cedula, nombre, apellido, fecha, "Colombia", "Risaralda", "Pereira", "3185551234", "passCliente");
        if (debeExitoso) {
            String resultado = servicios.registrarPlanMovil(cedula, minutos, gigas, valor, descuento);
            assertEquals("Plan móvil registrado exitosamente", resultado, "Debe registrar plan móvil correctamente");
        } else {
            assertThrows(MinutosGigasnegativos.class, () -> servicios.registrarPlanMovil(cedula, minutos, gigas, valor, descuento));
        }
    }

    

    @Test
    void testSeleccionarPlanDisponible() {
        try {
            
            servicios.crearPlanMovilAdmin(120, 10.0, 60000, 15);
            servicios.crearPlanHogarAdmin("digital", 100, 80000, 10);
            String disponibles = servicios.obtenerPlanesMovilesDisponibles();
            Pattern p = Pattern.compile("ID=(\\d+)");
            Matcher m = p.matcher(disponibles);
            if (!m.find()) {
                throw new AssertionError("No se encontró ID en la lista de planes disponibles");
            }
            int idPlan = Integer.parseInt(m.group(1));
            LocalDate fecha = LocalDate.of(1995, 6, 1);
            servicios.registrarCliente("700", "Test", "Cliente", fecha, "Colombia", "Dept", "Ciudad", "3007007000", "pass700");
            String resultado = servicios.seleccionarPlanDisponible("700", idPlan);
            assertEquals("Plan contratado exitosamente", resultado, "Debe contratar el plan disponible correctamente");
            Cliente cliente = (Cliente) servicios.buscarPorIdUsuarios("700");
            assertTrue(cliente.getPlanes().size() > 0, "El cliente debe tener al menos un plan después de contratar");
            String noEncontrado = servicios.seleccionarPlanDisponible("700", 999999);
            assertEquals("Plan no encontrado", noEncontrado, "Debe indicar que el plan no fue encontrado");            
            final int finalId = idPlan;
            assertThrows(UsuarioNoencontrado.class, () -> servicios.seleccionarPlanDisponible("999", finalId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSolicitarPlanPersonalizado() {
        try {
            LocalDate fecha = LocalDate.of(1995, 6, 15);
            servicios.registrarCliente("800", "Persona", "Prueba", fecha, "Colombia", "Dept", "Ciudad", "3008008000", "pass800");
            String descripcion = "Plan con muchas llamadas y datos";
            String resultado = servicios.solicitarPlanPersonalizado("800", descripcion);
            assertEquals("Solicitud de plan personalizado enviada exitosamente", resultado, "Debe enviar correctamente la solicitud de plan personalizado");
            Cliente cliente = (Cliente) servicios.buscarPorIdUsuarios("800");
            assertTrue(cliente.getPQRSs().stream().anyMatch(p -> p.getDescripcion() != null && p.getDescripcion().contains(descripcion)), "La PQRS del cliente debe contener la descripción enviada");
            String listaPQRS = servicios.obtenerPQRSCliente("800");
            assertNotNull(listaPQRS, "La lista de PQRS del cliente no debe ser nula");
            assertThrows(UsuarioNoencontrado.class, () -> servicios.solicitarPlanPersonalizado("999", "x"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    
}
