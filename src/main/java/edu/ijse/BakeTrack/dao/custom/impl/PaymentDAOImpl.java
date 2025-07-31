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

public class PaymentDAOImpl implements PaymentDAO {



    public PaymentDAOImpl() throws ClassNotFoundException, SQLException {

    }


    public String delete(int orderId) throws SQLException {
        String sql = "DELETE FROM payments WHERE order_id = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql,orderId);
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
        int rowsAffected = SqlExecute.SqlExecute(sql,payment.getStatus(),payment.getPaymentMethod(),Date.valueOf(payment.getPaymentDate()),payment.getOrderID());
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
            ResultSet resultSet = SqlExecute.SqlExecute(sql,orderId);
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
        ResultSet resultSet = SqlExecute.SqlExecute(sql);

        if (resultSet.next()) {
            return resultSet.getDouble("total_revenue");
        }
        return no_sales;
    }

    public ArrayList<Payment> getAll() {
        String sql = "SELECT * FROM payments";
        ArrayList<Payment> paymentsList = new ArrayList<>();

        try {

            ResultSet resultSet = SqlExecute.SqlExecute(sql);

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

    public String updatePaymentStatus(Payment payment) throws SQLException {
        String sql = "UPDATE payments SET payment_method=?, payment_date=?, status=? WHERE payment_id=?";
        int done=SqlExecute.SqlExecute(sql,payment.getPaymentMethod(),Date.valueOf(payment.getPaymentDate()),payment.getStatus(),payment.getPayment_id());
        return done > 0 ? "OK" : "update payment error";
    }

    public Map<String,Integer> getPaymentCount(){
        String sql="SELECT status,COUNT(*) as count FROM payments GROUP BY status";

        Map<String,Integer> countMap=new HashMap<>();

        try {
            ResultSet rs =SqlExecute.SqlExecute(sql);
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
