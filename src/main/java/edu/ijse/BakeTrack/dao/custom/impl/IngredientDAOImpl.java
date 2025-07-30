package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.BakeTrack.dao.custom.IngredientDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.entity.Ingredient;
import edu.ijse.BakeTrack.tm.IngredientTM;
import javafx.collections.ObservableList;

public class IngredientDAOImpl implements IngredientDAO {
     
    private Connection connection;

    public IngredientDAOImpl() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

     public String save(Ingredient ingredientDto) throws SQLException {
        String sql = "INSERT INTO ingredient (name, stock_amount, unit, buying_price, expire_date) VALUES (?, ?, ?, ?, ?)";

        Boolean done= SqlExecute.SqlExecute(sql,ingredientDto.getName(),ingredientDto.getStock_amount(),ingredientDto.getUnit(),
                ingredientDto.getBuying_price(),Date.valueOf(ingredientDto.getExpire_date()));

        if (done) {
            return "Ingredient added successfully";
        } else {
            return "Failed to add ingredient";
        }
    }

    public int countIng() throws SQLException {
        ResultSet rs= null;
        try {
            String sql="SELECT COUNT(*) as count FROM ingredient";
            rs = SqlExecute.SqlExecute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        while (rs.next()){
            return rs.getInt("count");
        }
        return -1;
    }

    public String delete(int ingredientId) throws SQLException {
        String sql = "DELETE FROM ingredient WHERE ingredient_id = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql,ingredientId);
        if (rowsAffected > 0) {
            return "Ingredient deleted successfully";
        } else {
            return "Failed to delete ingredient";
        }
    }

    public String update( Ingredient ingredientDto) throws SQLException {
        String sql = "UPDATE ingredient SET name = ?, stock_amount = ?, unit = ?, buying_price = ?, expire_date = ? WHERE ingredient_id = ?";

        Boolean done= SqlExecute.SqlExecute(sql,ingredientDto.getName(),ingredientDto.getStock_amount(),ingredientDto.getUnit(),
                ingredientDto.getBuying_price(),Date.valueOf(ingredientDto.getExpire_date()),ingredientDto.getIngredient_id());
        if (done) {
            return "Ingredient updated successfully";
        } else {
            return "Failed to update ingredient";
        }
    }

    public ArrayList<Ingredient> getAll()  {
        String allSql="SELECT * FROM ingredient";
        ArrayList<Ingredient> getall=new ArrayList<>();

        try {
            ResultSet resultSet=SqlExecute.SqlExecute(allSql);
            while (resultSet.next()){
                getall.add(new Ingredient( resultSet.getInt("ingredient_id"),resultSet.getString("name"), resultSet.getInt("stock_amount"),resultSet.getString("unit"), resultSet.getDouble("buying_price"),resultSet.getDate("expire_date").toLocalDate()));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        }
        return getall;
    }

    public String getIngredientNameById(int ingredientId) throws SQLException {
        String sql = "SELECT name FROM ingredient WHERE ingredient_id = ?";
            try (ResultSet resultSet = SqlExecute.SqlExecute(sql,ingredientId)) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    return null;
                }

        }
    }

    public int getStockById(int ingredientId) throws SQLException {
        String sql = "SELECT stock_amount FROM ingredient WHERE ingredient_id = ?";
            try (ResultSet resultSet = SqlExecute.SqlExecute(sql,ingredientId)) {
                if (resultSet.next()) {
                    return resultSet.getInt("stock_amount");
                } else {
                    System.out.println("Ingredient not found");
                    return -1;
                }

        }
    }

    public boolean deductIngredientStock(ObservableList<IngredientTM> ingredients) throws SQLException {
        String sql = "UPDATE ingredient SET stock_amount = (stock_amount - ?) WHERE ingredient_id = ?";
        for (IngredientTM ingredient : ingredients) {
            boolean success = SqlExecute.SqlExecute(sql, ingredient.getTotal_amount_need(), ingredient.getIngredient_id());
            if (!success) return false;
        }
        return true;
    }




}
