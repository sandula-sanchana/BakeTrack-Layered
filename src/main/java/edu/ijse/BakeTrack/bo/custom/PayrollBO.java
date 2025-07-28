package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.bo.SuperBO;
import edu.ijse.BakeTrack.dto.PayrollDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface PayrollBO  extends SuperBO {
    String addPayroll(PayrollDto payrollDto) throws Exception;

    String deletePayroll(int payrollId) throws Exception;

    ArrayList<PayrollDto> getAllPayrolls() throws Exception;

    String updatePayroll(PayrollDto payrollDto, int payrollId) throws SQLException;

    Map<String,Integer> getPayrollStatus() throws SQLException;
}
