package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.IngredientBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.IngredientDAO;
import edu.ijse.BakeTrack.dto.IngredientDto;
import edu.ijse.BakeTrack.entity.Ingredient;

import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientBOImpl implements IngredientBO {

    IngredientDAO ingredientDAO=(IngredientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INGREDIENT);

    public IngredientBOImpl() throws SQLException, ClassNotFoundException {
    }

    public String addIngredient(IngredientDto ingredientDto) throws Exception {
        return ingredientDAO.save(new Ingredient(
                ingredientDto.getName(),
                ingredientDto.getStock_amount(),
                ingredientDto.getUnit(),
                ingredientDto.getBuying_price(),
                ingredientDto.getExpire_date()
        ));
    }

    @Override
    public String deleteIngredient(int ingredientId) throws Exception {
        return ingredientDAO.delete(ingredientId);
    }

    @Override
    public String updateIngredient(IngredientDto ingredientDto) throws Exception {
        return ingredientDAO.update(new Ingredient(
                ingredientDto.getIngredient_id(),
                ingredientDto.getName(),
                ingredientDto.getStock_amount(),
                ingredientDto.getUnit(),
                ingredientDto.getBuying_price(),
                ingredientDto.getExpire_date()
        ));
    }

    @Override
    public ArrayList<IngredientDto> getAllIngredients() throws Exception {
        ArrayList<Ingredient> ingredients = ingredientDAO.getAll();
        ArrayList<IngredientDto> ingredientDtos = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            ingredientDtos.add(new IngredientDto(
                    ingredient.getIngredient_id(),
                    ingredient.getName(),
                    ingredient.getStock_amount(),
                    ingredient.getUnit(),
                    ingredient.getBuying_price(),
                    ingredient.getExpire_date()
            ));
        }
        return ingredientDtos;
    }

    @Override
    public String getIngredientNameById(int ingredientId) throws SQLException {
        return ingredientDAO.getIngredientNameById(ingredientId);
    }

    @Override
    public int getStockById(int ingredientId) throws SQLException {
        return ingredientDAO.getStockById(ingredientId);
    }

    @Override
    public int countIng() throws SQLException {
        return ingredientDAO.countIng();
    }
}
