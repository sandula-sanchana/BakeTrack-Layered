package edu.ijse.BakeTrack.tm;

public class VehicleTM {
    private int vehicle_id;
    private String type;
    private String license_plate;
    private String status;

    public VehicleTM(int vehicle_id, String type, String license_plate, String status) {
        this.vehicle_id = vehicle_id;
        this.type = type;
        this.license_plate = license_plate;
        this.status = status;
    }

    public VehicleTM(int vehicle_id, String type, String license_plate) {
        this.vehicle_id = vehicle_id;
        this.type = type;
        this.license_plate = license_plate;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
