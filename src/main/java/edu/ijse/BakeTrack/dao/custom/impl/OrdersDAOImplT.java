package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.ijse.BakeTrack.dao.custom.OrderDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.entity.Order;
import edu.ijse.BakeTrack.entity.OrderDetail;
import edu.ijse.BakeTrack.entity.OrderTrend;
import edu.ijse.BakeTrack.tm.IngredientTM;
import edu.ijse.BakeTrack.dao.SqlExecute;
import javafx.collections.ObservableList;

public class OrdersDAOImplT implements OrderDAO {

    private Connection connection;

    public OrdersDAOImplT() throws ClassNotFoundException, SQLException {
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


    public String placeOrder(Order orderDto, ArrayList<OrderDetail> orderDetail) throws SQLException {

        connection=DBobject.getInstance().getConnection();

           try{
               connection.setAutoCommit(false);

               String addSql = "INSERT INTO orders (customer_id,delivery_id,order_date,total_price,status) VALUES (?,?,?,?,?)";
               PreparedStatement statement = connection.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);
               statement.setInt(1, orderDto.getCustomerID());
               statement.setNull(2,-1);
               statement.setDate(3, Date.valueOf(orderDto.getOrderDate()));
               statement.setDouble(4, orderDto.getTotalPrice());
               statement.setString(5, orderDto.getStatus());
               statement.executeUpdate();

               ResultSet resultSet=statement.getGeneratedKeys();
               int orderId=-1;
               if(resultSet.next()){
                       orderId=resultSet.getInt(1);
                   String orderDetailsql = "INSERT INTO order_detail (product_id, order_id, quantity, price_at_order) VALUES (?, ?, ?, ?)";
                   boolean orderDetailsResult=true;
                   for(OrderDetail orderDetailDto : orderDetail) {
                       Boolean done=SqlExecute.SqlExecute(orderDetailsql,orderDetailDto.getProductID(), orderId,
                               orderDetailDto.getQuantity(),orderDetailDto.getPriceAtOrder());
                       if (!done){
                           orderDetailsResult=false;
                       }
                   }

                   if (orderDetailsResult){
                       String Quantitysql = "UPDATE product SET total_quantity=(total_quantity-?) WHERE product_id = ?";
                       boolean quantitySaved=true;
                       for (OrderDetail orderDetailDto: orderDetail) {
                           Boolean UpdateDone=SqlExecute.SqlExecute(Quantitysql,orderDetailDto.getQuantity(),orderDetailDto.getProductID());
                         if(!UpdateDone){
                             quantitySaved=false;
                         }
                       }
                       if(quantitySaved){
                             String paymentSql="INSERT INTO payments (order_id,price,payment_method,payment_date,status) VALUES (?,?,?,?,?)";
                             Boolean paymentDone=SqlExecute.SqlExecute(paymentSql,orderId,orderDto.getTotalPrice(),null,null,"pending");

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

    public boolean startProductionAndDeductIng(ObservableList<IngredientTM> ingredientTMObservableList, int order_id){

        try {
            connection.setAutoCommit(false);
            String sql="UPDATE orders SET status=? WHERE order_id=?";

            Boolean done=SqlExecute.SqlExecute(sql,"processing",order_id);

            if(done){

                String ingSql="UPDATE ingredient SET stock_amount=(stock_amount-?) WHERE ingredient_id=?";


                for (IngredientTM ingredientTM: ingredientTMObservableList){
                    int ingredient_id=ingredientTM.getIngredient_id();
                    int needed_stock=ingredientTM.getTotal_amount_need();

                    Boolean doneUpdate=SqlExecute.SqlExecute(ingSql,needed_stock,ingredient_id);

                    if(!doneUpdate){
                        connection.rollback();
                        return false;
                    }
                }
                     connection.commit();
                    return true;

            }else {
                connection.rollback();
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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


}
