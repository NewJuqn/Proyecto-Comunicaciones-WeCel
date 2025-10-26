package edu.uptc.Servicios;

import java.time.LocalDate;
import java.util.ArrayList;

import edu.uptc.Entidades.Asesor;
import edu.uptc.Entidades.Cliente;
import edu.uptc.Entidades.PlanHogar;
import edu.uptc.Entidades.PlanMovil;
import edu.uptc.Entidades.Usuario;
import edu.uptc.exepciones.AsesorNocreado;
import edu.uptc.exepciones.ClienteNocreado;
import edu.uptc.exepciones.MegasNegativas;
import edu.uptc.exepciones.MinutosGigasnegativos;
import edu.uptc.exepciones.TipoTVincorrectos;
import edu.uptc.exepciones.UsuarioNoencontrado;

public class Servicios {
    private ArrayList<Usuario> usuarios;
    private int contadorPlanes;
    private int contadorPQRS;

    public Servicios() {
        this.usuarios = new ArrayList<>();
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
        usuarios.add(nuevoCliente);
        return "Cliente registrado";
    }

    public String registrarAsesor(String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String estado, String ciudad, String contrasena)throws AsesorNocreado {

        if (nombre == null || apellido == null || fechaNacimiento == null ||
                pais == null || estado == null || ciudad == null || contrasena == null ||
                nombre.trim().isEmpty() || apellido.trim().isEmpty() ||
                pais.trim().isEmpty() || estado.trim().isEmpty() ||
                ciudad.trim().isEmpty() || contrasena.trim().isEmpty()) {
            throw new AsesorNocreado("vuelve a ingresar el asesor, algo salio mal");
        }

        Asesor nuevoAsesor = new Asesor(nombre, apellido, fechaNacimiento,
                pais, estado, ciudad, contrasena);
        usuarios.add(nuevoAsesor);
        return "Asesor registrado";
    }

    public Usuario buscarPorIdUsuarios(int id){
        for (Usuario usuario : usuarios) {
            if (usuario.getId()==id) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario login(int id, String contrasena){
        Usuario usuarioEncontrado = buscarPorIdUsuarios(id);
        if (usuarioEncontrado!=null && usuarioEncontrado.getContrasena().equalsIgnoreCase(contrasena)) {
            return usuarioEncontrado;
        }
        return null;
    }

    public String registrarPlanMovil(int idCliente, int minutos, double gigas, double valorServicio, double descuento)
        throws UsuarioNoencontrado, MinutosGigasnegativos{
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

    public String registrarPlanHogar(int idCliente, String tipoTV, int megasInternet, double valorServicio, double descuento)
        throws UsuarioNoencontrado, MegasNegativas, TipoTVincorrectos {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        
        if (usuario == null || !(usuario instanceof Cliente)) {
            throw new UsuarioNoencontrado("id o contraseña incorrectos, intenta volver a ingresar");
        }
        
        if (megasInternet <= 0) {
           throw new MegasNegativas("ingrese un numero mayor a 0 en la megas");
        }
        
        if (tipoTV == null || (!tipoTV.equalsIgnoreCase("digital") && !tipoTV.equalsIgnoreCase("análoga"))) {
            throw new TipoTVincorrectos("tipo de television invorrectos, ingrese los que son corretos");
        }

        Cliente cliente = (Cliente) usuario;
        cliente.getPlanes().add(new PlanHogar(LocalDate.now(), valorServicio, descuento, tipoTV, megasInternet));
        contadorPlanes++;
        
        return "Plan hogar registrado exitosamente";
    }
    
    
}
