package edu.uptc.Entidades;

import java.time.LocalDate;

public class Sugerencia extends PQRS {
    private int nivelImportancia;

    public Sugerencia(LocalDate fechaRegistro, String descripcion, int nivelImportancia, Plan planPQRS) {
        super(fechaRegistro, descripcion, planPQRS);
        this.nivelImportancia = nivelImportancia;
    }

    public int getNivelImportancia() {
        return nivelImportancia;
    }

    public void setNivelImportancia(int nivelImportancia) {
        this.nivelImportancia = nivelImportancia;
    }

    @Override
    public String obtenerTipo() {
        return "Sugerencia";
    }

    @Override
    public void procesar() {

    }

    public void procesar(int nuevaImportancia) {
        setNivelImportancia(nuevaImportancia);
    }

    @Override
    public String toString() {
        return "Sugerencia [ID=" + super.getIdPQRS() +
                ", Fecha=" + super.getFechaRegistro() +
                ", Descripción=" + super.getDescripcion() +
                ", Nivel de importancia=" + nivelImportancia + "]";
    }

}
