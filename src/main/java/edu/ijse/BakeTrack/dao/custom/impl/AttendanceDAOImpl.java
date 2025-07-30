package edu.ijse.BakeTrack.dao.custom.impl;
import edu.ijse.BakeTrack.db.DBobject;

import edu.ijse.BakeTrack.entity.AttendanceCountE;
import edu.ijse.BakeTrack.dao.custom.AttendanceDAO;
import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.entity.Attendance;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    private final Connection connection;

    public AttendanceDAOImpl() {
        try {
            this.connection = DBobject.getInstance().getConnection();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    public String save(Attendance attendance) {
        String addSql = "INSERT INTO attendance (employee_id,attend_date,check_in_time,check_out_time,status) VALUES (?,?,?,?,?)";
        Boolean done = null;
        try {
            done = SqlExecute.SqlExecute(addSql, attendance.getEmployee_id(), Date.valueOf(attendance.getAttend_date()),
                    Time.valueOf(attendance.getCheck_in_time()), Time.valueOf(attendance.getCheck_out_time()), attendance.getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (done){
               return "attendance added successfully";
            } else{
                return "failed";
            }


    }

    public String deleteAttendance(int employeeID, LocalDate attendDate) throws SQLException {
        String sql = "DELETE FROM attendance WHERE employee_id = ? AND attend_date = ?";
        try  {
            Boolean done=SqlExecute.SqlExecute(sql,employeeID,Date.valueOf(attendDate));
            if (done) {
                return "Attendance record deleted successfully.";
            } else {
                return "No attendance record found for deletion.";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public double getEmployeeTotalOTHours(int employeeId, int month, int year) throws SQLException {
        String sql = "SELECT ROUND(SUM(GREATEST((TIME_TO_SEC(TIMEDIFF(check_out_time, check_in_time)) / 3600.0) - 8, 0)), 2) AS total_overtime_hours " +
                "FROM attendance " +
                "WHERE employee_id = ? AND MONTH(attend_date) = ? AND YEAR(attend_date) = ? " +
                "GROUP BY employee_id";

        try {
            ResultSet resultSet = SqlExecute.SqlExecute(sql, employeeId, month, year);
            if (resultSet.next()) {
                return resultSet.getDouble("total_overtime_hours");
            } else {
                return 0.0;
            }
        } catch (SQLException e) {
            System.err.println("Error calculating OT: " + e.getMessage());
            throw e;
        }
    }



    public ArrayList<Attendance> getAll() throws SQLException {
        String allSql="SELECT * FROM attendance";
        ArrayList<Attendance> getall=new ArrayList<>();
        try {

            ResultSet resultSet= SqlExecute.SqlExecute(allSql);
            while (resultSet.next()){
                getall.add(new Attendance(resultSet.getInt("employee_id"), resultSet.getDate("attend_date").toLocalDate(),resultSet.getTime("check_in_time").toLocalTime(),resultSet.getTime("check_out_time").toLocalTime(),resultSet.getString("status")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        }
        return  getall;
    }

    public String update(Attendance dto) throws SQLException {
        String sql = "UPDATE attendance SET check_in_time = ?, check_out_time = ?, status = ? " +
                "WHERE employee_id = ? AND attend_date = ?";
        try  {

            Boolean done=SqlExecute.SqlExecute(sql,Time.valueOf(dto.getCheck_in_time()),Time.valueOf(dto.getCheck_out_time()),dto.getStatus(),dto.getEmployee_id(),Date.valueOf(dto.getAttend_date()));
            return done ? "Attendance updated successfully." : "Update failed or record not found.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String delete(int t) throws Exception {
        return "";
    }

    public List<AttendanceCountE> getAttendanceEachDay(){
        List<AttendanceCountE> attendanceDtos=new ArrayList<>();
        try {
            String sql="SELECT attend_date, COUNT(*) AS count\n" +
                    "FROM attendance\n" +
                    "WHERE status = 'present'\n" +
                    "GROUP BY attend_date\n" +
                    "ORDER BY attend_date;\n";

            ResultSet rs=SqlExecute.SqlExecute(sql);
            while (rs.next()){
                   int count=rs.getInt("count");
                   LocalDate date=rs.getDate("attend_date").toLocalDate();
                   attendanceDtos.add(new AttendanceCountE(count,date));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return attendanceDtos;
    }

}
