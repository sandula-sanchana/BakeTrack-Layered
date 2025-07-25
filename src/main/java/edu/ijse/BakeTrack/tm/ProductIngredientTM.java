package edu.ijse.BakeTrack.tm;

public class ProductIngredientTM {

    private int id;
    private int product_id;
    private int ingredient_id;
    private int quantity_per_product;
    private String unit;

    public ProductIngredientTM(int id, int product_id, int ingredient_id, int quantity_per_product, String unit) {
        this.id = id;
        this.product_id = product_id;
        this.ingredient_id = ingredient_id;
        this.quantity_per_product = quantity_per_product;
        this.unit = unit;
    }

    public ProductIngredientTM(int product_id, int ingredient_id, int quantity_per_product, String unit) {
        this.product_id = product_id;
        this.ingredient_id = ingredient_id;
        this.quantity_per_product = quantity_per_product;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public int getQuantity_per_product() {
        return quantity_per_product;
    }

    public void setQuantity_per_product(int quantity_per_product) {
        this.quantity_per_product = quantity_per_product;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
