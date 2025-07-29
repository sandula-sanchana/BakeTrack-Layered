package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.UserBO;
import edu.ijse.BakeTrack.dto.UsersDto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupPageController implements Initializable {
    public ComboBox<String> cmbRole;
    public AnchorPane apSignUP;
    private UserBO userBO=(UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    private String usernameRegex = "^[a-zA-Z0-9_]{3,20}$";
    private String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

    public SignupPageController() throws SQLException, ClassNotFoundException {

    }

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField reEnterPAss;

    @FXML
    private TextField username;

    @FXML
    void btnSignUp(ActionEvent event) {
        if(username==null || password==null || email==null || reEnterPAss==null || cmbRole.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"fill fields first").showAndWait();
            return;
        }
        if (!password.getText().equals(reEnterPAss.getText())){
            new Alert(Alert.AlertType.ERROR,"passwords do not match").showAndWait();
            return;
        }

        String name = username.getText();
        String passwords = password.getText();
        String emailEntered=email.getText();
        String role=cmbRole.getValue();

        boolean validName = name.matches(usernameRegex);
        boolean validEmail = emailEntered.matches(emailRegex);

        if (!validName) username.setStyle("-fx-border-color: red;");
        if (!validEmail) email.setStyle("-fx-border-color: red;");


        if (validName && validEmail) {
            UsersDto dto = new UsersDto(name,passwords,role,emailEntered);
            try {
                String response = userBO.addUser(dto);
                new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         cmbRole.getItems().addAll("Admin","StoreManager","HRManager");
    }

    public  void clearFields(){
        username.clear();
        password.clear();
        reEnterPAss.clear();
        email.clear();
        cmbRole.setValue(null);
    }

    public void btnLOginSignup(ActionEvent actionEvent) {
        try {
            apSignUP.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/LogInPage.fxml"));
            apSignUP.getChildren().add(ap);
        } catch (IOException e) {
           new Alert(Alert.AlertType.ERROR,"page not found").showAndWait();
        }
    }
}
