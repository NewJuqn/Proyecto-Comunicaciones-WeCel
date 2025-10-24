package edu.uptc.Servicios;

import java.time.LocalDate;
import java.util.ArrayList;

import edu.uptc.Entidades.*;

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
            String pais, String estado, String ciudad, String contrasena) {

        if (nombre == null || apellido == null || fechaNacimiento == null ||
                pais == null || estado == null || ciudad == null || contrasena == null ||
                nombre.trim().isEmpty() || apellido.trim().isEmpty() ||
                pais.trim().isEmpty() || estado.trim().isEmpty() ||
                ciudad.trim().isEmpty() || contrasena.trim().isEmpty()) {
            return "Algo salio mal"; //cambiar por excepcion
        }

        Cliente nuevoCliente = new Cliente(nombre, apellido, fechaNacimiento,
                pais, estado, ciudad, contrasena);
        usuarios.add(nuevoCliente);
        return "Cliente registrado";
    }

    public String registrarAsesor(String nombre, String apellido, LocalDate fechaNacimiento,
            String pais, String estado, String ciudad, String contrasena) {

        if (nombre == null || apellido == null || fechaNacimiento == null ||
                pais == null || estado == null || ciudad == null || contrasena == null ||
                nombre.trim().isEmpty() || apellido.trim().isEmpty() ||
                pais.trim().isEmpty() || estado.trim().isEmpty() ||
                ciudad.trim().isEmpty() || contrasena.trim().isEmpty()) {
            return "Algo salio mal"; //cambiar por excepcion
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

    public String registrarPlanMovil(int idCliente, int minutos, double gigas, double valorServicio, double descuento) {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        
        if (usuario == null || !(usuario instanceof Cliente)) {
            return "Cliente no encontrado"; //Cambiar por excepcion
        }
        
        if (minutos <= 0 || gigas <= 0) {
            return "Los valores de minutos y gigas deben ser positivos"; //cambiar por excepcion
        }

        Cliente cliente = (Cliente) usuario;
        cliente.getPlanes().add(new PlanMovil(LocalDate.now(), valorServicio, descuento, minutos, gigas));
        contadorPlanes++;
        return "Plan móvil registrado exitosamente";
    }

    public String registrarPlanHogar(int idCliente, String tipoTV, int megasInternet, double valorServicio, double descuento) {
        Usuario usuario = buscarPorIdUsuarios(idCliente);
        
        if (usuario == null || !(usuario instanceof Cliente)) {
            return "Cliente no encontrado"; //cambiar por excepcion
        }
        
        if (megasInternet <= 0) {
            return "Los megas de internet deben ser positivos"; //cambiar por excepcion
        }
        
        if (tipoTV == null || (!tipoTV.equalsIgnoreCase("digital") && !tipoTV.equalsIgnoreCase("análoga"))) {
            return "Tipo de TV inválido. Debe ser 'digital' o 'análoga'"; //cambiar por excepcion
        }

        Cliente cliente = (Cliente) usuario;
        cliente.getPlanes().add(new PlanHogar(LocalDate.now(), valorServicio, descuento, tipoTV, megasInternet));
        contadorPlanes++;
        
        return "Plan hogar registrado exitosamente";
    }
    
}
