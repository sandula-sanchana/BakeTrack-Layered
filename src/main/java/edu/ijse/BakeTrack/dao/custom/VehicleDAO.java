package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.dto.VehicleDto;
import edu.ijse.BakeTrack.entity.Vehicle;

public interface VehicleDAO extends CrudDAO<Vehicle> {

    void getVehicleById(int vehicleId) throws SQLException;

    ArrayList<VehicleDto> getAvailableVehicles(String status) throws SQLException;

    String getLicensePlateById(int vehicleId) throws SQLException;

    Map<String, Integer> getVehicleStatusCount() throws  SQLException;

    boolean updateVehicleStatus(int vehicleID, String status) throws SQLException;

    String updateVehicleStatusToAvailable(int vehicleId) throws SQLException;
}
