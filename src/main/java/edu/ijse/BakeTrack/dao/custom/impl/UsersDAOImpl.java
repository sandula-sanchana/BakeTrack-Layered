package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.BakeTrack.dao.custom.UsersDAO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.entity.Users;

public class UsersDAOImpl implements UsersDAO {

    private Connection connection;

    public UsersDAOImpl() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public String save(Users usersDto) throws SQLException {
        String sql = "INSERT INTO users (user_name, user_password, roles, email) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usersDto.getUser_name());
        statement.setString(2, usersDto.getUser_password());
        statement.setString(3, usersDto.getRoles());
        statement.setString(4, usersDto.getEmail());

        int updates = statement.executeUpdate();
        if (updates > 0) {
            return "user added";
        } else {
            return "fail";
        }
    }

    public String getEmailByRole(String role) throws SQLException {
        String sql = "SELECT email FROM users WHERE roles = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, role);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("email");
        } else {
            return null;
        }
    }

    public String getPasswordByRole(String role) throws SQLException {
        String sql = "SELECT user_password FROM users WHERE roles = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, role);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("user_password");
        } else {
            return null;
        }
    }

    public String getUserNameByRole(String role) throws SQLException {
        String sql = "SELECT user_name FROM users WHERE roles = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, role);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("user_name");
        } else {
            return null;
        }
    }

    public String updatePasswordByRole(String role, String newPassword) throws SQLException {
        String sql = "UPDATE users SET user_password = ? WHERE roles = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newPassword);
        statement.setString(2, role);

        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "Password updated successfully." : "Failed to update password.";
    }





    public String update(Users usersDto) throws SQLException {
        String sql = "UPDATE users SET user_name = ?, roles = ?, email = ? WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usersDto.getUser_name());
        statement.setString(2, usersDto.getRoles());
        statement.setString(3, usersDto.getEmail());
        statement.setInt(4, usersDto.getUser_id());

        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "User updated successfully." : "Failed to update user.";
    }


    public String delete(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "User deleted successfully." : "Failed to delete user.";
    }

    public String authenticater(String user_name, String user_password) throws SQLException {
        String authenticateSQL = "SELECT roles,user_password as storedPassword FROM users WHERE user_name=?";
        PreparedStatement statement = connection.prepareStatement(authenticateSQL);
        statement.setString(1, user_name);

        ResultSet results = statement.executeQuery();

        if (results.next()) {

            String sp = results.getString("storedPassword");
            if (sp.equals(user_password)) {
                return results.getString("roles");
            } else {
                return null;
            }

        }
        return null;
    }

    public ArrayList<Users> getAll() throws SQLException {
        ArrayList<Users> usersList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            Users user = new Users(
                    resultSet.getInt("user_id"),
                    resultSet.getString("user_name"),
                    resultSet.getString("user_password"),
                    resultSet.getString("roles"),
                    resultSet.getString("email")
            );

            usersList.add(user);
        }
        return usersList;
    }



}
