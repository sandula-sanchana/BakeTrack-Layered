package edu.ijse.BakeTrack.entity;

public class Customer {
    private int id;
    private String name;
    private String contact;
    private String address;

    public Customer() {
    }

    public Customer(int id, String name, String contact, String address){
     this.id=id;
     this.name=name;
     this.contact=contact;
     this.address=address;
   }

    public Customer(String name, String address, String contact) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    public int getCustomerID(){
     return this.id;
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

    public void setName(String name){
         this.name=name;
    }
    public void setContact(String contact){
         this.contact=contact;
    }
    public void setAddress(String address){
         this.address=address;
    }
    public void setCustomerID(int id){
          this.id=id;
    }
    

}