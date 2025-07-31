package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.dao.custom.VehicleDAO;
import edu.ijse.BakeTrack.dto.VehicleDto;
import edu.ijse.BakeTrack.entity.Vehicle;

public class VehicleDAOImpl implements VehicleDAO {

    public VehicleDAOImpl() throws ClassNotFoundException, SQLException {

    }

    public String save(Vehicle vehicleDto) throws SQLException {
        String sql = "INSERT INTO vehicle (type, license_plate) VALUES (?, ?)";
        int rowsAffected = SqlExecute.SqlExecute(sql, vehicleDto.getType(), vehicleDto.getLicensePlate());
        return rowsAffected > 0 ? "Vehicle added successfully" : "Failed to add vehicle";
    }

    public Map<String, Integer> getVehicleStatusCount() {
        String sql = "SELECT status, COUNT(*) as count FROM vehicle GROUP BY status";
        Map<String, Integer> statusCountMap = new HashMap<>();

        try {
            ResultSet rs = SqlExecute.SqlExecute(sql);
            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("count");
                statusCountMap.put(status, count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return statusCountMap;
    }

    public String updateVehicleStatusToAvailable(int vehicleId) throws SQLException {
        String sql = "UPDATE vehicle SET status = ? WHERE vehicle_id = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql, "available", vehicleId);
        return rowsAffected > 0 ? "OK" : "vehicle status update error";
    }

    @Override
    public boolean updateVehicleStatus(int vehicleID, String status) throws SQLException {
        String sql = "UPDATE vehicle SET status = ? WHERE vehicle_id = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql, status, vehicleID);
        return rowsAffected > 0;
    }

    public String delete(int vehicleId) throws SQLException {
        String sql = "DELETE FROM vehicle WHERE vehicle_id = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql, vehicleId);
        return rowsAffected > 0 ? "Vehicle deleted successfully" : "Failed to delete vehicle";
    }

    public String update(Vehicle vehicleDto) throws SQLException {
        String sql = "UPDATE vehicle SET type = ?, license_plate = ?, status = ? WHERE vehicle_id = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql,
                vehicleDto.getType(),
                vehicleDto.getLicensePlate(),
                vehicleDto.getStatus(),
                vehicleDto.getVehicle_id()
        );
        return rowsAffected > 0 ? "Vehicle updated successfully" : "Failed to update vehicle";
    }

    public void getVehicleById(int vehicleId) throws SQLException {
        String sql = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        ResultSet resultSet = SqlExecute.SqlExecute(sql, vehicleId);
        if (resultSet.next()) {
            System.out.println(resultSet.getString("type"));
        }
    }

    public ArrayList<Vehicle> getAll() throws SQLException {
        String sql = "SELECT * FROM vehicle";
        ArrayList<Vehicle> vehicleList = new ArrayList<>();

        try {
            ResultSet resultSet = SqlExecute.SqlExecute(sql);
            while (resultSet.next()) {
                Vehicle vehicle = new Vehicle(
                        resultSet.getInt("vehicle_id"),
                        resultSet.getString("type"),
                        resultSet.getString("license_plate"),
                        resultSet.getString("status")
                );
                vehicleList.add(vehicle);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return vehicleList;
    }

    public ArrayList<VehicleDto> getAvailableVehicles(String status) throws SQLException {
        String sql = "SELECT * FROM vehicle WHERE status = ?";
        ArrayList<VehicleDto> vehicleList = new ArrayList<>();

        try {
            ResultSet resultSet = SqlExecute.SqlExecute(sql, status);
            while (resultSet.next()) {
                VehicleDto vehicle = new VehicleDto(
                        resultSet.getInt("vehicle_id"),
                        resultSet.getString("type"),
                        resultSet.getString("license_plate"),
                        resultSet.getString("status")
                );
                vehicleList.add(vehicle);
            }
            return vehicleList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getLicensePlateById(int vehicleId) throws SQLException {
        String sql = "SELECT license_plate FROM vehicle WHERE vehicle_id = ?";
        ResultSet resultSet = SqlExecute.SqlExecute(sql, vehicleId);
        return resultSet.next() ? resultSet.getString("license_plate") : null;
    }
}
