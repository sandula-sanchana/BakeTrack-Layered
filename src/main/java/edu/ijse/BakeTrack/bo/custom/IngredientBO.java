package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.dto.IngredientDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IngredientBO {
    String addIngredient(IngredientDto ingredientDto) throws Exception;
    String deleteIngredient(int ingredientId) throws Exception;
    String updateIngredient( IngredientDto ingredientDto) throws Exception;
    ArrayList<IngredientDto> getAllIngredients() throws Exception;
    String getIngredientNameById(int ingredientId) throws SQLException;
    int getStockById(int ingredientId) throws SQLException;
    int countIng() throws SQLException;
}
