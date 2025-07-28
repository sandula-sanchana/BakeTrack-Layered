package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Order;
import edu.ijse.BakeTrack.entity.OrderDetail;
import edu.ijse.BakeTrack.entity.OrderTrend;
import edu.ijse.BakeTrack.tm.IngredientTM;
import javafx.collections.ObservableList;

public interface OrderDAO extends CrudDAO<Order> {

    int saveOrder(Order order) throws SQLException;
    ArrayList<Order> getOrderByID(int orderID) throws SQLException;
    public ArrayList<Order> getOrderByDelID(String delID)throws SQLException;
    ArrayList<Order> getAllPendingOrders()throws SQLException;
    List<OrderTrend> getOrderTrends() throws SQLException;
    boolean updateOrderDelivery(int deliveryID, int orderID) throws SQLException;
    boolean updateOrderStatus(int orderId, String newStatus) throws SQLException;
    String updateOrderStatusToDelivered(int orderId) throws SQLException;
}
