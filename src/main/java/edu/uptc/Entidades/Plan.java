package edu.uptc.Entidades;

import java.time.LocalDate;
/**
 * Abstract base class representing a service plan in the system.
 * Contains common attributes for all plan types including pricing, discounts, and acquisition date.
 * Subclasses must implement methods for calculating monthly payments and displaying plan details.
 */
public abstract class Plan {
    /** Unique identifier for the plan */
    private int idPlan;
    
    /** Static counter for generating unique plan IDs */
    private static int contadorID = 10000;
    
    /** Date when the plan was acquired */
    private LocalDate fechaAdquisicion;
    
    /** Base service value before discounts */
    private double valorServicio;
    
    /** Discount percentage applied to the plan */
    private double descuento;

    /**
     * Constructs a new Plan with the specified details.
     * Automatically assigns a unique ID using the static counter.
     *
     * @param fechaAdquisicion Date of plan acquisition
     * @param valorServicio Base service value
     * @param descuento Discount percentage
     */
    public Plan(LocalDate fechaAdquisicion, double valorServicio, double descuento) {
        this.idPlan = contadorID++;
        this.fechaAdquisicion = fechaAdquisicion;
        this.valorServicio = valorServicio;
        this.descuento = descuento;
    }
    /**
     * Calculates the monthly payment for this plan.
     * Must be implemented by subclasses to define specific calculation logic.
     *
     * @return The monthly payment amount
     */
    public abstract double calcularPagoMensual();
    /**
     * Returns a string with the plan's details.
     * Must be implemented by subclasses to provide specific plan information.
     *
     * @return String containing plan details
     */
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

    @Override
    public String toString() {
        return "Plan [ID=" + idPlan + ", Fecha adquisición=" + fechaAdquisicion +
                ", Valor servicio=" + valorServicio + ", Descuento=" + descuento + "%]";
    }

}
