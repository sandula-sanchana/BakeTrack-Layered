package edu.ijse.BakeTrack.dao.custom;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.ProductIngredient;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductIngredientDAO extends CrudDAO<ProductIngredient> {

    ArrayList<ProductIngredient> getProductIngredientByPid(int productID) throws SQLException;
}
