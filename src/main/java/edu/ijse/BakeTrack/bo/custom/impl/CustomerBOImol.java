package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.CustomerBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.CustomerDAO;
import edu.ijse.BakeTrack.dto.CustomersDto;
import edu.ijse.BakeTrack.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerBOImol implements CustomerBO {

CustomerDAO customerDAO=(CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    public CustomerBOImol() throws SQLException, ClassNotFoundException {
    }

    @Override
    public String addCustomer(CustomersDto customer) throws Exception {
        return customerDAO.save(new Customer(customer.getName(),customer.getContact(),customer.getAddress()));
    }

    @Override
    public String deleteCustomer(int customerId) throws Exception {
        return customerDAO.delete(customerId);
    }

    @Override
    public ArrayList<CustomersDto> getAllCustomers() throws Exception {
        ArrayList<Customer> customers=customerDAO.getAll();
        ArrayList<CustomersDto> customersDtos=new ArrayList<>();
        for (Customer customer : customers) {
            customersDtos.add(new CustomersDto(customer.getCustomerID(),customer.getName(),customer.getContact(),customer.getAddress()));
        }
        return customersDtos;
    }

    @Override
    public CustomersDto getCustomerByCOn(int cus_no) throws SQLException {
        Customer customer= customerDAO.getCustomerByCOn(cus_no);
        return new CustomersDto(customer.getCustomerID(),customer.getName(),customer.getContact(),customer.getAddress());
    }

    @Override
    public String updateCustomer(CustomersDto customersDto) throws Exception {
        return customerDAO.update(new Customer(customersDto.getCustomerID(),customersDto.getName(),customersDto.getContact(),customersDto.getAddress()));
    }
}
