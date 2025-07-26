package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;
import java.util.Map;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Payroll;

public interface PayrollDAO extends CrudDAO<Payroll> {

    String updatePayroll(Payroll payrollDto, int payrollId) throws SQLException;

    Map<String,Integer> getPayrollStatus() throws SQLException;
}
