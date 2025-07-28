package edu.ijse.BakeTrack.bo.custom;

import java.sql.SQLException;

public interface SystemSettingBO {
    int getOTRate() throws SQLException;

    String setOTRate(int rate) throws SQLException;
}
