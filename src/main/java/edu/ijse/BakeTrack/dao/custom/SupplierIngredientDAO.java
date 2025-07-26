package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.SupplierIngredient;

public interface SupplierIngredientDAO extends CrudDAO<SupplierIngredient> {
    String deleteSupplierIngredient(int ingredientId, int supplierId) throws SQLException;

    void getSupplierIngredientByID(int ingredientId, int supplierId) throws SQLException;

}
