package edu.uptc.Entidades;



public class Queja extends PQRS {
    private int nivelInconformismo;
    private boolean revisada;

    public Queja(String descripcion, Plan planPQRS) {
        super(descripcion, planPQRS);
        this.nivelInconformismo = 0;
        this.revisada = false;
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
    public String toString() {
        return "Queja [ID=" + super.getIdPQRS() +
                ", Fecha=" + super.getFechaRegistro() +
                ", Descripción=" + super.getDescripcion() +
                ", Nivel inconformismo=" + nivelInconformismo +
                ", Revisada=" + revisada + "]";
    }

}
