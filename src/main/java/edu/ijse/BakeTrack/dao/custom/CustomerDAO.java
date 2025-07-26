package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Customer;

public interface CustomerDAO extends CrudDAO<Customer> {

 Customer getCustomerByCOn(int cus_no) throws SQLException;

}
