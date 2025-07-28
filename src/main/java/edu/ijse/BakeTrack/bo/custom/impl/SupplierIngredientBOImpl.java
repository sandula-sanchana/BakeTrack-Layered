package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.SupplierIngredientBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.SupplierIngredientDAO;
import edu.ijse.BakeTrack.dto.SupplierIngredientDto;
import edu.ijse.BakeTrack.entity.SupplierIngredient;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierIngredientBOImpl implements SupplierIngredientBO {

    SupplierIngredientDAO supplierIngredientDAO = (SupplierIngredientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER_INGREDIENT);

    public SupplierIngredientBOImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public String addSupplierIngredient(SupplierIngredientDto dto) throws Exception {
        SupplierIngredient entity = new SupplierIngredient(
                dto.getSupplier_id(),
                dto.getIngredient_id(),
                dto.getPrice_per_unit(),
                dto.getUnit(),
                dto.getOrder_date()
        );
        return supplierIngredientDAO.save(entity);
    }

    @Override
    public String updateSupplierIngredient(SupplierIngredientDto dto) throws Exception {
        SupplierIngredient entity = new SupplierIngredient(
                dto.getSupplier_id(),
                dto.getIngredient_id(),
                dto.getPrice_per_unit(),
                dto.getUnit(),
                dto.getOrder_date()
        );
        return supplierIngredientDAO.update(entity);
    }

    @Override
    public String deleteSupplierIngredient(int ingredientId, int supplierId) throws SQLException {
        return supplierIngredientDAO.deleteSupplierIngredient(ingredientId, supplierId);
    }

    @Override
    public ArrayList<SupplierIngredientDto> getAllSupplierIngredients() throws Exception {
        ArrayList<SupplierIngredientDto> list = new ArrayList<>();
        for (SupplierIngredient entity : supplierIngredientDAO.getAll()) {
            list.add(new SupplierIngredientDto(
                    entity.getSupplier_id(),
                    entity.getIngredient_id(),
                    entity.getPrice_per_unit(),
                    entity.getUnit(),
                    entity.getOrder_date()
            ));
        }
        return list;
    }
}
