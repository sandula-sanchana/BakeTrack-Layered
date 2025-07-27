package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.dto.DeliveryDto;
import edu.ijse.BakeTrack.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryBO {


    String deleteDelivery(int deliveryId) throws Exception;

    String updateDelivery( DeliveryDto deliveryDto) throws Exception;

    public int getVehicleIDbyDelID(int delivery_id) throws SQLException;

    ArrayList<DeliveryDto> getAllDelivery() throws Exception;

    ArrayList<DeliveryDto> getUnassignedDeliveries() throws SQLException;

    public String setDelivery(DeliveryDto deliveryDto, String orderID) throws SQLException;

    public String setEmployeeForDelivery(int del_id,int emp_id) throws SQLException;
}
