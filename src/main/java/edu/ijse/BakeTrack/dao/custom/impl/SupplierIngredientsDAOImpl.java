package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.dao.custom.SupplierIngredientDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.entity.SupplierIngredient;

public class SupplierIngredientsDAOImpl implements SupplierIngredientDAO {


    public SupplierIngredientsDAOImpl() throws ClassNotFoundException, SQLException {

    }

    public String save(SupplierIngredient supplierIngredientDto) throws SQLException {
        String sql = "INSERT INTO supplier_ingredient (supplier_id, ingredient_id, price_per_unit, unit, last_order_date) VALUES (?, ?, ?, ?, ?)";
        int rowsAffected = SqlExecute.SqlExecute(sql,supplierIngredientDto.getSupplier_id(),supplierIngredientDto.getIngredient_id(),
                supplierIngredientDto.getPrice_per_unit(),supplierIngredientDto.getUnit(),Date.valueOf(supplierIngredientDto.getOrder_date()));

        return rowsAffected > 0 ? "Supplier-Ingredient added successfully" : "Failed to add Supplier-Ingredient";
    }


    public String update(SupplierIngredient supplierIngredientDto) throws SQLException {
        String sql = "UPDATE supplier_ingredient SET price_per_unit = ?, unit = ?, last_order_date = ? WHERE ingredient_id = ? AND supplier_id = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql,supplierIngredientDto.getPrice_per_unit(),supplierIngredientDto.getUnit(),
                Date.valueOf(supplierIngredientDto.getOrder_date()), supplierIngredientDto.getIngredient_id(),supplierIngredientDto.getSupplier_id() );

               return rowsAffected > 0 ? "Supplier-Ingredient updated successfully" : "Failed to update Supplier-Ingredient";
    }

    @Override
    public String delete(int t) throws Exception {
        return "";
    }

    public String deleteSupplierIngredient(int ingredientId, int supplierId) throws SQLException {
        String sql = "DELETE FROM supplier_ingredient WHERE ingredient_id = ? AND supplier_id = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql,ingredientId,supplierId);
        return rowsAffected > 0 ? "Supplier-Ingredient deleted successfully" : "Failed to delete Supplier-Ingredient";
    }


    public ArrayList<SupplierIngredient> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier_ingredient";
        ArrayList<SupplierIngredient> supplierIngredientList = new ArrayList<>();

        try {
            ResultSet resultSet =SqlExecute.SqlExecute(sql);

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
