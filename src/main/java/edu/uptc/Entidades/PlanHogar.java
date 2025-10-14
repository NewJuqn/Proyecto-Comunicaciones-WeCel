package edu.uptc.Entidades;

import java.time.LocalDate;

public class PlanHogar extends Plan {
    private boolean tvDigital;
    private double megas;

    public PlanHogar(LocalDate fechaAdquisicion, double valorServicio, double descuento, boolean tvDigital,
            double megas) {
        super(fechaAdquisicion, valorServicio, descuento);
        this.tvDigital = tvDigital;
        this.megas = megas;
    }

    public boolean isTvDigital() {
        return tvDigital;
    }

    public void setTvDigital(boolean tvDigital) {
        this.tvDigital = tvDigital;
    }

    public double getMegas() {
        return megas;
    }

    public void setMegas(double megas) {
        this.megas = megas;
    }

    @Override
    public double calcularPagoMensual() {
        double base = super.getValorServicio() - (super.getValorServicio() * super.getDescuento() / 100);
        return base;
    }

    @Override
    public String mostrarDetallesPlan() {
        String tipoTV = tvDigital ? "TV Digital" : "TV Análoga";
        return "Plan Hogar [ID=" + super.getIdPlan() + ", " + tipoTV + ", megas=" + megas + "]";
    }

    @Override
    public String toString() {
        return "Plan Hogar [ID=" + super.getIdPlan() +
                ", Fecha=" + super.getFechaAdquisicion() +
                ", Valor=" + super.getValorServicio() +
                ", Descuento=" + super.getDescuento() + "%" +
                ", TV=" + (tvDigital ? "Digital" : "Análoga") +
                ", Megas=" + megas + "]";
    }

}
