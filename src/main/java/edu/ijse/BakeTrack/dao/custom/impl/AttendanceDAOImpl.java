package edu.ijse.BakeTrack.dao.custom.impl;
import edu.ijse.BakeTrack.db.DBobject;

import edu.ijse.BakeTrack.entity.AttendanceCount;
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




    public void getAttendanceByEmployee(int employee_id, String status) throws SQLException {
        String sql = "SELECT * FROM attendance WHERE employee_id = ? AND status=?";
        ResultSet rs = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, employee_id);
            statement.setString(2, status);

            rs = statement.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        while (rs.next()) {
            System.out.println("Date: " + rs.getDate("attend_date") +
                    ", Check-in: " + rs.getTime("check_in_time") +
                    ", Check-out: " + rs.getTime("check_out_time") +
                    ", Status: " + rs.getString(("status")));
        }
    }

    public void getAttendanceOnDate(LocalDate date, String status) throws SQLException {
        String sql = "SELECT a.employee_id, e.emp_name, a.attend_date, a.check_in_time, a.check_out_time, a.status FROM attendance a INNER JOIN employee e ON a.employee_id = e.employee_id WHERE a.attend_date = ? AND a.status=?";

        ResultSet rs = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(date));
        statement.setString(2, status);

        rs = statement.executeQuery();
        while (rs.next())
            System.out.println("Employee: " + rs.getString("emp_name") +
                    " (ID: " + rs.getInt("employee_id") + ")" +
                    ", Date: " + rs.getDate("attend_date") +
                    ", Check-in: " + rs.getTime("check_in_time") +
                    ", Check-out: " + rs.getTime("check_out_time") +
                    ", Status: " + rs.getString("status"));

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

    public List<AttendanceCount> getAttendanceEachDay(){
        List<AttendanceCount> attendanceDtos=new ArrayList<>();
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
                   attendanceDtos.add(new AttendanceCount(count,date));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return attendanceDtos;
    }

}
