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
            return "Algo salio mal";
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
            return "Algo salio mal";
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
}
