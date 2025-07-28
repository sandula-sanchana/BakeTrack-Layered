package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.dto.ProductIngredientDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductIngredientBO {
    ArrayList<ProductIngredientDto> getProductIngredientByPid(int productID) throws SQLException;

    ArrayList<ProductIngredientDto> getAll() throws Exception;

    String addProductIngredient(ProductIngredientDto dto) throws Exception;

    String updateProductIngredient(ProductIngredientDto productIngredientDto) throws Exception;

    String deleteProductIngredient(int id) throws Exception;
}
