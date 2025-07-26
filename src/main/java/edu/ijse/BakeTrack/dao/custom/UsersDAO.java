package edu.ijse.BakeTrack.dao.custom;

import java.sql.SQLException;

import edu.ijse.BakeTrack.dao.CrudDAO;
import edu.ijse.BakeTrack.entity.Users;

public interface UsersDAO extends CrudDAO<Users> {
    String authenticater(String u_name,String u_password) throws SQLException;

    String getEmailByRole(String role) throws SQLException;

    String getPasswordByRole(String role) throws SQLException;

    String getUserNameByRole(String role) throws SQLException;

    String updatePasswordByRole(String role, String newPassword) throws SQLException;
}
