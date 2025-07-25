package edu.ijse.BakeTrack.tm;

import java.time.LocalDate;

public class OrderTM {
    private int order_id;
    private int customer_id;
    private int delivery_id;
    private LocalDate order_date;
    private double total_price;
    private String status;

    public OrderTM(int order_id, int customer_id, int delivery_id, LocalDate order_date, double total_price, String status) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.delivery_id = delivery_id;
        this.order_date = order_date;
        this.total_price = total_price;
        this.status = status;
    }

    public OrderTM(int customer_id, int delivery_id, LocalDate order_date, double total_price, String status) {
        this.customer_id = customer_id;
        this.delivery_id = delivery_id;
        this.order_date = order_date;
        this.total_price = total_price;
        this.status = status;
    }

    public OrderTM(int order_id, int customer_id, LocalDate order_date, double total_price) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.total_price = total_price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(int delivery_id) {
        this.delivery_id = delivery_id;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return ">>>"+
                "order_id=" + order_id +
                ", customer_id=" + customer_id +
                ", order_date=" + order_date +
                ", total_price=" + total_price +
                '}';
    }
}
