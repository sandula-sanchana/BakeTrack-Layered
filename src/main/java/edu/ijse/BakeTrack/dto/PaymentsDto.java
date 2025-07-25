package edu.ijse.BakeTrack.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentsDto {
    private int payment_id;
    private int order_id;
    private double price;
    private String payment_method;
    private LocalDate payment_date;
    private String status;

    public PaymentsDto(int payment_id, int order_id, double price, String payment_method, LocalDate payment_date,String status) {
        this.payment_id = payment_id;
        this.order_id = order_id;
        this.price = price;
        this.payment_method = payment_method;
        this.payment_date = payment_date;
        this.status=status;
    }

    public PaymentsDto(int payment_id,int order_id, String payment_method, LocalDate payment_date, String status) {
        this.payment_id = payment_id;
        this.order_id = order_id;
        this.payment_method = payment_method;
        this.payment_date = payment_date;
        this.status = status;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getOrderID() {
        return order_id;
    }

    public void setOrderID(int order_id) {
        this.order_id = order_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPaymentMethod() {
        return payment_method;
    }

    public void setPaymentMethod(String payment_method) {
        this.payment_method = payment_method;
    }

    public LocalDate getPaymentDate() {

        return payment_date;
    }

    public void setPaymentDate(LocalDate payment_date) {
       this.payment_date=payment_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

