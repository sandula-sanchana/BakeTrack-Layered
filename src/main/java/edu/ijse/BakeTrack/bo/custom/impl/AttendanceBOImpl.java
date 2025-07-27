package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.AttendanceBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.AttendanceDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dto.AttendanceDto;
import edu.ijse.BakeTrack.entity.Attendance;
import edu.ijse.BakeTrack.dto.AttendanceCount;
import edu.ijse.BakeTrack.entity.AttendanceCountE;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBOImpl implements AttendanceBO {

    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ATTENDANCE);


    public AttendanceBOImpl() throws SQLException, ClassNotFoundException {
    }

    public String saveAttendance(AttendanceDto attendance) {
        try {
            return attendanceDAO.save(new Attendance(attendance.getEmployee_id(),attendance.getAttend_date(),attendance.getCheck_in_time(),attendance.getCheck_out_time(),attendance.getStatus()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String deleteAttendance(int employeeID, LocalDate attendDate) throws SQLException {
        try  {
            return attendanceDAO.deleteAttendance(employeeID, attendDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public double getEmployeeTotalOTHours(int employeeId, int month, int year) throws SQLException {

        try {
           return attendanceDAO.getEmployeeTotalOTHours(employeeId, month, year);
        } catch (SQLException e) {
            System.err.println("Error calculating OT: " + e.getMessage());
            throw e;
        }
    }


    public ArrayList<AttendanceDto> getAllAttendance() throws SQLException {
        try {
            ArrayList<Attendance> getall=attendanceDAO.getAll();
            ArrayList<AttendanceDto> getallDto=new ArrayList<>();
            for(Attendance attendance:getall){
                getallDto.add(new AttendanceDto(attendance.getEmployee_id(),attendance.getAttend_date(),attendance.getCheck_in_time(),attendance.getCheck_out_time(),attendance.getStatus()));
            }
            return getallDto;
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String updateAttendance(AttendanceDto dto) throws SQLException {

        try  {
           return attendanceDAO.update(new Attendance(dto.getEmployee_id(),dto.getAttend_date(),dto.getCheck_in_time(),dto.getCheck_out_time(),dto.getStatus()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<AttendanceCount> getAttendanceEachDay(){
        List<AttendanceCount> attendanceDtos=new ArrayList<>();
        try {
            List<AttendanceCountE> attendanceCounts=attendanceDAO.getAttendanceEachDay();
            for(AttendanceCountE attendanceCount:attendanceCounts){
                attendanceDtos.add(new AttendanceCount(attendanceCount.getCount(),attendanceCount.getDate()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return attendanceDtos;
    }

}
