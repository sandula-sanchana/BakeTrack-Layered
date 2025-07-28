package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ijse.BakeTrack.dao.custom.VehicleDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dto.VehicleDto;
import edu.ijse.BakeTrack.entity.Vehicle;

public class VehicleDAOImpl implements VehicleDAO {
    private Connection connection;

    public VehicleDAOImpl() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public String save(Vehicle vehicleDto) throws SQLException {
        String sql = "INSERT INTO vehicle (type, license_plate) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vehicleDto.getType());
        statement.setString(2, vehicleDto.getLicensePlate());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Vehicle added successfully";
        } else {
            return "Failed to add vehicle";
        }
    }

    public Map<String, Integer> getVehicleStatusCount() {
        String sql = "SELECT status, COUNT(*) as count FROM vehicle GROUP BY status";

        Map<String, Integer> statusCountMap = new HashMap<>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

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
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "available");
        statement.setInt(2, vehicleId);

        return statement.executeUpdate() > 0 ? "OK" : "vehicle status update error";
    }

    @Override
    public boolean updateVehicleStatus(int vehicleID, String status) throws SQLException {
        String sql = "UPDATE vehicle SET status = ? WHERE vehicle_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, vehicleID);
            return ps.executeUpdate() > 0;
        }
    }


    public String delete(int vehicleId) throws SQLException {
        String sql = "DELETE FROM vehicle WHERE vehicle_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, vehicleId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Vehicle deleted successfully";
        } else {
            return "Failed to delete vehicle";
        }
    }

    public String update(Vehicle vehicleDto) throws SQLException {
        String sql = "UPDATE vehicle SET type = ?, license_plate = ?,status=? WHERE vehicle_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vehicleDto.getType());
        statement.setString(2, vehicleDto.getLicensePlate());
        statement.setString(3, vehicleDto.getStatus());
        statement.setInt(4,vehicleDto.getVehicle_id());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Vehicle updated successfully";
        } else {
            return "Failed to update vehicle";
        }
    }

     public void getVehicleById(int vehicleId) throws SQLException {
        String sql = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, vehicleId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
           System.out.println(resultSet.getString("type"));
        }
    }

    public ArrayList<Vehicle> getAll() throws SQLException {
        String sql = "SELECT * FROM vehicle";
        ArrayList<Vehicle> vehicleList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

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
        String sql = "SELECT * FROM vehicle WHERE status=?";
        ArrayList<VehicleDto> vehicleList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,status);
            ResultSet resultSet = statement.executeQuery();


                while(resultSet.next()){
                VehicleDto vehicle = new VehicleDto(
                        resultSet.getInt("vehicle_id"),
                        resultSet.getString("type"),
                        resultSet.getString("license_plate"),
                        resultSet.getString("status"));
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
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, vehicleId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("license_plate");
        } else {
            return null;
        }
    }


}