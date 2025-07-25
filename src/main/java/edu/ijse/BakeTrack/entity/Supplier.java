package edu.ijse.BakeTrack.entity;

public class Supplier {
    private int supplier_id;
    private String name;
    private String contact;
    private String address;
    private String email;

    public Supplier(int supplier_id, String name, String contact, String address) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    public Supplier(String name, String contact, String address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    public Supplier(int supplier_id, String name, String contact, String address, String email) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.email = email;
    }

    public Supplier(String name, String contact, String address, String email) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.email = email;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return
                "supplier_id=" + supplier_id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\''
                ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
