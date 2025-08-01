package edu.ijse.BakeTrack.dao.custom.impl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ijse.BakeTrack.dao.custom.EmployeeDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.entity.Employee;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDAOImpl implements EmployeeDAO {


    public EmployeeDAOImpl() throws ClassNotFoundException,SQLException{

    }

    public String save(Employee employeeDto) throws SQLException{
        String addSql="INSERT INTO employee (emp_name,emp_address,salary,contact_no,roles) VALUES (?,?,?,?,?)";
        Boolean done=SqlExecute.SqlExecute(addSql, employeeDto.getName(),employeeDto.getAddress(), employeeDto.getSalary(),employeeDto.getContact(),employeeDto.getROle());

        if(done){
           return "updated emp successfully";
        }else{
         return "failed";
        }

    }

    public ArrayList<Employee> getAllEmployeeNamesAndIds() {
        String sql = "SELECT employee_id, emp_name FROM employee";
        ArrayList<Employee> empList = new ArrayList<>();

        try {
            ResultSet resultSet=SqlExecute.SqlExecute(sql);

            while (resultSet.next()) {
                empList.add(new Employee(resultSet.getInt("employee_id"),resultSet.getString("emp_name")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching employee names and IDs: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return empList;
    }


    public String delete(int employee_id)throws SQLException{
         String deleteSql="DELETE FROM employee WHERE employee_id=?";

         Boolean done=SqlExecute.SqlExecute(deleteSql,employee_id);

         if(done){
            return "deleted emp successfully";
         }else{
               return "failed to delete";
         }

    }

    public ArrayList<Employee> getAll()  {
        String allSql="SELECT * FROM employee";
        ArrayList<Employee> getall=new ArrayList<>();

        try {
            ResultSet resultSet=SqlExecute.SqlExecute(allSql);

            while (resultSet.next()){
                getall.add(new Employee( resultSet.getInt("employee_id"),resultSet.getString("emp_name"),resultSet.getString("emp_address"),resultSet.getDouble("salary"), resultSet.getString("contact_no"),resultSet.getString("roles")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        }
        return getall;
    }




    public String update(Employee employeeDto) throws SQLException {
        String updateSql = "UPDATE employee SET emp_name=?, emp_address=?, salary=?, contact_no=?, roles=? WHERE employee_id=?";
        boolean done = SqlExecute.SqlExecute(
                updateSql,
                employeeDto.getName(),
                employeeDto.getAddress(),
                employeeDto.getSalary(),
                employeeDto.getContact(),
                employeeDto.getROle(),
                employeeDto.getEmployee_id()
        );

        return done ? "Updated employee successfully" : "Failed to update employee";
    }

    public Double getSalaryById(int employeeId) throws SQLException {
        String sql = "SELECT salary FROM employee WHERE employee_id = ?";
        try  {
            ResultSet resultSet = SqlExecute.SqlExecute(sql,employeeId);
            if (resultSet.next()) {
                return resultSet.getDouble("salary");
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,Integer> getEmpCount(){
        String sql="SELECT roles,COUNT(*) as count FROM employee GROUP BY roles";

        Map<String,Integer> countMap=new HashMap<>();

        try {
            ResultSet rs = SqlExecute.SqlExecute(sql);

            while(rs.next()){
                String status = rs.getString("roles");
                int count = rs.getInt("count");
                countMap.put(status,count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countMap;

    }





}
