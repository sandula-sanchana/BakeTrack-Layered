package edu.ijse.BakeTrack.dao.custom;

import edu.ijse.BakeTrack.dao.SuperDAO;
import edu.ijse.BakeTrack.entity.Custom;
import edu.ijse.BakeTrack.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<Custom> getAllAvailableAndNonAssinEmp() throws SQLException;
}
