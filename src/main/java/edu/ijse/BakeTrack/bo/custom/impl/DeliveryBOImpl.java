package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.DeliveryBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.DeliveryDAO;
import edu.ijse.BakeTrack.dao.custom.OrderDAO;
import edu.ijse.BakeTrack.dao.custom.VehicleDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dto.DeliveryDto;
import edu.ijse.BakeTrack.entity.Delivery;

import java.sql.*;
import java.util.ArrayList;

public class DeliveryBOImpl implements DeliveryBO {

    Connection connection;

    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DELIVERY);
    OrderDAO orderDAO=(OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    VehicleDAO vehicleDAO=(VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);

    public DeliveryBOImpl() throws SQLException, ClassNotFoundException {
        this.connection= DBobject.getInstance().getConnection();
    }

    @Override
    public String deleteDelivery(int deliveryId) throws Exception {
        return deliveryDAO.delete(deliveryId);
    }

    @Override
    public String updateDelivery(DeliveryDto deliveryDto) throws Exception {
        return deliveryDAO.update(new Delivery(deliveryDto.getDelivery_id(),deliveryDto.getVehicleID(),deliveryDto.getEmployee_id(),deliveryDto.getDeliveryDate(),deliveryDto.getDeliveryArea()));
    }

    @Override
    public int getVehicleIDbyDelID(int delivery_id) throws SQLException {
        return deliveryDAO.getVehicleIDbyDelID(delivery_id);
    }

    @Override
    public ArrayList<DeliveryDto> getAllDelivery() throws Exception {
        ArrayList<DeliveryDto> deliveryDtos = new ArrayList<>();
        ArrayList<Delivery> deliveries=deliveryDAO.getAll();
        for (Delivery delivery : deliveries) {
            deliveryDtos.add(new DeliveryDto(delivery.getDelivery_id(),delivery.getVehicleID(),delivery.getEmployee_id(),delivery.getDeliveryDate(),delivery.getDeliveryArea()));
        }
        return deliveryDtos;
    }

    @Override
    public ArrayList<DeliveryDto> getUnassignedDeliveries() throws SQLException {
        ArrayList<DeliveryDto> deliveryDtos = new ArrayList<>();
        ArrayList<Delivery> deliveries=deliveryDAO.getUnassignedDeliveries();
        for (Delivery delivery : deliveries) {
            deliveryDtos.add(new DeliveryDto(delivery.getDelivery_id(),delivery.getVehicleID(),delivery.getEmployee_id(),delivery.getDeliveryDate(),delivery.getDeliveryArea()));
        }
        return deliveryDtos;
    }

    @Override
    public String setDelivery(DeliveryDto deliveryDto, String orderID) throws SQLException {
        try {
            connection.setAutoCommit(false);
            Delivery delivery=new Delivery(deliveryDto.getVehicleID(),deliveryDto.getDeliveryDate(),deliveryDto.getDeliveryArea());
            int deliveryID = deliveryDAO.addDelivery(delivery);
            if (deliveryID == -1) {
                connection.rollback();
                return "Failed to insert into delivery table";
            }

            boolean orderUpdated = orderDAO.updateOrderDelivery(deliveryID, Integer.parseInt(orderID));
            if (!orderUpdated) {
                connection.rollback();
                return "Failed to update order with delivery ID";
            }

            boolean vehicleUpdated = vehicleDAO.updateVehicleStatus(deliveryDto.getVehicleID(), "not available");
            if (!vehicleUpdated) {
                connection.rollback();
                return "Failed to update vehicle status";
            }

            connection.commit();
            return "setDelivery Successfully done";

        } catch (Exception e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public String setEmployeeForDelivery(int del_id, int emp_id) throws SQLException {
        return deliveryDAO.setEmployeeForDelivery(del_id,emp_id);
    }
}
