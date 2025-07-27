package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.OrderBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.dao.custom.*;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dto.OrderDetailDto;
import edu.ijse.BakeTrack.dto.OrderDto;
import edu.ijse.BakeTrack.dto.OrderTrendDto;
import edu.ijse.BakeTrack.entity.Order;
import edu.ijse.BakeTrack.entity.OrderDetail;
import edu.ijse.BakeTrack.entity.OrderTrend;
import edu.ijse.BakeTrack.tm.IngredientTM;
import javafx.collections.ObservableList;
import java.util.ArrayList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO=(OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    OrderDetailDAO orderDetailDAO=(OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAIL);
    ProductDAO productDAO=(ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);
    PaymentDAO paymentDAO=(PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    IngredientDAO ingredientDAO=(IngredientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INGREDIENT);
    private Connection connection;

    public OrderBOImpl() throws SQLException, ClassNotFoundException {
        this.connection= DBobject.getInstance().getConnection();
    }

    @Override
    public String updateOrder(OrderDto orderDto) throws Exception {
        Order order = new Order(
                orderDto.getOrder_id(),
                orderDto.getCustomerID(),
                orderDto.getDeliveryID(),
                orderDto.getOrderDate(),
                orderDto.getTotalPrice(),
                orderDto.getStatus()
        );
        return orderDAO.update(order);
    }


    @Override
    public String deleteOrder(int orderId) throws Exception {
        return orderDAO.delete(orderId);
    }

    @Override
    public ArrayList<OrderDto> getAllOrders() throws Exception {
        ArrayList<OrderDto> orderDtos = new ArrayList<>();
        ArrayList<Order> orders = orderDAO.getAll();
        for (Order order : orders) {
            orderDtos.add(new OrderDto(
                    order.getOrder_id(),
                    order.getCustomerID(),
                    order.getDeliveryID(),
                    order.getOrderDate(),
                    order.getTotalPrice(),
                    order.getStatus()
            ));
        }
        return orderDtos;
    }

    @Override
    public String placeOrder(OrderDto orderDto, ArrayList<OrderDetailDto> orderDetail) throws SQLException {

        Order order=new Order(orderDto.getCustomerID(),orderDto.getOrderDate(),orderDto.getTotalPrice(),orderDto.getStatus());
        ArrayList<OrderDetail> orderDetails=new ArrayList<>();
        for (OrderDetailDto orderDetailDto: orderDetail) {
            orderDetails.add(new OrderDetail(orderDetailDto.getOrderID(),orderDetailDto.getProductID(),orderDetailDto.getQuantity(),orderDetailDto.getPriceAtOrder()));
        }

        try{
            connection.setAutoCommit(false);
            int done=orderDAO.saveOrder(order);
            if(done>-1){

               boolean orderDetailsResult=orderDetailDAO.saveOrderDetails(orderDetails,done);

                if (orderDetailsResult){
                    boolean quantitySaved=productDAO.updateQuantitiesAfterOrder(orderDetails);
                    if(quantitySaved){

                        boolean paymentDone=paymentDAO.insertPendingPayment(done,orderDto.getTotalPrice());

                        if(paymentDone){
                            connection.commit();
                            return "setOrder done, payment pending";
                        }else {
                            connection.rollback();
                            return "payment table set error";
                        }
                    }else{
                        connection.rollback();
                        return "Quantity update failed";
                    }
                }else{
                    connection.rollback();
                    return  "order detail update error";
                }
            }
            else {
                connection.rollback();
                return "generated key not found";
            }


        } catch (Exception e) {
            connection.rollback();
            throw new RuntimeException(e);
        }
        finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public ArrayList<OrderDto> getOrderByID(int orderID) throws SQLException {
        ArrayList<OrderDto> dtos = new ArrayList<>();
        ArrayList<Order> orders = orderDAO.getOrderByID(orderID);
        if (orders != null) {
            for (Order order : orders) {
                dtos.add(new OrderDto(
                        order.getOrder_id(),
                        order.getCustomerID(),
                        order.getDeliveryID(),
                        order.getOrderDate(),
                        order.getTotalPrice(),
                        order.getStatus()
                ));
            }
        }
        return dtos;
    }


    @Override
    public ArrayList<OrderDto> getOrderByDelID(String delID) throws SQLException {
        ArrayList<OrderDto> dtos = new ArrayList<>();
        ArrayList<Order> orders = orderDAO.getOrderByDelID(delID);
        if (orders != null) {
            for (Order order : orders) {
                dtos.add(new OrderDto(
                        order.getOrder_id(),
                        order.getCustomerID(),
                        order.getDeliveryID(),
                        order.getOrderDate(),
                        order.getTotalPrice(),
                        order.getStatus()
                ));
            }
        }
        return dtos;
    }

    @Override
    public ArrayList<OrderDto> getAllPendingOrders() throws SQLException {
        ArrayList<OrderDto> dtos = new ArrayList<>();
        ArrayList<Order> orders = orderDAO.getAllPendingOrders();
        for (Order order : orders) {
            dtos.add(new OrderDto(
                    order.getOrder_id(),
                    order.getCustomerID(),
                    order.getDeliveryID(),
                    order.getOrderDate(),
                    order.getTotalPrice(),
                    order.getStatus()
            ));
        }
        return dtos;
    }

    @Override
    public boolean startProductionAndDeductIng(ObservableList<IngredientTM> ingredientTMObservableList, int order_id) throws SQLException {
        try {
            connection.setAutoCommit(false);

            boolean statusUpdated = orderDAO.updateOrderStatus(order_id, "processing");
            if (!statusUpdated) {
                connection.rollback();
                return false;
            }

            boolean ingredientsUpdated = ingredientDAO.deductIngredientStock(ingredientTMObservableList);
            if (!ingredientsUpdated) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed", ex);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to reset auto-commit", e);
            }
        }
    }

    @Override
    public List<OrderTrendDto> getOrderTrends() throws SQLException {
       List<OrderTrend> orderTrends = orderDAO.getOrderTrends();
       List<OrderTrendDto> orderTrendDtos=new ArrayList<>();
       for (OrderTrend orderTrend : orderTrends) {
           orderTrendDtos.add(new OrderTrendDto(orderTrend.getDate(),orderTrend.getCount()));
       }
       return orderTrendDtos;
    }
}
