package edu.uptc.Entidades;

import java.time.LocalDate;

public class PlanHogar extends Plan {
    private String tipoTV;
    private double megas;

    public PlanHogar(LocalDate fechaAdquisicion, double valorServicio, double descuento, String tipoTV,
            double megas) {
        super(fechaAdquisicion, valorServicio, descuento);
        this.tipoTV = tipoTV;
        this.megas = megas;
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
        return "Plan Hogar [ID=" + super.getIdPlan() + ", " + this.tipoTV + ", megas=" + this.megas + "]";
    }

    @Override
    public String toString() {
        return "Plan Hogar [ID=" + super.getIdPlan() +
                ", Fecha=" + super.getFechaAdquisicion() +
                ", Valor=" + super.getValorServicio() +
                ", Descuento=" + super.getDescuento() + "%" +
                ", TV=" + (this.tipoTV) +
                ", Megas=" + this.megas + "]";
    }

    public String getTipoTV() {
        return tipoTV;
    }

    public void setTipoTV(String tipoTV) {
        this.tipoTV = tipoTV;
    }

}
