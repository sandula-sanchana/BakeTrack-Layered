package edu.ijse.BakeTrack.dao.custom.impl;

import edu.ijse.BakeTrack.dao.custom.DeliveryDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.entity.Delivery;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeliveryDAOImpl implements DeliveryDAO {

    private Connection connection;

    public DeliveryDAOImpl() {
        try {
            this.connection = DBobject.getInstance().getConnection();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public String delete(int deliveryId) {
        String deleteSql = "DELETE FROM delivery WHERE delivery_id = ?";

        Boolean delete = SqlExecute.SqlExecute(deleteSql, deliveryId);
        if (delete) {
            return "Delivery deleted successfully";
        } else {
            return "Failed to delete delivery";
        }
    }

    public ArrayList<Delivery> getUnassignedDeliveries() {
        String query = "SELECT * FROM delivery WHERE employee_id IS NULL";
        ArrayList<Delivery> unassignedDeliveries = new ArrayList<>();

        try {

            ResultSet resultSet = SqlExecute.SqlExecute(query);

            while (resultSet.next()) {

                unassignedDeliveries.add(new Delivery(
                        resultSet.getInt("delivery_id"),
                        resultSet.getInt("vehicle_id"),
                        0,
                        resultSet.getDate("delivery_date").toLocalDate(),
                        resultSet.getString("area")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return unassignedDeliveries;
    }


    @Override
    public String save(Delivery delivery) throws Exception {
        return "";
    }

    public String update(Delivery deliveryDto) {
        String updateSql = "UPDATE delivery SET vehicle_id = ?,employee_id=?, delivery_date = ?, area = ? WHERE delivery_id = ?";
        Boolean updateDone = SqlExecute.SqlExecute(updateSql, deliveryDto.getVehicleID(),
                deliveryDto.getEmployee_id(), Date.valueOf(deliveryDto.getDeliveryDate()), deliveryDto.getDeliveryArea(), deliveryDto.getDelivery_id());

        if (updateDone) {
            return "Delivery updated successfully";
        }
        return "Failed to update delivery";
    }

    @Override
    public int addDelivery(Delivery dto) throws SQLException {
        String sql = "INSERT INTO delivery (vehicle_id, employee_id, delivery_date, area) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, dto.getVehicleID());
            ps.setNull(2, -1);
            ps.setDate(3, Date.valueOf(dto.getDeliveryDate()));
            ps.setString(4, dto.getDeliveryArea());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        }
    }

    @Override
    public int getVehicleIDbyDelID(int delivery_id) throws SQLException {
        String sql = "SELECT vehicle_id FROM delivery WHERE delivery_id = ?";

            try (ResultSet resultSet = SqlExecute.SqlExecute(sql, delivery_id)) {
                if (resultSet.next()) {
                    return resultSet.getInt("vehicle_id");
                } else {
                    throw new SQLException("No delivery found with ID: " + delivery_id);
                }
            }

    }

    public ArrayList<Delivery> getAll()  {
        String allSql="SELECT * FROM delivery";
        ArrayList<Delivery> getall=new ArrayList<>();

        try {
            ResultSet resultSet=SqlExecute.SqlExecute(allSql);

            while (resultSet.next()){
                getall.add(new Delivery(resultSet.getInt("delivery_id"),resultSet.getInt("vehicle_id"),resultSet.getInt("employee_id"),resultSet.getDate("delivery_date").toLocalDate(), resultSet.getString("area")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        }
        return getall;
    }


    public String setEmployeeForDelivery(int del_id,int emp_id){
        Boolean done= null;
        try {
            String empSql="UPDATE delivery SET employee_id=? WHERE delivery_id=?";
            done = SqlExecute.SqlExecute(empSql,emp_id,del_id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(done){
            return "successfully set the employee";
        }
        return "error while set Employee";
    }

}
