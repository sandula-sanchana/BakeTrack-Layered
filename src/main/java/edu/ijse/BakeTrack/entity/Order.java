package edu.ijse.BakeTrack.entity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

 public class Order {
     private int order_id;
    private int customer_id;
    private int delivery_id;
    private LocalDate order_date;
    private double total_price;
    private String status;

     public Order(int order_id, int customer_id, int delivery_id, LocalDate order_date, double total_price, String status) {
         this.order_id = order_id;
         this.customer_id = customer_id;
         this.delivery_id = delivery_id;
         this.order_date = order_date;
         this.total_price = total_price;
         this.status = status;
     }

     public Order(int customer_id, LocalDate order_date, double total_price, String status) {
         this.customer_id = customer_id;
         this.delivery_id = delivery_id;
         this.order_date = order_date;
         this.total_price = total_price;
         this.status = status;
     }

     public Order() {

     }

     public int getOrder_id() {
         return order_id;
     }

     public void setOrder_id(int order_id) {
         this.order_id = order_id;
     }

     public int getCustomerID() {
        return customer_id;
    }

    public void setCustomerID(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getDeliveryID() {
        return delivery_id;
    }

    public void setDeliveryID(int delivery_id) {
        this.delivery_id = delivery_id;
    }

    public LocalDate getOrderDate() {
        return order_date;
    }

    public void setOrderDate(String order_date_String) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.order_date = LocalDate.parse(order_date_String, format);
    }

    public double getTotalPrice() {
        return total_price;
    }

    public void setTotalPrice(double total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}