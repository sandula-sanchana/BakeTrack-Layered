package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.PayrollBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.PayrollDAO;
import edu.ijse.BakeTrack.dto.PayrollDto;
import edu.ijse.BakeTrack.entity.Payroll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PayrollBOImpl implements PayrollBO {

    PayrollDAO payrollDAO=(PayrollDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYROLL);

    public PayrollBOImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public String addPayroll(PayrollDto payrollDto) throws Exception {
        Payroll entity = new Payroll(
                payrollDto.getEmployee_id(),
                payrollDto.getPay_Date(),
                payrollDto.getOver_time_hours(),
                payrollDto.getBase_salary(),
                payrollDto.getFull_salary(),
                payrollDto.getStatus()
        );
        return payrollDAO.save(entity);
    }

    @Override
    public String deletePayroll(int payrollId) throws Exception {
        return payrollDAO.delete(payrollId);
    }

    @Override
    public ArrayList<PayrollDto> getAllPayrolls() throws Exception {
        List<Payroll> all = payrollDAO.getAll();
        ArrayList<PayrollDto> list = new ArrayList<>();
        for (Payroll p : all) {
            list.add(new PayrollDto(
                    p.getPayroll_id(),
                    p.getEmployee_id(),
                    p.getPay_Date(),
                    p.getOver_time_hours(),
                    p.getBase_salary(),
                    p.getFull_salary(),
                    p.getStatus()
            ));
        }
        return list;
    }

    @Override
    public String updatePayroll(PayrollDto dto, int payrollId) throws SQLException {
        Payroll entity = new Payroll(
                payrollId,
                dto.getEmployee_id(),
                dto.getPay_Date(),
                dto.getOver_time_hours(),
                dto.getBase_salary(),
                dto.getFull_salary(),
                dto.getStatus()
        );
        return payrollDAO.updatePayroll(entity, payrollId);
    }

    @Override
    public Map<String, Integer> getPayrollStatus() throws SQLException {
        return payrollDAO.getPayrollStatus();
    }
}
