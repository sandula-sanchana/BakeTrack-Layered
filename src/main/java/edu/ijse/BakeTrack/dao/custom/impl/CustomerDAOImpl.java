package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ijse.BakeTrack.dao.custom.CustomerDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.entity.Customer;

import java.sql.Connection;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    private Connection connection;

    public CustomerDAOImpl() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public String save(Customer customer) throws SQLException {
        String addSql = "INSERT INTO customer (name,contact_no,address) VALUES (?,?,?)";
       Boolean done= SqlExecute.SqlExecute(addSql,customer.getName(),customer.getContact(),customer.getAddress());

        if (done) {
           return "updated successfully";
        } else {
           return "failed";
        }

    }

    public String update(Customer customer) throws SQLException {
        String addSql = "UPDATE customer SET name=?,address=?,contact_no=? WHERE customer_id=?";
        Boolean done= SqlExecute.SqlExecute(addSql,customer.getName(),customer.getAddress(),customer.getContact(),customer.getCustomerID());

        if (done) {
            return "updated successfully";
        } else {
            return "failed";
        }

    }



    public String delete(int customer_id) throws SQLException {
        String deleteSql = "DELETE FROM customer WHERE customer_id=?";
        Boolean done=SqlExecute.SqlExecute(deleteSql,customer_id);

        if (done) {
            return "deleted successfully";
        } else {
           return "failed to delete";
        }

    }



    public ArrayList<Customer> getAll()  {
        String allSql="SELECT * FROM customer";
        ArrayList<Customer> getall=new ArrayList<>();

        try {
            PreparedStatement statement=connection.prepareStatement(allSql);
            ResultSet resultSet=statement.executeQuery();


            while (resultSet.next()){
                getall.add(new Customer(resultSet.getInt("customer_id"),
                        resultSet.getString("name"),resultSet.getString("address"),
                        resultSet.getString("contact_no")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        }
        return getall;
    }

    public Customer getCustomerByCOn(int cus_no) throws SQLException {
        connection=DBobject.getInstance().getConnection();
        String sql="SELECT * FROM customer WHERE contact_no=?";
        ResultSet resultSet=SqlExecute.SqlExecute(sql,cus_no);

        if(resultSet.next()){
            return new Customer(resultSet.getInt("customer_id"), resultSet.getString("name"),resultSet.getString("address"),resultSet.getString("contact_no"));
        }
        return null;
    }
}
