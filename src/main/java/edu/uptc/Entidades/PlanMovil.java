package edu.uptc.Entidades;

import java.time.LocalDate;

public class PlanMovil extends Plan {
    private int minutos;
    private double gigas;

    public PlanMovil(LocalDate fechaAdquisicion, double valorServicio, double descuento, int minutos, double gigas) {
        super(fechaAdquisicion, valorServicio, descuento);
        this.minutos = minutos;
        this.gigas = gigas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public double getGigas() {
        return gigas;
    }

    public void setGigas(double gigas) {
        this.gigas = gigas;
    }

    @Override
    public double calcularPagoMensual() {
        double base = super.getValorServicio() - (super.getValorServicio() * super.getDescuento() / 100);
        return base;
    }

    @Override
    public String mostrarDetallesPlan() {
        return "Plan Móvil [ID=" + super.getIdPlan() + ", minutos=" + this.minutos + ", gigas=" + this.gigas + "]";
    }

    @Override
    public String toString() {
        return "Plan Móvil [ID=" + super.getIdPlan() +
                ", Fecha=" + super.getFechaAdquisicion() +
                ", Valor=" + super.getValorServicio() +
                ", Descuento=" + super.getDescuento() + "%" +
                ", Minutos=" + this.minutos +
                ", Gigas=" + this.gigas + "]";
    }

}
