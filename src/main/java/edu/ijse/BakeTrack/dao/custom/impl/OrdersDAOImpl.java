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

        PreparedStatement statement = connection.prepareStatement(updateSql) ;
            statement.setInt(1, orderDto.getCustomerID());
            statement.setInt(2, orderDto.getDeliveryID());
            statement.setDate(3, Date.valueOf(orderDto.getOrderDate()));
            statement.setDouble(4, orderDto.getTotalPrice());
            statement.setString(5, orderDto.getStatus());
            statement.setInt(6, orderDto.getOrder_id());

            int rowsAffected = statement.executeUpdate();
            return (rowsAffected > 0 ? "Order updated successfully" : "Failed to update order");
        
    }

    public String delete(int orderId) throws SQLException {
        String deleteSql = "DELETE FROM orders WHERE order_id = ?";

        PreparedStatement statement = connection.prepareStatement(deleteSql);
            statement.setInt(1, orderId);

            int rowsAffected = statement.executeUpdate();
           return rowsAffected > 0 ? "Order & order_Detail,Payments deleted successfully" : "Failed to delete order";
        }

    public ArrayList<Order> getAll() {
        String query = "SELECT * FROM orders";
        ArrayList<Order> ordersList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

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
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,orderID);

            ResultSet rs = statement.executeQuery();

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
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,Integer.parseInt(delID));

            ResultSet rs = statement.executeQuery();

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
        Connection con = DBobject.getInstance().getConnection();
        String sql = "SELECT order_date, COUNT(*) AS order_count FROM orders GROUP BY order_date ORDER BY order_date";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

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
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, deliveryID);
            ps.setString(2, "in transit");
            ps.setInt(3, orderID);
            return ps.executeUpdate() > 0;
        }
    }


}
