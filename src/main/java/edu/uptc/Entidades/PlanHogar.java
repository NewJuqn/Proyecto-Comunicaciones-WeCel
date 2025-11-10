package edu.uptc.Entidades;

import java.time.LocalDate;
/**
 * Represents a home service plan.
 * Extends Plan and includes home-specific features such as TV type and internet megabytes.
 */
public class PlanHogar extends Plan {
    /** Type of TV service (digital or analog) */
    private String tipoTV;
    
    /** Internet speed in megabytes */
    private double megas;

    /**
     * Constructs a new PlanHogar with the specified details.
     *
     * @param fechaAdquisicion Date of plan acquisition
     * @param valorServicio Base service value
     * @param descuento Discount percentage
     * @param tipoTV Type of TV service
     * @param megas Internet speed in megabytes
     */
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
    /**
     * Calculates the monthly payment for this home plan.
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
     * Returns a summary of the home plan details.
     *
     * @return String containing plan ID, TV type, and internet speed
     */
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
