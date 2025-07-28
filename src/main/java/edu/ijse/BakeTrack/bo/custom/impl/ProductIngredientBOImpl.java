package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.ProductIngredientBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.ProductIngredientDAO;
import edu.ijse.BakeTrack.dto.ProductIngredientDto;
import edu.ijse.BakeTrack.entity.ProductIngredient;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductIngredientBOImpl implements ProductIngredientBO {

    ProductIngredientDAO productIngredientDAO=(ProductIngredientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT_INGREDIENT);

    public ProductIngredientBOImpl() throws ClassNotFoundException, SQLException {
    }

    @Override
    public ArrayList<ProductIngredientDto> getProductIngredientByPid(int productID) throws SQLException {
        ArrayList<ProductIngredientDto> list = new ArrayList<>();
        for (ProductIngredient p : productIngredientDAO.getProductIngredientByPid(productID)) {
            list.add(new ProductIngredientDto(
                    p.getId(),
                    p.getProduct_id(),
                    p.getIngredient_id(),
                    p.getQuantity_per_product(),
                    p.getUnit()
            ));
        }
        return list;
    }

    @Override
    public ArrayList<ProductIngredientDto> getAll() throws Exception {
        ArrayList<ProductIngredientDto> list = new ArrayList<>();
        for (ProductIngredient p : productIngredientDAO.getAll()) {
            list.add(new ProductIngredientDto(
                    p.getId(),
                    p.getProduct_id(),
                    p.getIngredient_id(),
                    p.getQuantity_per_product(),
                    p.getUnit()
            ));
        }
        return list;
    }


    @Override
    public String addProductIngredient(ProductIngredientDto dto) throws Exception {
        ProductIngredient entity = new ProductIngredient(
                dto.getProduct_id(),
                dto.getIngredient_id(),
                dto.getQuantity_per_product(),
                dto.getUnit()
        );
        return productIngredientDAO.save(entity);
    }

    @Override
    public String updateProductIngredient(ProductIngredientDto dto) throws Exception {
        ProductIngredient entity = new ProductIngredient(
                dto.getId(),
                dto.getProduct_id(),
                dto.getIngredient_id(),
                dto.getQuantity_per_product(),
                dto.getUnit()
        );
        return productIngredientDAO.update(entity);
    }


    @Override
    public String deleteProductIngredient(int id) throws Exception {
        return productIngredientDAO.delete(id);
    }
}
