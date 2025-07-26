package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import edu.ijse.BakeTrack.dao.custom.SupplierIngredientDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.entity.SupplierIngredient;

public class SupplierIngredientsDAOImpl implements SupplierIngredientDAO {
    private Connection connection;

    public SupplierIngredientsDAOImpl() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public String save(SupplierIngredient supplierIngredientDto) throws SQLException {
        String sql = "INSERT INTO supplier_ingredient (supplier_id, ingredient_id, price_per_unit, unit, last_order_date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, supplierIngredientDto.getSupplier_id());
        statement.setInt(2, supplierIngredientDto.getIngredient_id());
        statement.setDouble(3, supplierIngredientDto.getPrice_per_unit());
        statement.setString(4, supplierIngredientDto.getUnit());
        statement.setDate(5, Date.valueOf(supplierIngredientDto.getOrder_date()));

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0 ? "Supplier-Ingredient added successfully" : "Failed to add Supplier-Ingredient";
    }


    public String update(SupplierIngredient supplierIngredientDto) throws SQLException {
        String sql = "UPDATE supplier_ingredient SET price_per_unit = ?, unit = ?, last_order_date = ? WHERE ingredient_id = ? AND supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, supplierIngredientDto.getPrice_per_unit());
        statement.setString(2, supplierIngredientDto.getUnit());
        statement.setDate(3, Date.valueOf(supplierIngredientDto.getOrder_date()));
        statement.setInt(4, supplierIngredientDto.getIngredient_id());
        statement.setInt(5, supplierIngredientDto.getSupplier_id());

        int rowsAffected = statement.executeUpdate();

               return rowsAffected > 0 ? "Supplier-Ingredient updated successfully" : "Failed to update Supplier-Ingredient";
    }

    @Override
    public String delete(int t) throws Exception {
        return "";
    }

    public String deleteSupplierIngredient(int ingredientId, int supplierId) throws SQLException {
        String sql = "DELETE FROM supplier_ingredient WHERE ingredient_id = ? AND supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ingredientId);
        statement.setInt(2, supplierId);

        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "Supplier-Ingredient deleted successfully" : "Failed to delete Supplier-Ingredient";
    }

      public void getSupplierIngredientByID(int ingredientId, int supplierId) throws SQLException {
        String sql = "SELECT * FROM supplier_ingredient WHERE ingredient_id = ? AND supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ingredientId);
        statement.setInt(2, supplierId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println("Ingredient ID: " + resultSet.getInt("ingredient_id"));
            System.out.println("Supplier ID: " + resultSet.getInt("supplier_id"));
            System.out.println("Price per Unit: " + resultSet.getDouble("price_per_unit"));
            System.out.println("Unit: " + resultSet.getString("unit"));
            System.out.println("Last Order Date: " + resultSet.getDate("last_order_date"));
        } else {
            System.out.println("No record found for ingredient_id: " + ingredientId + " and supplier_id: " + supplierId);
        }
    }

    public ArrayList<SupplierIngredient> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier_ingredient";
        ArrayList<SupplierIngredient> supplierIngredientList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SupplierIngredient supplierIngredient = new SupplierIngredient(
                        resultSet.getInt("supplier_id"),
                        resultSet.getInt("ingredient_id"),
                        resultSet.getDouble("price_per_unit"),
                        resultSet.getString("unit"),
                        resultSet.getDate("last_order_date").toLocalDate()
                );
                supplierIngredientList.add(supplierIngredient);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return supplierIngredientList;
    }


}
