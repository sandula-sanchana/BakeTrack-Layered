package edu.ijse.BakeTrack.tm;

public class OrderDetailTM {
    private int productId; // <-- renamed from product_id
    private int orderId;
    private int quantity;
    private double priceAtOrder; // <-- renamed from price_at_order

    public OrderDetailTM(int productId, int quantity, double priceAtOrder) {
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    public OrderDetailTM(int orderId,int productId,  int quantity, double priceAtOrder) {
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    public int getProductId() {  // <-- camelCase getter
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtOrder() {  // <-- camelCase getter
        return priceAtOrder;
    }

    public void setPriceAtOrder(double priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }
}
