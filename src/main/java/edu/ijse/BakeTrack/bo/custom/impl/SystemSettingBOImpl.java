package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.SystemSettingBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.SystemSettingDAO;

import java.sql.SQLException;

public class SystemSettingBOImpl implements SystemSettingBO {

    SystemSettingDAO systemSettingDAO=(SystemSettingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SYSTEM_SETTING);

    public SystemSettingBOImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public int getOTRate() throws SQLException {
        return systemSettingDAO.getOTRate();
    }

    @Override
    public String setOTRate(int rate) throws SQLException {
        return systemSettingDAO.setOTRate(rate);
    }
}
