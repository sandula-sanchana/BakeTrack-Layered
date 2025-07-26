package edu.ijse.BakeTrack.dao.custom.impl;

import edu.ijse.BakeTrack.dao.custom.PayrollDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.entity.Payroll;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PayrollDAOImpl implements PayrollDAO {
    private Connection connection;

    public PayrollDAOImpl() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public String save(Payroll payrollDto){
        String addSql = "INSERT INTO payroll (employee_id,pay_date,over_time_hours,base_salary,full_salary,status) VALUES (?,?,?,?,?,?)";


        Boolean done= null;
        try {
            done = SqlExecute.SqlExecute(addSql,payrollDto.getEmployee_id(), Date.valueOf(payrollDto.getPay_Date()),
                    payrollDto.getOver_time_hours(),payrollDto.getBase_salary(),payrollDto.getFull_salary(),payrollDto.getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (done) {
            return  "payroll updated successfully";
        } else {
            return "failed";
        }

    }

    @Override
    public String update(Payroll payroll) throws Exception {
        return "";
    }


    public String delete(int payrollId) throws SQLException {
        String deleteSql = "DELETE FROM payroll WHERE payroll_id=?";

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
           Boolean done=SqlExecute.SqlExecute(deleteSql,payrollId);
            return (done? "Payroll deleted successfully" : "Failed to delete payroll");
        }
    }


    public ArrayList<Payroll> getAll() {
        String sql = "SELECT * FROM payroll";
        ArrayList<Payroll> payrollList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Payroll payroll = new Payroll(
                        resultSet.getInt("payroll_id"),
                        resultSet.getInt("employee_id"),
                        resultSet.getDate("pay_date").toLocalDate(),
                        resultSet.getDouble("over_time_hours"),
                        resultSet.getDouble("base_salary"),
                        resultSet.getDouble("full_salary"),
                        resultSet.getString("status")
                );
                payrollList.add(payroll);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return payrollList;
    }

    public String updatePayroll(Payroll payrollDto, int payrollId) {
        String updateSql = "UPDATE payroll SET employee_id=?,pay_date=?,  over_time_hours=?,base_salary=?, full_salary=?, status=? WHERE payroll_id=?";

        try  {

            boolean done=SqlExecute.SqlExecute(updateSql,payrollDto.getEmployee_id(),payrollDto.getPay_Date(),payrollDto.getOver_time_hours(),
                    payrollDto.getBase_salary(),payrollDto.getFull_salary(),payrollDto.getStatus(),payrollId);
            if (done) {
                return "Payroll updated successfully";
            } else {
                return "Failed to update payroll";
            }
              } catch (Exception e) {
               System.err.println("Error updating payroll: " + e.getMessage());
                throw e;
        }


    }

    public Map<String,Integer> getPayrollStatus(){
        Map<String,Integer> statusCountMap=new HashMap<>();
        String sql="SELECT status, COUNT(*) as count\n" +
                "FROM payroll\n" +
                "WHERE MONTH(pay_date) = MONTH(CURRENT_DATE())\n" +
                "  AND YEAR(pay_date) = YEAR(CURRENT_DATE())\n" +
                "GROUP BY status;\n";

        try {
            ResultSet rs=SqlExecute.SqlExecute(sql);
            while (rs.next()){
                String status=rs.getString("status");
                Integer count=rs.getInt("count");
                statusCountMap.put(status,count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statusCountMap;
    }

}
