package edu.ijse.BakeTrack.dao.custom.impl;

import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.dao.custom.QueryDAO;
import edu.ijse.BakeTrack.entity.Custom;
import edu.ijse.BakeTrack.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    public ArrayList<Custom> getAllAvailableAndNonAssinEmp() throws SQLException {
        ArrayList<Custom> arrayList=new ArrayList<>();
        String searchSQl= "SELECT * \n" +
                "FROM employee e \n" +
                "JOIN attendance a ON e.employee_id = a.employee_id \n" +
                "LEFT JOIN delivery d \n" +
                "  ON e.employee_id = d.employee_id \n" +
                "  AND d.delivery_date = CURRENT_DATE \n" +
                "WHERE a.status = 'present' \n" +
                "AND a.attend_date = CURRENT_DATE \n" +
                "AND e.roles = 'Mobile_sellers' \n" +
                "AND d.employee_id IS NULL;";

        ResultSet resultSet= null;
        try {
            resultSet = SqlExecute.SqlExecute(searchSQl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        while (resultSet.next()){
            arrayList.add(new Custom( resultSet.getInt("employee_id"),
                    resultSet.getString("emp_name"),resultSet.getString("emp_address"),resultSet.getDouble("salary"), resultSet.getString("contact_no"),resultSet.getString("roles")));
        }

        return arrayList;
    }
}
