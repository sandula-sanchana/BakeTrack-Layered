package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.dto.CustomersDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO {
    String addCustomer(CustomersDto customer) throws Exception;

    String deleteCustomer(int customerId) throws Exception;

    ArrayList<CustomersDto> getAllCustomers() throws Exception;

    CustomersDto getCustomerByCOn(int cus_no) throws SQLException;

    String updateCustomer(CustomersDto customersDto) throws Exception;
}
