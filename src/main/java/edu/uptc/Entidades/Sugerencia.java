package edu.uptc.Entidades;


public class Sugerencia extends PQRS {
    private int nivelImportancia;

    public Sugerencia(String descripcion, int nivelImportancia, Plan planPQRS) {
        super(descripcion, planPQRS);
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
