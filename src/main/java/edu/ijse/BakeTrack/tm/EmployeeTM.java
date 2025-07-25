package edu.ijse.BakeTrack.tm;

public class EmployeeTM {
    private int employee_id;
    private String name;
    private String address;
    private Double salary;
    private String contact;
    private String role;

    public EmployeeTM(int employee_id, String name, String address, Double salary, String contact, String role) {
        this.employee_id = employee_id;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.contact = contact;
        this.role = role;
    }

    public EmployeeTM(int employee_id, String name, String contact, String role) {
        this.employee_id = employee_id;
        this.name = name;
        this.contact = contact;
        this.role = role;
    }

    public EmployeeTM(int employee_id, String name) {
        this.employee_id = employee_id;
        this.name = name;
    }

    public int getEmployeeID() {
        return employee_id;
    }

    public void setEmployeeID(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toString() {
        return employee_id + "---->  " + name ;
    }
}
