package edu.uptc.Entidades;

import java.time.LocalDate;

public class Queja extends PQRS {
    private int nivelInconformismo;
    private boolean revisada;

    public Queja(LocalDate fechaRegistro, String descripcion, int nivelInconformismo, boolean revisada, Plan planPQRS) {
        super(fechaRegistro, descripcion, planPQRS);
        this.nivelInconformismo = nivelInconformismo;
        this.revisada = revisada;
    }

    public int getNivelInconformismo() {
        return nivelInconformismo;
    }

    public void setNivelInconformismo(int nivelInconformismo) {
        this.nivelInconformismo = nivelInconformismo;
    }

    public boolean isRevisada() {
        return revisada;
    }

    public void setRevisada(boolean revisada) {
        this.revisada = revisada;
    }

    @Override
    public String obtenerTipo() {
        return "Queja";
    }

    @Override
    public void procesar() {
        this.revisada = true;
    }

    @Override
    public String toString() {
        return "Queja [ID=" + super.getIdPQRS() +
                ", Fecha=" + super.getFechaRegistro() +
                ", Descripción=" + super.getDescripcion() +
                ", Nivel inconformismo=" + nivelInconformismo +
                ", Revisada=" + revisada + "]";
    }

}
