package edu.ijse.BakeTrack.dao.custom.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.BakeTrack.dao.SqlExecute;
import edu.ijse.BakeTrack.dao.custom.UsersDAO;
import edu.ijse.BakeTrack.entity.Users;

public class UsersDAOImpl implements UsersDAO {

    public UsersDAOImpl() throws ClassNotFoundException, SQLException {

    }

    public String save(Users usersDto) throws SQLException {
        String sql = "INSERT INTO users (user_name, user_password, roles, email) VALUES (?, ?, ?, ?)";
        int updates = SqlExecute.SqlExecute(sql,
                usersDto.getUser_name(),
                usersDto.getUser_password(),
                usersDto.getRoles(),
                usersDto.getEmail()
        );
        return updates > 0 ? "User added" : "Fail";
    }

    public String getEmailByRole(String role) throws SQLException {
        String sql = "SELECT email FROM users WHERE roles = ? LIMIT 1";
        ResultSet rs = SqlExecute.SqlExecute(sql, role);
        return rs.next() ? rs.getString("email") : null;
    }

    public String getPasswordByRole(String role) throws SQLException {
        String sql = "SELECT user_password FROM users WHERE roles = ? LIMIT 1";
        ResultSet rs = SqlExecute.SqlExecute(sql, role);
        return rs.next() ? rs.getString("user_password") : null;
    }

    public String getUserNameByRole(String role) throws SQLException {
        String sql = "SELECT user_name FROM users WHERE roles = ? LIMIT 1";
        ResultSet rs = SqlExecute.SqlExecute(sql, role);
        return rs.next() ? rs.getString("user_name") : null;
    }

    public String updatePasswordByRole(String role, String newPassword) throws SQLException {
        String sql = "UPDATE users SET user_password = ? WHERE roles = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql, newPassword, role);
        return rowsAffected > 0 ? "Password updated successfully." : "Failed to update password.";
    }

    public String update(Users usersDto) throws SQLException {
        String sql = "UPDATE users SET user_name = ?, roles = ?, email = ? WHERE user_id = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql,
                usersDto.getUser_name(),
                usersDto.getRoles(),
                usersDto.getEmail(),
                usersDto.getUser_id()
        );
        return rowsAffected > 0 ? "User updated successfully." : "Failed to update user.";
    }

    public String delete(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        int rowsAffected = SqlExecute.SqlExecute(sql, userId);
        return rowsAffected > 0 ? "User deleted successfully." : "Failed to delete user.";
    }

    public String authenticater(String user_name, String user_password) throws SQLException {
        String sql = "SELECT roles, user_password as storedPassword FROM users WHERE user_name = ?";
        ResultSet rs = SqlExecute.SqlExecute(sql, user_name);
        if (rs.next()) {
            String storedPassword = rs.getString("storedPassword");
            return storedPassword.equals(user_password) ? rs.getString("roles") : null;
        }
        return null;
    }

    public ArrayList<Users> getAll() throws SQLException {
        String sql = "SELECT * FROM users";
        ResultSet rs = SqlExecute.SqlExecute(sql);
        ArrayList<Users> usersList = new ArrayList<>();

        while (rs.next()) {
            Users user = new Users(
                    rs.getInt("user_id"),
                    rs.getString("user_name"),
                    rs.getString("user_password"),
                    rs.getString("roles"),
                    rs.getString("email")
            );
            usersList.add(user);
        }
        return usersList;
    }
}
