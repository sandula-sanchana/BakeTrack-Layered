package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.bo.SuperBO;
import edu.ijse.BakeTrack.dto.AttendanceCount;
import edu.ijse.BakeTrack.dto.AttendanceDto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface AttendanceBO  extends SuperBO {
    String saveAttendance(AttendanceDto attendance)  throws SQLException;

    ArrayList<AttendanceDto> getAllAttendance() throws  SQLException;

    String deleteAttendance(int employeeID, LocalDate attendDate) throws SQLException;

    String updateAttendance(AttendanceDto dto) throws SQLException;

    double getEmployeeTotalOTHours(int employeeId, int month, int year) throws SQLException;

    List<AttendanceCount> getAttendanceEachDay() throws SQLException;
}
