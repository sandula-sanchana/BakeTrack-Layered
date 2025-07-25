package edu.ijse.BakeTrack.tm;

import java.time.LocalDate;

public class DeliveryTM {
    private int delivery_id;
    private int vehicle_id;
    private int employee_id;
    private LocalDate delivery_date;
    private String area;

    public DeliveryTM(int delivery_id, int vehicle_id, int employee_id, LocalDate delivery_date, String area) {
        this.delivery_id = delivery_id;
        this.vehicle_id = vehicle_id;
        this.employee_id = employee_id;
        this.delivery_date = delivery_date;
        this.area = area;
    }

    public int getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(int delivery_id) {
        this.delivery_id = delivery_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public LocalDate getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(LocalDate delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
