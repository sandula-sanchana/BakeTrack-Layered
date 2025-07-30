package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.ijse.BakeTrack.dao.custom.OrderDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.entity.Order;
import edu.ijse.BakeTrack.entity.OrderTrend;
import edu.ijse.BakeTrack.dao.SqlExecute;

public class OrdersDAOImpl implements OrderDAO {

    private Connection connection;

    public OrdersDAOImpl() throws ClassNotFoundException, SQLException {
        this.connection = DBobject.getInstance().getConnection();
    }


    @Override
    public String save(Order order) throws Exception {
        return "";
    }

    public String update(Order orderDto) throws SQLException {
        String updateSql = "UPDATE orders SET customer_id=?, delivery_id=?, order_date=?, total_price=?, status=? WHERE order_id=?";
            int rowsAffected = SqlExecute.SqlExecute(updateSql,orderDto.getCustomerID(),orderDto.getDeliveryID(),orderDto.getOrderDate(),orderDto.getTotalPrice(),orderDto.getStatus(),orderDto.getOrder_id());
            return (rowsAffected > 0 ? "Order updated successfully" : "Failed to update order");
        
    }

    public String delete(int orderId) throws SQLException {
        String deleteSql = "DELETE FROM orders WHERE order_id = ?";
            int rowsAffected = SqlExecute.SqlExecute(deleteSql,orderId);
           return rowsAffected > 0 ? "Order & order_Detail,Payments deleted successfully" : "Failed to delete order";
        }

    public ArrayList<Order> getAll() {
        String query = "SELECT * FROM orders";
        ArrayList<Order> ordersList = new ArrayList<>();

        try {

            ResultSet rs = SqlExecute.SqlExecute(query);

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("delivery_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status")// my database status need to be ENUM , I will fix it later :)
                );
                ordersList.add(order);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return ordersList;
    }

    public String updateOrderStatusToDelivered(int orderId) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        int done=SqlExecute.SqlExecute(sql,"delivered",orderId);
        return  done> 0 ? "OK" : "order status update error";
    }


    @Override
    public int saveOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, delivery_id, order_date, total_price, status) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, order.getCustomerID());
        ps.setNull(2, -1);
        ps.setDate(3, Date.valueOf(order.getOrderDate()));
        ps.setDouble(4, order.getTotalPrice());
        ps.setString(5, order.getStatus());

        if (ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }


    public ArrayList<Order> getOrderByID(int orderID) throws SQLException{
        String query = "SELECT * FROM orders WHERE order_id=?";
        ArrayList<Order> ordersList = new ArrayList<>();

        try {
            ResultSet rs = SqlExecute.SqlExecute(query,orderID);
            if (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("delivery_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status")// my database status need to be ENUM , I will fix it later :)
                );
                ordersList.add(order);
                return ordersList;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    public ArrayList<Order> getOrderByDelID(String delID) {
        String query = "SELECT * FROM orders WHERE delivery_id=?";
        ArrayList<Order> ordersList = new ArrayList<>();

        try {
            ResultSet rs = SqlExecute.SqlExecute(query,delID);

            if (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("delivery_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status")
                );
                ordersList.add(order);
                return ordersList;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    public ArrayList<Order> getAllPendingOrders() {
        String query = "SELECT * FROM orders WHERE status=?";
        ArrayList<Order> ordersList = new ArrayList<>();

        try {
          ResultSet rs=SqlExecute.SqlExecute(query,"pending");

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("delivery_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status")// my database status need to be ENUM , I will fix it later :)
                );
                ordersList.add(order);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return ordersList;
    }


    public boolean updateOrderStatus(int orderId, String newStatus) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        return SqlExecute.SqlExecute(sql, newStatus, orderId);
    }

    public List<OrderTrend> getOrderTrends() throws SQLException {
        String sql = "SELECT order_date, COUNT(*) AS order_count FROM orders GROUP BY order_date ORDER BY order_date";
        ResultSet rs = SqlExecute.SqlExecute(sql);

        List<OrderTrend> trends = new ArrayList<>();
        while (rs.next()) {
            trends.add(new OrderTrend(
                    rs.getString("order_date"),
                    rs.getInt("order_count")
            ));
        }

        return trends;
    }

    @Override
    public boolean updateOrderDelivery(int deliveryID, int orderID) throws SQLException {
        String sql = "UPDATE orders SET delivery_id = ?, status = ? WHERE order_id = ?";
        try {
            int done=SqlExecute.SqlExecute(sql,deliveryID,"in transit",orderID);
            return done > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
