package edu.ijse.BakeTrack.entity;


import java.time.LocalDate;

public class SupplierIngredient {

    private int supplier_id;
    private int ingredient_id;
    private double price_per_unit;
    private String unit;
    private LocalDate order_date;

    public SupplierIngredient(int supplier_id, int ingredient_id, double price_per_unit, String unit, LocalDate order_date) {
        this.supplier_id = supplier_id;
        this.ingredient_id = ingredient_id;
        this.price_per_unit = price_per_unit;
        this.unit = unit;
        this.order_date = order_date;
    }


    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice_per_unit() {
        return price_per_unit;
    }

    public void setPrice_per_unit(double price_per_unit) {
        this.price_per_unit = price_per_unit;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }
}