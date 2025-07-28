package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.bo.SuperBO;

import java.sql.SQLException;

public interface SystemSettingBO  extends SuperBO {
    int getOTRate() throws SQLException;

    String setOTRate(int rate) throws SQLException;
}
