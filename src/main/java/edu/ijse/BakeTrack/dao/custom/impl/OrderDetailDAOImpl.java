package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.dao.custom.OrderDetailDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dto.OrderDetailDto;
import edu.ijse.BakeTrack.entity.OrderDetail;

public class OrderDetailDAOImpl implements OrderDetailDAO {



    public OrderDetailDAOImpl() throws ClassNotFoundException, SQLException {

    }

    public String save(OrderDetail orderDetailDto) throws SQLException {
        String sql = "INSERT INTO order_details (product_id, order_id, quantity, price_at_order) VALUES (?, ?, ?, ?)";
        int rowsAffected = SqlExecute.SqlExecute(sql, orderDetailDto.getProductID(), orderDetailDto.getOrderID(), orderDetailDto.getQuantity(), orderDetailDto.getPriceAtOrder());
        return (rowsAffected > 0 )? "Order detail added successfully" : "Failed to add order detail";
    }

    @Override
    public String update(OrderDetail orderDetail) throws Exception {
        return "";
    }

    @Override
    public String delete(int t) throws Exception {
        return "";
    }


    public ArrayList<OrderDetailDto> getOrderDetailsByOrderID(int order_id) throws SQLException {
        ArrayList<OrderDetailDto> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM order_detail WHERE order_id=?";
        ResultSet resultSet = SqlExecute.SqlExecute(sql,order_id);

        while (resultSet.next()) {
            int productId = resultSet.getInt("product_id");
            int order_id_selected=resultSet.getInt("order_id");
            int quantity = resultSet.getInt("quantity");
            double priceAtOrder = resultSet.getDouble("price_at_order");

            OrderDetailDto orderDetail = new OrderDetailDto(order_id_selected,productId, quantity, priceAtOrder);
            orderDetailsList.add(orderDetail);
        }

        return orderDetailsList;
    }

    @Override
    public boolean saveOrderDetails(List<OrderDetail> orderDetails, int orderId) throws SQLException {
        String sql = "INSERT INTO order_detail (product_id, order_id, quantity, price_at_order) VALUES (?, ?, ?, ?)";
        for (OrderDetail dto : orderDetails) {
            boolean success = SqlExecute.SqlExecute(sql,
                    dto.getProductID(),
                    orderId,
                    dto.getQuantity(),
                    dto.getPriceAtOrder()
            );
            if (!success) return false;
        }
        return true;
    }

    public ArrayList<OrderDetail> getAll() {
        String sql = "SELECT * FROM order_detail";
        ArrayList<OrderDetail> orderDetailsList = new ArrayList<>();

        try {

            ResultSet resultSet = SqlExecute.SqlExecute(sql);

            while (resultSet.next()) {
                orderDetailsList.add(new OrderDetail(resultSet.getInt("product_id"),
                        resultSet.getInt("order_id"),

                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price_at_order")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return orderDetailsList;
    }

}
