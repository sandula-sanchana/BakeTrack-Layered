package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.UserBO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogInPageController implements Initializable {
    public Button loginButton;
    private UserBO userBO=(UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public  LogInPageController() throws SQLException, ClassNotFoundException {

    }

    @FXML
    private AnchorPane apLoginPage;

    @FXML
    private TextField txtUserNameSignUpPage;

    @FXML
    private TextField txtUserPasswordSignUpPage;

    public void setUserName(String userName) {
        txtUserNameSignUpPage.setText(userName);
    }

    public void setPassword(String password) {
        txtUserPasswordSignUpPage.setText(password);
    }

    @FXML
    void btnLogInloginPage(ActionEvent event) {

        String userName=txtUserNameSignUpPage.getText();
        String password=txtUserPasswordSignUpPage.getText();
        String fxmlPath=null;

        try {
            String role=userBO.authenticater(userName,password);

            if(role!=null){
                if(role.equals("Admin")){
                    fxmlPath="/View/OwnerDashboard.fxml";
                }else if (role.equals("HRManager")){
                    fxmlPath="/View/HRManagerDashboard.fxml";
                }else if(role.equals("StoreManager")){
                    fxmlPath="/View/StorekeeperDashboard.fxml";
                }


                if(fxmlPath!=null){
                    apLoginPage.getChildren().clear();
                    try {
                        AnchorPane ap=FXMLLoader.load(getClass().getResource(fxmlPath));
                        apLoginPage.getChildren().add(ap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"can't load dashboard").showAndWait();
                }

            }else{
                new Alert(Alert.AlertType.ERROR,"Wrong Credentials!! try again").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSignUploginPage(ActionEvent event) {
        try {
            apLoginPage.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/SignupPage.fxml"));
            apLoginPage.getChildren().add(ap);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"can't find signup page").show();
            e.printStackTrace();
        }
    }

    @FXML
    void hlForgotPwdLPage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ForgotPassword.fxml"));
            Parent root = fxmlLoader.load();
            ForgotPasswordController forgotPasswordController=fxmlLoader.getController();
            forgotPasswordController.setLoginPageController(this);
            Stage stage = new Stage();
            stage.setTitle("Forgot Password - BakeTrack");
            stage.setScene(new Scene(root, 1920, 1080));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnMouseEntered(event -> {
            loginButton.setCursor(Cursor.OPEN_HAND);
        });

        loginButton.setOnMouseExited(event -> {
            loginButton.setCursor(Cursor.DEFAULT);
        });
    }
}