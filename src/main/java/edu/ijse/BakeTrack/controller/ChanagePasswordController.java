package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.UserBO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ChanagePasswordController {
    private UserBO userBO=(UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    private ForgotPasswordController forgotPasswordController;
    public PasswordField txtPassword;
    public PasswordField txtPasswordreEnter;
    public AnchorPane apUpass;
    public String role;

    public ChanagePasswordController() throws SQLException, ClassNotFoundException {

    }

    public void btnUpdatePass(ActionEvent actionEvent) {
        if(txtPassword.getText().isEmpty() || txtPasswordreEnter.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"enter a new password first").showAndWait();
            return;
        }

        if(!txtPasswordreEnter.getText().equals(txtPassword.getText())){
            new Alert(Alert.AlertType.ERROR,"passwords do not match").showAndWait();
            return;
        }

        if(role!=null){
            try {
                String resp=userBO.updatePasswordByRole(role,txtPassword.getText());
                new Alert(Alert.AlertType.INFORMATION,resp).show();
                if (resp.contains("Password updated successfully.")){
                    ((Stage) apUpass.getScene().getWindow()).close();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"no role selected").show();
        }


    }

    public void getRole(String role){
        this.role=role;
    }
}
