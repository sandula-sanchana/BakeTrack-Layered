package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Delivery;

public interface DeliveryDAO extends CrudDAO<Delivery> {

    public int getVehicleIDbyDelID(int delivery_id) throws SQLException;

    ArrayList<Delivery> getUnassignedDeliveries() throws SQLException;

    public String setDelivery(Delivery deliveryDto,String orderID) throws SQLException;

    public String setEmployeeForDelivery(int del_id,int emp_id) throws SQLException;
}
