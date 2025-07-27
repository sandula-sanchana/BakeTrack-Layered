package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.EmployeeBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.EmployeeDAO;
import edu.ijse.BakeTrack.dao.custom.QueryDAO;
import edu.ijse.BakeTrack.dto.CustomDto;
import edu.ijse.BakeTrack.dto.EmployeeDto;
import edu.ijse.BakeTrack.entity.Custom;
import edu.ijse.BakeTrack.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO=(EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
    QueryDAO queryDAO=(QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    public EmployeeBOImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public String addEmployee(EmployeeDto employeeDto) throws Exception {
        return employeeDAO.save(new Employee(employeeDto.getName(),employeeDto.getAddress(),employeeDto.getSalary(),employeeDto.getContact(),employeeDto.getROle()));
    }

    @Override
    public String deleteEmployee(int employee_id) throws Exception {
        return employeeDAO.delete(employee_id);
    }

    @Override
    public ArrayList<EmployeeDto> getAllEmployee() throws Exception {
        ArrayList<EmployeeDto> employeeDtos=new ArrayList<>();
        ArrayList<Employee> employees=employeeDAO.getAll();
        for (Employee employee : employees) {
            employeeDtos.add(new EmployeeDto(employee.getEmployee_id(),employee.getName(),employee.getAddress(),employee.getSalary(),employee.getContact(),employee.getROle()));
        }
        return employeeDtos;
    }

    @Override
    public ArrayList<CustomDto> getAllAvailableAndNonAssinEmp() throws SQLException {
        ArrayList<CustomDto> customDtos=new ArrayList<>();
        ArrayList<Custom> employee=queryDAO.getAllAvailableAndNonAssinEmp();
        for (Custom custom : employee) {
            customDtos.add(new CustomDto(custom.getEmployee_id(),custom.getName(),custom.getAddress(),custom.getSalary(),custom.getContact(),custom.getRole()));
        }
        return customDtos;
    }

    @Override
    public String updateEmployee(EmployeeDto employeeDto) throws Exception {
        return employeeDAO.update(new Employee(employeeDto.getEmployee_id(),employeeDto.getName(),employeeDto.getAddress(),employeeDto.getSalary(),employeeDto.getContact(),employeeDto.getROle()));
    }

    @Override
    public ArrayList<EmployeeDto> getAllEmployeeNamesAndIds() throws SQLException {
        ArrayList<EmployeeDto> employeeDtos=new ArrayList<>();
        ArrayList<Employee> employees=employeeDAO.getAllEmployeeNamesAndIds();
        for (Employee employee : employees) {
            employeeDtos.add(new EmployeeDto(employee.getEmployee_id(),employee.getName()));
        }
        return employeeDtos;
    }

    @Override
    public Double getSalaryById(int employeeId) throws SQLException {
        return employeeDAO.getSalaryById(employeeId);
    }

    @Override
    public Map<String, Integer> getEmpCount() throws SQLException {
        return employeeDAO.getEmpCount();
    }
}
