package edu.ijse.BakeTrack.dto;

public class CustomDto {
    private int employee_id;
    private String name;
    private String address;
    private Double salary;
    private String contact;
    private String role;

    public CustomDto(int employee_id, String name, String address, Double salary, String contact, String role) {
        this.employee_id = employee_id;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.contact = contact;
        this.role = role;
    }

    public CustomDto(String name, String address, Double salary, String contact, String role) {
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.contact = contact;
        this.role = role;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Double getSalary() {
        return salary;
    }

    public String getContact() {
        return contact;
    }

    public String getRole() {
        return role;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
