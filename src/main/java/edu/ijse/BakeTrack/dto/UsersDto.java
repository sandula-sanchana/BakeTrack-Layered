package edu.ijse.BakeTrack.dto;

public class UsersDto {
    private int user_id;
    private String user_name;
    private String user_password;
    private String roles;
    private String email;

    public UsersDto(int user_id, String user_name, String user_password, String roles, String email) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.roles = roles;
        this.email = email;
    }

    public UsersDto(String user_name, String user_password, String roles, String email) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.roles = roles;
        this.email = email;
    }

    public UsersDto(int user_id, String user_name, String roles, String email) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.roles = roles;
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
