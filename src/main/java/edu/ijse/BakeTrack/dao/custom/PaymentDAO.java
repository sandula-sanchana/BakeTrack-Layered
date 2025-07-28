package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Payment;

public interface PaymentDAO extends CrudDAO<Payment> {
    ArrayList<Payment> getPaymentDetailsByOrderId(int orderId) throws SQLException;
    double getTotRevenue() throws SQLException;
    public String setPayments(Payment paymentsDto,int vehicle_id)throws SQLException;
    Map<String,Integer> getPaymentCount() throws SQLException;
    boolean insertPendingPayment(int orderId, double price) throws SQLException;
    String updatePaymentStatus(Payment payment) throws SQLException;
}
