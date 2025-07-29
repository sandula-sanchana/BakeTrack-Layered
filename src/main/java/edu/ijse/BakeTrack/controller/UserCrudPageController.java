package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.UserBO;
import edu.ijse.BakeTrack.dto.UsersDto;
import edu.ijse.BakeTrack.tm.UserTM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserCrudPageController implements Initializable {

    private String usernameRegex = "^[a-zA-Z0-9_]{3,20}$";
    private String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";



    private ObservableList<UserTM> userTMObservableList= FXCollections.observableArrayList();
    private UserBO userBO=(UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public UserCrudPageController() throws SQLException, ClassNotFoundException {

    }

    @FXML
    private AnchorPane apUserCrudPage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private TableColumn<UserTM, String> colEmail;

    @FXML
    private TableColumn<UserTM, String> colRole;

    @FXML
    private TableColumn<UserTM, Integer> colUserID;

    @FXML
    private TableColumn<UserTM, String> colUsername;

    @FXML
    private TableView<UserTM> tblUsers;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnDelete(ActionEvent event) {
           deleteUser();
    }

    @FXML
    void btnGoBack(ActionEvent event) {
        apUserCrudPage.getChildren().clear();
        try {
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/OwnerDashboard.fxml"));
            apUserCrudPage.getChildren().add(ap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSave(ActionEvent event) {
        saveUser();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
       updateUser();
    }

    @FXML
    void tableMouseClick(MouseEvent event) {
        UserTM selected=tblUsers.getSelectionModel().getSelectedItem();
        if(selected!=null){
            txtUsername.setText(selected.getUser_name());
             txtEmail.setText(selected.getEmail());
             cmbRole.setValue(selected.getRoles());
        }
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

    }

    @FXML
    void txtMouseClick(MouseEvent event) {
        btnSave.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colUserID.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("roles"));

        loadAllusersToTable();
        tblUsers.setItems(userTMObservableList);
        cmbRole.getItems().addAll("Admin","StoreManager","HRManager");

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

    }

    public void loadAllusersToTable(){
        try {
            ArrayList<UsersDto>usersDtos = userBO.getAllUsers();
            for (UsersDto usersDto : usersDtos) {
               userTMObservableList.add(new UserTM(usersDto.getUser_id(),usersDto.getUser_name(),usersDto.getRoles(),usersDto.getEmail()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(){
        if(txtUsername==null || txtPassword==null || txtEmail==null || cmbRole.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"fill fields first").showAndWait();
            return;
        }

        String name = txtUsername.getText();
        String password = txtPassword.getText();
        String email=txtEmail.getText();
        String role=cmbRole.getValue();

        boolean validName = name.matches(usernameRegex);
        boolean validEmail = email.matches(emailRegex);

        if (!validName) txtUsername.setStyle("-fx-border-color: red;");
        if (!validEmail) txtEmail.setStyle("-fx-border-color: red;");


        if (validName && validEmail) {
            UsersDto dto = new UsersDto(name,password,role,email);
            try {
                String response = userBO.addUser(dto);
                new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                userTMObservableList.clear();
                loadAllusersToTable();
                clearFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter valid data!").showAndWait();
        }
    }

    private void clearFields() {
        txtUsername.clear();
        txtPassword.clear();
        txtEmail.clear();
        cmbRole.setValue(null);
        txtUsername.setStyle(null);
        txtEmail.setStyle(null);
    }

    public void deleteUser(){
        UserTM selected = tblUsers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Deletion", ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText("Are you sure you want to delete this user?");
            confirm.setContentText("This action cannot be undone.");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    userBO.deleteUser(selected.getUser_id());
                    new Alert(Alert.AlertType.INFORMATION, "User deleted successfully.").showAndWait();
                    userTMObservableList.clear();
                    loadAllusersToTable();
                    clearFields();
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete user.").showAndWait();
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a user to delete.").showAndWait();
        }
    }

    public void updateUser() {

        UserTM selected = tblUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a user to update.").showAndWait();
            return;
        }

        String updatedName = txtUsername.getText();
        String updatedEmail = txtEmail.getText();
        String updatedRole = cmbRole.getValue();

        boolean validName = updatedName.matches(usernameRegex);
        boolean validEmail = updatedEmail.matches(emailRegex);

        if (!validName) txtUsername.setStyle("-fx-border-color: red;");
        if (!validEmail) txtEmail.setStyle("-fx-border-color: red;");

        if (validName && validEmail && updatedRole != null) {
            UsersDto updatedUser = new UsersDto(
                    selected.getUser_id(),
                    updatedName,
                    updatedRole,
                    updatedEmail
            );

            try {
                String response =userBO.updateUser(updatedUser);
                new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                userTMObservableList.clear();
                loadAllusersToTable();
                clearFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter valid data!").showAndWait();
        }


    }




}
