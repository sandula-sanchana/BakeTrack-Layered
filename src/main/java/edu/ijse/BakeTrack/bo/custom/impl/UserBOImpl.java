package edu.ijse.BakeTrack.bo.custom.impl;

import edu.ijse.BakeTrack.bo.custom.UserBO;
import edu.ijse.BakeTrack.dao.DAOFactory;
import edu.ijse.BakeTrack.dao.custom.UsersDAO;
import edu.ijse.BakeTrack.dto.UsersDto;
import edu.ijse.BakeTrack.entity.Users;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    private UsersDAO usersDAO = (UsersDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    public UserBOImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public String addUser(UsersDto usersDto) throws Exception {
        Users user = new Users(
                usersDto.getUser_id(),
                usersDto.getUser_name(),
                usersDto.getUser_password(),
                usersDto.getRoles(),
                usersDto.getEmail()
        );
        return usersDAO.save(user);
    }

    @Override
    public String deleteUser(int user_id) throws Exception {
        return usersDAO.delete(user_id);
    }

    @Override
    public String updateUser(UsersDto usersDto) throws Exception {
        Users user = new Users(
                usersDto.getUser_id(),
                usersDto.getUser_name(),
                usersDto.getUser_password(),
                usersDto.getRoles(),
                usersDto.getEmail()
        );
        return usersDAO.update(user);
    }

    @Override
    public String authenticater(String u_name, String u_password) throws SQLException {
        return usersDAO.authenticater(u_name, u_password);
    }

    @Override
    public ArrayList<UsersDto> getAllUsers() throws Exception {
        ArrayList<UsersDto> dtoList = new ArrayList<>();
        for (Users user : usersDAO.getAll()) {
            dtoList.add(new UsersDto(
                    user.getUser_id(),
                    user.getUser_name(),
                    user.getUser_password(),
                    user.getRoles(),
                    user.getEmail()
            ));
        }
        return dtoList;
    }

    @Override
    public String getEmailByRole(String role) throws SQLException {
        return usersDAO.getEmailByRole(role);
    }

    @Override
    public String getPasswordByRole(String role) throws SQLException {
        return usersDAO.getPasswordByRole(role);
    }

    @Override
    public String getUserNameByRole(String role) throws SQLException {
        return usersDAO.getUserNameByRole(role);
    }

    @Override
    public String updatePasswordByRole(String role, String newPassword) throws SQLException {
        return usersDAO.updatePasswordByRole(role, newPassword);
    }
}
