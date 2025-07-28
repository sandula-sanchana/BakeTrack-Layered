package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.bo.SuperBO;
import edu.ijse.BakeTrack.dto.SupplierIngredientDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierIngredientBO  extends SuperBO {

    String addSupplierIngredient(SupplierIngredientDto supplierIngredientDto) throws Exception;

    String updateSupplierIngredient(SupplierIngredientDto supplierIngredientDto) throws Exception;

    String deleteSupplierIngredient(int ingredientId, int supplierId) throws SQLException;

    ArrayList<SupplierIngredientDto> getAllSupplierIngredients() throws Exception;
}
