package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Ingredient;
import edu.ijse.BakeTrack.tm.IngredientTM;
import javafx.collections.ObservableList;

public interface IngredientDAO extends CrudDAO<Ingredient> {
    String getIngredientNameById(int ingredientId) throws SQLException;
   int getStockById(int ingredientId) throws SQLException;
    int countIng() throws SQLException;
    boolean deductIngredientStock(ObservableList<IngredientTM> ingredients) throws SQLException;
}
