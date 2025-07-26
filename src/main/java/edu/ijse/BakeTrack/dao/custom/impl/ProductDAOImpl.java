package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.BakeTrack.dao.custom.ProductDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.entity.Product;

public class ProductDAOImpl implements ProductDAO {
    private Connection connection;

    public ProductDAOImpl() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public String save(Product productDto) throws SQLException {
        String sql = "INSERT INTO product (name, category, price,total_quantity, description) VALUES (?, ?, ?, ?,?)";
        Boolean done=SqlExecute.SqlExecute(sql,productDto.getName(),productDto.getCategory(),productDto.getPrice(),productDto.getQuantity(),productDto.getDescription());

        if (done) {
            return "Product added successfully";
        } else {
            return "Failed to add product";
        }
    }

    public String delete(int productID) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id=?";
        Boolean done=SqlExecute.SqlExecute(sql,productID);

        if (done) {
            return "Product deleted successfully";
        } else {
            return "Failed to delete product";
        }
    }

    public int countProducts() throws SQLException {
        ResultSet rs= null;
        try {
            String sql="SELECT COUNT(*) as count FROM product";
            rs = SqlExecute.SqlExecute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        while (rs.next()){
            return rs.getInt("count");
        }
        return -1;
    }

    public String update(Product productDto) throws SQLException {
        String sql = "UPDATE product SET name=?, category = ?, price = ?,total_quantity=?, description = ? WHERE product_id = ?";


        Boolean done=SqlExecute.SqlExecute(sql,productDto.getName(),productDto.getCategory(),productDto.getPrice(),
                productDto.getQuantity(),productDto.getDescription(),productDto.getProduct_id());


        if (done) {
            return "Product updated successfully";
        } else {
            return "Failed to update product";
        }
    }

    public Product getProductDetailsByProductID(int productID) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,productID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Product(resultSet.getInt("product_id"),resultSet.getString("name"), resultSet.getString("category"),resultSet.getDouble("price"),resultSet.getInt("total_quantity"),
                    resultSet.getString("description"));
        } else {
           return  null;
        }
    }

    public int getQtyByPid(int ProductID) throws SQLException {
        String sql = "SELECT total_quantity FROM product WHERE product_id=?";
        ResultSet resultSet= null;
        try {
            resultSet = SqlExecute.SqlExecute(sql,ProductID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultSet.getInt("total_quantity");

    }

    public ArrayList<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM product";
        ArrayList<Product> productList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("total_quantity"),
                        resultSet.getString("description")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return productList;
    }

    public double getPriceAtOrder(int product_id) throws SQLException {
        String sql="SELECT price FROM product WHERE product_id=?";

        try {
            PreparedStatement statement=connection.prepareStatement(sql);

            statement.setInt(1,product_id);

            ResultSet resultSet=statement.executeQuery();

            if (resultSet.next()){
                return resultSet.getDouble("price");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    public String getProductNameById(int productId) throws SQLException {
        String sql = "SELECT name FROM product WHERE product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    return null;
                }
            }
        }
    }

}
