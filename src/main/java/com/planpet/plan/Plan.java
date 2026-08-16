package com.planpet.plan;

import java.math.BigDecimal;

/**
 * Entidad Plan: representa un plan de salud ofrecido por PlanPet.
 */
public class Plan {

    private int idPlan;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int idCompany;

    public Plan() {
    }

    public Plan(String nombre, String descripcion, BigDecimal precio, int idCompany) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idCompany = idCompany;
    }

    public Plan(int idPlan, String nombre, String descripcion, BigDecimal precio, int idCompany) {
        this.idPlan = idPlan;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idCompany = idCompany;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }
}
