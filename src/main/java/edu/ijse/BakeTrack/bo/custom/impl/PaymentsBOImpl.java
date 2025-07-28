package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.PaymentsBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.OrderDAO;
import edu.ijse.BakeTrack.dao.custom.PaymentDAO;
import edu.ijse.BakeTrack.dao.custom.VehicleDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dto.PaymentsDto;
import edu.ijse.BakeTrack.entity.Payment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentsBOImpl implements PaymentsBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);
    Connection connection;


    public PaymentsBOImpl() throws SQLException, ClassNotFoundException {
        this.connection= DBobject.getInstance().getConnection();
    }

    @Override
    public String deletePayment(int orderId) throws Exception {
        return paymentDAO.delete(orderId);
    }

    @Override
    public String updatePayment(PaymentsDto payment) throws Exception {
        Payment entity = new Payment(
                payment.getPayment_id(),
                payment.getOrderID(),
                payment.getPrice(),
                payment.getPaymentMethod(),
                payment.getPaymentDate(),
                payment.getStatus()
        );
        return paymentDAO.update(entity);
    }

    @Override
    public ArrayList<PaymentsDto> getPaymentDetailsByOrderId(int orderId) throws SQLException {
        ArrayList<PaymentsDto> list = new ArrayList<>();
        List<Payment> payments = paymentDAO.getPaymentDetailsByOrderId(orderId);
        if (payments != null) {
            for (Payment p : payments) {
                list.add(new PaymentsDto(
                        p.getPayment_id(),
                        p.getOrderID(),
                        p.getPrice(),
                        p.getPaymentMethod(),
                        p.getPaymentDate(),
                        p.getStatus()
                ));
            }
        }
        return list;
    }

    @Override
    public double getTotRevenue() throws SQLException {
        return paymentDAO.getTotRevenue();
    }

    @Override
    public ArrayList<PaymentsDto> getAllPayments() throws Exception {
        ArrayList<PaymentsDto> list = new ArrayList<>();
        for (Payment p : paymentDAO.getAll()) {
            list.add(new PaymentsDto(
                    p.getPayment_id(),
                    p.getOrderID(),
                    p.getPrice(),
                    p.getPaymentMethod(),
                    p.getPaymentDate(),
                    p.getStatus()
            ));
        }
        return list;
    }

    @Override
    public String setPayments(PaymentsDto dto, int vehicle_id) throws SQLException {
        Connection connection = DBobject.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            Payment entity = new Payment(
                    dto.getPayment_id(),
                    dto.getOrderID(),
                    dto.getPrice(),
                    dto.getPaymentMethod(),
                    dto.getPaymentDate(),
                    dto.getStatus()
            );

            if (!paymentDAO.updatePaymentStatus(entity).equals("OK")) {
                connection.rollback();
                return "update payment error";
            }

            if (!orderDAO.updateOrderStatusToDelivered(entity.getOrderID()).equals("OK")) {
                connection.rollback();
                return "order status update error";
            }

            if (!vehicleDAO.updateVehicleStatusToAvailable(vehicle_id).equals("OK")) {
                connection.rollback();
                return "vehicle status update error";
            }

            connection.commit();
            return "set Payment Done";

        } catch (Exception e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }


    @Override
    public Map<String, Integer> getPaymentCount() throws SQLException {
        return paymentDAO.getPaymentCount();
    }
}
