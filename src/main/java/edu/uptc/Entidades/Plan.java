package edu.uptc.Entidades;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Plan {
    private int idPlan;
    private static int contadorID = 10000;
    private LocalDate fechaAdquisicion;
    private double valorServicio;
    private double descuento;
    private ArrayList<PQRS> pqrsLista;

    public Plan(LocalDate fechaAdquisicion, double valorServicio, double descuento) {
        this.idPlan = contadorID++;
        this.fechaAdquisicion = fechaAdquisicion;
        this.valorServicio = valorServicio;
        this.descuento = descuento;
        this.pqrsLista = new ArrayList<>();
    }

    public abstract double calcularPagoMensual();

    public abstract String mostrarDetallesPlan();

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public double getValorServicio() {
        return valorServicio;
    }

    public void setValorServicio(double valorServicio) {
        this.valorServicio = valorServicio;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public ArrayList<PQRS> getPqrsLista() {
        return pqrsLista;
    }

    public void setPqrsLista(ArrayList<PQRS> pqrsLista) {
        this.pqrsLista = pqrsLista;
    }

    @Override
    public String toString() {
        return "Plan [ID=" + idPlan + ", Fecha adquisición=" + fechaAdquisicion +
                ", Valor servicio=" + valorServicio + ", Descuento=" + descuento + "%]";
    }

}
