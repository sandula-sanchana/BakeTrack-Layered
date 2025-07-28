package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.ProductBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.ProductDAO;
import edu.ijse.BakeTrack.dto.ProductDto;
import edu.ijse.BakeTrack.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBOImpl implements ProductBO {

    private ProductDAO productDAO=(ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);

    public ProductBOImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public String addProduct(ProductDto dto) throws Exception {
        Product entity = new Product(
                dto.getName(),
                dto.getCategory(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getDescription()
        );
        return productDAO.save(entity);
    }

    @Override
    public String deleteProduct(int productID) throws Exception {
        return productDAO.delete(productID);
    }

    @Override
    public String updateProduct(ProductDto dto) throws Exception {
        Product entity = new Product(
                dto.getProduct_id(),
                dto.getName(),
                dto.getCategory(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getDescription()
        );
        return productDAO.update(entity);
    }

    @Override
    public ProductDto getProductDetailsByProductID(int productID) throws SQLException {
        Product p = productDAO.getProductDetailsByProductID(productID);
        if (p == null) return null;
        return new ProductDto(
                p.getProduct_id(),
                p.getName(),
                p.getCategory(),
                p.getPrice(),
                p.getQuantity(),
                p.getDescription()
        );
    }

    @Override
    public ArrayList<ProductDto> getAllProducts() throws Exception {
        ArrayList<ProductDto> list = new ArrayList<>();
        for (Product p : productDAO.getAll()) {
            list.add(new ProductDto(
                    p.getProduct_id(),
                    p.getName(),
                    p.getCategory(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getDescription()
            ));
        }
        return list;
    }

    @Override
    public double getPriceAtOrder(int product_id) throws SQLException {
        return productDAO.getPriceAtOrder(product_id);
    }

    @Override
    public int getQtyByPid(int productID) throws SQLException {
        return productDAO.getQtyByPid(productID);
    }

    @Override
    public String getProductNameById(int productId) throws SQLException {
        return productDAO.getProductNameById(productId);
    }

    @Override
    public int countProducts() throws SQLException {
        return productDAO.countProducts();
    }
}
