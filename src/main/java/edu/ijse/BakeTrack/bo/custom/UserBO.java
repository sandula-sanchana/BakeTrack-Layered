package edu.ijse.BakeTrack.bo.custom;

import edu.ijse.BakeTrack.dto.UsersDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO {

    String addUser(UsersDto usersDto) throws Exception;
    String deleteUser(int user_id) throws Exception;
    String updateUser(UsersDto usersDto) throws Exception;
    String authenticater(String u_name,String u_password) throws SQLException;

    ArrayList<UsersDto> getAllUsers() throws Exception;

    String getEmailByRole(String role) throws SQLException;

    String getPasswordByRole(String role) throws SQLException;

    String getUserNameByRole(String role) throws SQLException;

    String updatePasswordByRole(String role, String newPassword) throws SQLException;
}
