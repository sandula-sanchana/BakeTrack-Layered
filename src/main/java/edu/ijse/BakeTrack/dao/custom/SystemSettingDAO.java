package edu.ijse.BakeTrack.dao.custom;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.SystemSetting;

import java.sql.SQLException;

public interface SystemSettingDAO extends CrudDAO<SystemSetting> {

    int getOTRate() throws SQLException;

    String setOTRate(int rate) throws SQLException;
}
