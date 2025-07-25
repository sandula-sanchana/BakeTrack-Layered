package edu.ijse.BakeTrack.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IngredientDto {
    private  int ingredient_id;
    private String name;
    private int stock_amount;
    private String unit;
    private double buying_price;
    private LocalDate expire_date;

    public IngredientDto(int ingredient_id, String name, int stock_amount, String unit, double buying_price, LocalDate expire_date) {
        this.ingredient_id = ingredient_id;
        this.name = name;
        this.stock_amount = stock_amount;
        this.unit = unit;
        this.buying_price = buying_price;
        this.expire_date = expire_date;
    }

    public IngredientDto(String name, int stock_amount, String unit, double buying_price, LocalDate expire_date) {
        this.name = name;
        this.stock_amount = stock_amount;
        this.unit = unit;
        this.buying_price = buying_price;
        this.expire_date = expire_date;
    }

    public IngredientDto(int ingredient_id, String name, String unit) {
        this.ingredient_id = ingredient_id;
        this.name = name;
        this.unit = unit;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock_amount() {
        return stock_amount;
    }

    public void setStock_amount(int stock_amount) {
        this.stock_amount = stock_amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getBuying_price() {
        return buying_price;
    }

    public void setBuying_price(double buying_price) {
        this.buying_price = buying_price;
    }

    public LocalDate getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(LocalDate expire_date) {
        this.expire_date = expire_date;
    }

    @Override
    public String toString() {
        return
                "ingredient_id=" + ingredient_id +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\''
                ;
    }
}