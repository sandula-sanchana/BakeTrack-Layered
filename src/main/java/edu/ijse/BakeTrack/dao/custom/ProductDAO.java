package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;
import java.util.List;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.OrderDetail;
import edu.ijse.BakeTrack.entity.Product;

public interface ProductDAO extends CrudDAO<Product> {

    Product getProductDetailsByProductID(int productID) throws SQLException;

    public double getPriceAtOrder(int product_id) throws SQLException;

    public int getQtyByPid(int ProductID) throws SQLException;

    String getProductNameById(int productId) throws SQLException;

    int countProducts() throws SQLException;

    boolean updateQuantitiesAfterOrder(List<OrderDetail> orderDetails) throws SQLException;
}
