package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.dao.custom.SupplierDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.entity.Supplier;

public class SupplierDAOImpl implements SupplierDAO {

    public SupplierDAOImpl() throws ClassNotFoundException, SQLException {
    }

    public String save(Supplier supplierDto) throws SQLException {
        String sql = "INSERT INTO supplier (name, contact, address, email) VALUES (?, ?, ?, ?)";
        boolean rowsAffected = SqlExecute.SqlExecute(sql,supplierDto.getName(),supplierDto.getContact(),supplierDto.getAddress(),supplierDto.getEmail());
        return (rowsAffected ) ? "Supplier added successfully" : "Failed to add supplier";
    }

    public String delete(int supplierId) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplier_id = ?";
        boolean rowsAffected = SqlExecute.SqlExecute(sql,supplierId);
        if (rowsAffected) {
            return "Supplier deleted successfully";
        } else {
            return "Failed to delete supplier";
        }
    }

    public String update(Supplier supplierDto) throws SQLException {
        String sql = "UPDATE supplier SET name = ?, contact = ?, address = ?, email = ? WHERE supplier_id = ?";
        boolean rowsAffected = SqlExecute.SqlExecute(sql,supplierDto.getName(),supplierDto.getContact(),supplierDto.getAddress(),supplierDto.getEmail(),supplierDto.getSupplier_id());
        return (rowsAffected) ? "Supplier updated successfully" : "Failed to update supplier";
    }


    public void getSupplierById(int supplierId) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE supplier_id = ?";
        ResultSet resultSet = SqlExecute.SqlExecute(sql,supplierId);
        if (resultSet.next()) {
           System.out.println(resultSet.getString("name"));
        }
    }

    public ArrayList<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier";
        ArrayList<Supplier> supplierList = new ArrayList<>();

        try {

            ResultSet resultSet =SqlExecute.SqlExecute(sql);

            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getInt("supplier_id"),
                        resultSet.getString("name"),
                        resultSet.getString("contact"),
                        resultSet.getString("address"),
                        resultSet.getString("email")
                );
                supplierList.add(supplier);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return supplierList;
    }
}
