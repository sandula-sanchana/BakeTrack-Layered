package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.bo.SuperBO;
import edu.ijse.BakeTrack.dto.VehicleDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface VehicleBO  extends SuperBO {

    String addVehicle(VehicleDto vehicleDto) throws Exception;

    String deleteVehicle(int vehicleId) throws Exception;

    String updateVehicle(VehicleDto vehicleDto) throws Exception;

    ArrayList<VehicleDto> getAllVehicles() throws Exception;

    ArrayList<VehicleDto> getAvailableVehicles(String status) throws SQLException;

    String getLicensePlateById(int vehicleId) throws SQLException;

    Map<String, Integer> getVehicleStatusCount() throws  SQLException;
}
