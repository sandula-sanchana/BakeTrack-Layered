package edu.ijse.BakeTrack.dao.custom.impl;

import edu.ijse.BakeTrack.dao.custom.ProductIngredientDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.entity.ProductIngredient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductIngredientDAOImpl implements ProductIngredientDAO {

    private Connection connection;

    public ProductIngredientDAOImpl() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public ArrayList<ProductIngredient> getProductIngredientByPid(int productID) throws SQLException {
        ArrayList<ProductIngredient> productIngredientDtoArrayList=new ArrayList<>();
        String sql="SELECT * FROM product_ingredient WHERE product_id=?";

        ResultSet resultSet= SqlExecute.SqlExecute(sql,productID);

        while (resultSet.next()){
             ProductIngredient productIngredientDto=new ProductIngredient(resultSet.getInt("id"),resultSet.getInt("product_id"),resultSet.getInt("ingredient_id"),
                     resultSet.getInt("quantity_required"),resultSet.getString("unit"));
             productIngredientDtoArrayList.add(productIngredientDto);
        }


        return productIngredientDtoArrayList;
    }

    public ArrayList<ProductIngredient> getAll() throws SQLException {
        ArrayList<ProductIngredient> list = new ArrayList<>();
        String sql = "SELECT * FROM product_ingredient";

        ResultSet resultSet = SqlExecute.SqlExecute(sql);

        while (resultSet.next()) {
            ProductIngredient dto = new ProductIngredient(
                    resultSet.getInt("id"),
                    resultSet.getInt("product_id"),
                    resultSet.getInt("ingredient_id"),
                    resultSet.getInt("quantity_required"),
                    resultSet.getString("unit")
            );
            list.add(dto);
        }

        return list;
    }


    public String save(ProductIngredient dto) throws SQLException {
        String sql = "INSERT INTO product_ingredient (product_id, ingredient_id, quantity_required, unit) VALUES (?, ?, ?, ?)";

        boolean done = SqlExecute.SqlExecute(sql, dto.getProduct_id(), dto.getIngredient_id(), dto.getQuantity_per_product(), dto.getUnit());

        if (done) {
            return "Product ingredient added successfully!";
        } else {
            return "Failed to add product ingredient!";
        }
    }

    public String update(ProductIngredient productIngredientDto){

        String sql="UPDATE product_ingredient SET product_id=?,ingredient_id=?,quantity_required=?,unit=? WHERE id=?";

        Boolean done=SqlExecute.SqlExecute(sql,productIngredientDto.getProduct_id(),
                productIngredientDto.getIngredient_id(),productIngredientDto.getQuantity_per_product(),productIngredientDto.getUnit(),productIngredientDto.getId());

        if (done) {
            return "Product ingredient updated successfully!";
        } else {
            return "Failed to update product ingredient!";
        }
    }

    public String delete(int id) throws SQLException{

        String sql="DELETE FROM product_ingredient WHERE id=?";

        Boolean done= null;
        try {
            done = SqlExecute.SqlExecute(sql,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (done) {
            return "Product ingredient deleted successfully!";
        } else {
            return "Failed to delete product ingredient!";
        }

    }


}
