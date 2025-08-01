package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Attendance;
import edu.ijse.BakeTrack.entity.AttendanceCountE;

public interface AttendanceDAO extends CrudDAO<Attendance> {


    String deleteAttendance(int employeeID, LocalDate attendDate) throws SQLException;

    double getEmployeeTotalOTHours(int employeeId, int month, int year) throws SQLException;

    List<AttendanceCountE> getAttendanceEachDay() throws SQLException;
}
