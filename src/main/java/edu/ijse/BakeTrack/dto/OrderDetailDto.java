package edu.ijse.BakeTrack.dto;

public class OrderDetailDto {
    private int product_id;
    private int order_id;
    private int quantity;
    private double price_at_order;

    public OrderDetailDto(int order_id, int product_id, int quantity, double price_at_order) {
        this.product_id = product_id;
        this.order_id = order_id;
        this.quantity = quantity;
        this.price_at_order = price_at_order;
    }

    public OrderDetailDto(int product_id, int quantity, double price_at_order) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.price_at_order = price_at_order;
    }

    public int getProductID() {
        return product_id;
    }

    public void setProductID(int product_id) {
        this.product_id = product_id;
    }

    public int getOrderID() {
        return order_id;
    }

    public void setOrderID(int order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtOrder() {
        return price_at_order;
    }

    public void setPriceAtOrder(double price_at_order) {
        this.price_at_order = price_at_order;
    }
}
