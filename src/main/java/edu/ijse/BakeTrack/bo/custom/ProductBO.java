package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.dto.ProductDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductBO {
    String addProduct(ProductDto productDto) throws Exception;

    String deleteProduct(int productID) throws Exception;

    String updateProduct(ProductDto productDto) throws Exception;

    ProductDto getProductDetailsByProductID(int productID) throws SQLException;

    ArrayList<ProductDto> getAllProducts() throws Exception;

    public double getPriceAtOrder(int product_id) throws SQLException;

    public int getQtyByPid(int ProductID) throws SQLException;

    String getProductNameById(int productId) throws SQLException;

    int countProducts() throws SQLException;
}
