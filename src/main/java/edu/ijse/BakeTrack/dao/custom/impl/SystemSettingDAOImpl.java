package edu.ijse.BakeTrack.dao.custom.impl;

import edu.ijse.BakeTrack.dao.custom.SystemSettingDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.entity.SystemSetting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public  class SystemSettingDAOImpl implements SystemSettingDAO {

    private final Connection connection;

    public SystemSettingDAOImpl() {
        try {
            this.connection = DBobject.getInstance().getConnection();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    public int getOTRate(){
        String sql = "SELECT setting_value FROM system_settings WHERE setting_name = 'ot_rate'";

        try {
            ResultSet resultSet= SqlExecute.SqlExecute(sql);
            if(resultSet.next()){
                int rate=resultSet.getInt("setting_value");
                return rate;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return  0;
    }


    public String setOTRate(int rate) throws SQLException {
        String sql = "UPDATE system_settings SET setting_value = ? WHERE setting_name = 'ot_rate'";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, rate);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                return "OT rate updated successfully.";
            } else {
                return "Failed to update OT rate. Record may not exist.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating OT rate: " + e.getMessage(), e);
        }
    }

    @Override
    public String save(SystemSetting systemSetting) throws Exception {
        return "";
    }

    @Override
    public String update(SystemSetting systemSetting) throws Exception {
        return "";
    }

    @Override
    public String delete(int t) throws Exception {
        return "";
    }

    @Override
    public ArrayList<SystemSetting> getAll() throws Exception {
        return null;
    }
}
