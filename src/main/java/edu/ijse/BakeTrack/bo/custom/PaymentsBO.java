package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.bo.SuperBO;
import edu.ijse.BakeTrack.dto.PaymentsDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface PaymentsBO  extends SuperBO {

    String deletePayment(int orderId) throws Exception;
    String updatePayment(PaymentsDto payment) throws Exception;
    ArrayList<PaymentsDto> getPaymentDetailsByOrderId(int orderId) throws SQLException;
    double getTotRevenue() throws SQLException;
    ArrayList<PaymentsDto> getAllPayments() throws Exception;
    public String setPayments(PaymentsDto paymentsDto,int vehicle_id)throws SQLException;
    Map<String,Integer> getPaymentCount() throws SQLException;
}
