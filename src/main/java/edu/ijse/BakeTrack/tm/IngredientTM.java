package edu.ijse.BakeTrack.tm;

import java.time.LocalDate;

public class IngredientTM {
    private  int ingredient_id;
    private String name;
    private int stock_amount;
    private String unit;
    private double buying_price;
    private LocalDate expire_date;
    private int total_amount_need;
    private String product_name;

    public IngredientTM(int ingredient_id, String name, int stock_amount, String unit, double buying_price, LocalDate expire_date) {
        this.ingredient_id = ingredient_id;
        this.name = name;
        this.stock_amount = stock_amount;
        this.unit = unit;
        this.buying_price = buying_price;
        this.expire_date = expire_date;
    }

    public IngredientTM(String product_name,int ingredient_id, String name, int total_amount_need) {
        this.product_name=product_name;
        this.ingredient_id = ingredient_id;
        this.name = name;
        this.total_amount_need = total_amount_need;
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

    public int getTotal_amount_need() {
        return total_amount_need;
    }

    public void setTotal_amount_need(int total_amount_need) {
        this.total_amount_need = total_amount_need;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
