package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Employee;

public interface EmployeeDAO extends CrudDAO<Employee> {


    ArrayList<Employee> getAllEmployeeNamesAndIds() throws SQLException;

    Double getSalaryById(int employeeId) throws SQLException;

    Map<String,Integer> getEmpCount() throws SQLException;
}
