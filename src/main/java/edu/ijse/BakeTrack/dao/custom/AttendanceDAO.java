package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Attendance;
import edu.ijse.BakeTrack.entity.AttendanceCount;

public interface AttendanceDAO extends CrudDAO<Attendance> {


    void getAttendanceByEmployee(int employee_id, String status) throws SQLException;

    void getAttendanceOnDate(LocalDate date, String status) throws SQLException;

    String deleteAttendance(int employeeID, LocalDate attendDate) throws SQLException;

    double getEmployeeTotalOTHours(int employeeId, int month, int year) throws SQLException;

    List<AttendanceCount> getAttendanceEachDay() throws SQLException;
}
