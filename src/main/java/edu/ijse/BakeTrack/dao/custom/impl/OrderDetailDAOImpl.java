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

    private Connection connection;

    public OrderDetailDAOImpl() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public String save(OrderDetail orderDetailDto) throws SQLException {
        String sql = "INSERT INTO order_details (product_id, order_id, quantity, price_at_order) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderDetailDto.getProductID());
        statement.setInt(2, orderDetailDto.getOrderID());
        statement.setInt(3, orderDetailDto.getQuantity());
        statement.setDouble(4, orderDetailDto.getPriceAtOrder());

        int rowsAffected = statement.executeUpdate();
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
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, order_id);
        ResultSet resultSet = statement.executeQuery();

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
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

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
