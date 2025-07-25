package edu.ijse.BakeTrack.dto;

public class EmployeeDto {
      private int employee_id;
      private String name;
      private String address;
      private Double salary;
      private String contact;
      private String role;

    public EmployeeDto(int employee_id, String name, String address, Double salary, String contact, String role) {
        this.employee_id = employee_id;
        this.name = name;
        this.salary = salary;
        this.address = address;
        this.contact = contact;
        this.role = role;
    }

    public EmployeeDto(String name, String address, Double salary, String contact, String role) {
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.contact = contact;
        this.role = role;
    }

    public EmployeeDto(int employee_id, String name) {
        this.employee_id = employee_id;
        this.name = name;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getName(){
        return this.name;
   }
   public String getContact(){
        return this.contact;
   }
   public String getAddress(){
        return this.address;
   }
   public Double getSalary(){
        return this.salary;
   }
   public String getROle(){
     return this.role;
   }


   public void setName(String name){
        this.name=name;
   }
   public void setContact(String contact){
        this.contact=contact;
   }
   public void setAddress(String address){
        this.address=address;
   }
   public void setSalary(Double salary){
    this.salary=salary;
   }
   public void setRole(String role){
     this.role=role;
   }

   

}
