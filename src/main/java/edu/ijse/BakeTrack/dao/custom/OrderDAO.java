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

    String placeOrder(Order orderDto, ArrayList<OrderDetail> orderDetailDtos) throws SQLException;
    ArrayList<Order> getOrderByID(int orderID) throws SQLException;
    public ArrayList<Order> getOrderByDelID(String delID)throws SQLException;
    ArrayList<Order> getAllPendingOrders()throws SQLException;
    boolean startProductionAndDeductIng(ObservableList<IngredientTM> ingredientTMObservableList, int order_id)throws SQLException;
    List<OrderTrend> getOrderTrends() throws SQLException;
}
