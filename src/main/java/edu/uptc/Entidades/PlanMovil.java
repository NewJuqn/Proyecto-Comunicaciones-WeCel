package edu.uptc.Entidades;

import java.time.LocalDate;

/**
 * Represents a mobile service plan.
 * Extends Plan and includes mobile-specific features such as voice minutes and
 * data gigabytes.
 */
public class PlanMovil extends Plan {
    /** Number of voice minutes included in the plan */
    private int minutos;

    /** Amount of data in gigabytes included in the plan */
    private double gigas;

    /**
     * Constructs a new PlanMovil with the specified details.
     *
     * @param fechaAdquisicion Date of plan acquisition
     * @param valorServicio    Base service value
     * @param descuento        Discount percentage
     * @param minutos          Number of voice minutes
     * @param gigas            Amount of data in gigabytes
     */
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

    /**
     * Calculates the monthly payment for this mobile plan.
     * Applies the discount to the base service value.
     *
     * @return The monthly payment amount after discount
     */
    @Override
    public double calcularPagoMensual() {
        double base = super.getValorServicio() - (super.getValorServicio() * super.getDescuento() / 100);
        return base;
    }
    /**
     * Returns a summary of the mobile plan details.
     *
     * @return String containing plan ID, minutes, and gigabytes
     */
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
