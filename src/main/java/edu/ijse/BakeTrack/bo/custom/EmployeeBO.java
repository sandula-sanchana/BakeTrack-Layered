package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.bo.SuperBO;
import edu.ijse.BakeTrack.dto.CustomDto;
import edu.ijse.BakeTrack.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface EmployeeBO  extends SuperBO {

    String addEmployee(EmployeeDto employeeDto) throws Exception;

    String deleteEmployee(int employee_id) throws Exception;

    ArrayList<EmployeeDto> getAllEmployee() throws Exception;

    ArrayList<CustomDto> getAllAvailableAndNonAssinEmp() throws SQLException;

    String updateEmployee(EmployeeDto employeeDto) throws Exception;

    ArrayList<EmployeeDto> getAllEmployeeNamesAndIds() throws SQLException;

    Double getSalaryById(int employeeId) throws SQLException;

    Map<String,Integer> getEmpCount() throws SQLException;
}
