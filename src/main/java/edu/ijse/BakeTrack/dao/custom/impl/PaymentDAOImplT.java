package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.dao.custom.PaymentDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.entity.Payment;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentDAOImplT implements PaymentDAO {

    private Connection connection;

    public PaymentDAOImplT() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }


    public String delete(int orderId) throws SQLException {
        String sql = "DELETE FROM payments WHERE order_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Payment deleted successfully";
        } else {
            return "Failed to delete payment";
        }
    }

    @Override
    public String save(Payment payment) throws Exception {
        return "";
    }

    public String update(Payment payment) throws SQLException {
        String sql = "UPDATE payments SET status=?, payment_method = ?, payment_date = ? WHERE order_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, payment.getStatus());
        statement.setString(2, payment.getPaymentMethod());
        statement.setDate(3, Date.valueOf(payment.getPaymentDate()));
        statement.setInt(4, payment.getOrderID());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Payment updated successfully";
        } else {
           return "Failed to update payment";
        }
    }
    public ArrayList<Payment> getPaymentDetailsByOrderId(int orderId) {
        String sql = "SELECT * FROM payments WHERE order_id = ?";
        ArrayList<Payment> paymentsDtos = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Date sqlDate = resultSet.getDate("payment_date");
                LocalDate payDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                Payment paymentsDto = new Payment(
                        resultSet.getInt("payment_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getDouble("price"),
                        resultSet.getString("payment_method"),
                        payDate,
                        resultSet.getString("status")
                );

                paymentsDtos.add(paymentsDto);
                return paymentsDtos;

            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public double getTotRevenue() throws SQLException {
        Double no_sales=0.0;
        String sql = "SELECT SUM(price) as Total_Revenue From payments";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getDouble("total_revenue");
        }
        return no_sales;
    }

    public ArrayList<Payment> getAll() {
        String sql = "SELECT * FROM payments";
        ArrayList<Payment> paymentsList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payment payment = new Payment(
                        resultSet.getInt("payment_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getDouble("price"),
                        resultSet.getString("payment_method"),
                        resultSet.getDate("payment_date").toLocalDate(),
                        resultSet.getString("status")
                );
                paymentsList.add(payment);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return paymentsList;
    }

    public String setPayments(Payment paymentsDto,int vehicle_id){
          connection=DBobject.getInstance().getConnection();


          try {
              PreparedStatement statement= null;
              try {
                  connection.setAutoCommit(false);
                  String PayUpdate="UPDATE payments SET payment_method=?,payment_date=?,status=? WHERE payment_id=?";
                  statement = connection.prepareStatement(PayUpdate);
                  statement.setString(1,paymentsDto.getPaymentMethod());
                  statement.setDate(2,Date.valueOf(paymentsDto.getPaymentDate()));
                  statement.setString(3,paymentsDto.getStatus());
                  statement.setInt(4,paymentsDto.getPayment_id());
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }

              if(statement.executeUpdate()>0){
                  PreparedStatement orderStatement= null;
                  try {
                      String orderStatus="UPDATE orders SET status=? WHERE order_id=?";
                      orderStatement = connection.prepareStatement(orderStatus);
                      orderStatement.setString(1,"delivered");
                      orderStatement.setInt(2,paymentsDto.getOrderID());
                  } catch (SQLException e) {
                      throw new RuntimeException(e);
                  }
                  if(orderStatement.executeUpdate()>0){
                      PreparedStatement vehiStatement= null;
                      try {
                          String vehicleSql="UPDATE vehicle SET status=? WHERE vehicle_id=?";
                          vehiStatement = connection.prepareStatement(vehicleSql);
                          vehiStatement.setString(1,"available");
                          vehiStatement.setInt(2,vehicle_id);
                      } catch (SQLException e) {
                          throw new RuntimeException(e);
                      }

                      if(vehiStatement.executeUpdate()>0){
                             connection.commit();
                             return "set Payment Done";
                         }else {
                             connection.rollback();
                             return "vehicle status error";
                         }

                   }else{
                       connection.rollback();
                       return "order Status Update error";
                   }
              }else{
                 connection.rollback();
                 return "update payment error";
              }

          } catch (Exception e) {
              throw new RuntimeException(e);
          } finally {
              try {
                  connection.setAutoCommit(true);
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }
          }
    }

    public Map<String,Integer> getPaymentCount(){
        String sql="SELECT status,COUNT(*) as count FROM payments GROUP BY status";

        Map<String,Integer> countMap=new HashMap<>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String status = rs.getString("status");
                int count = rs.getInt("count");
                countMap.put(status,count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countMap;

    }

    public boolean insertPendingPayment(int orderId, double price) throws SQLException {
        String sql = "INSERT INTO payments (order_id, price, payment_method, payment_date, status) VALUES (?, ?, ?, ?, ?)";
        return SqlExecute.SqlExecute(sql, orderId, price, null, null, "pending");
    }

}
