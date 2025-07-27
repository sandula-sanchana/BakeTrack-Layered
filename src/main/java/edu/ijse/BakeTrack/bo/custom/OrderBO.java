package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.dto.OrderDetailDto;
import edu.ijse.BakeTrack.dto.OrderDto;
import edu.ijse.BakeTrack.dto.OrderTrendDto;
import edu.ijse.BakeTrack.tm.IngredientTM;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderBO {

    public String updateOrder(OrderDto orderDto) throws Exception;
    public String deleteOrder(int orderId) throws Exception;
    ArrayList<OrderDto> getAllOrders() throws Exception;
    String placeOrder(OrderDto orderDto, ArrayList<OrderDetailDto> orderDetailDtos) throws SQLException;
    ArrayList<OrderDto> getOrderByID(int orderID) throws SQLException;
    public ArrayList<OrderDto> getOrderByDelID(String delID)throws SQLException;
    ArrayList<OrderDto> getAllPendingOrders()throws SQLException;
    boolean startProductionAndDeductIng(ObservableList<IngredientTM> ingredientTMObservableList, int order_id)throws SQLException;
    List<OrderTrendDto> getOrderTrends() throws SQLException;
}
