package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.UserBO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {

    public AnchorPane apForgotPass;
    private LogInPageController loginPageController;
    private String sentCode;
    private String toEmail;
    private UserBO userBO=(UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public ForgotPasswordController() throws SQLException, ClassNotFoundException {

    }

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private Label lblEmail;

    @FXML
    private TextField txtVerificationCode;

    @FXML
    void btnGetCode(ActionEvent event) {

        if (lblEmail.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"SELECT A ROLE FIRST!").showAndWait();
            return;

        }
        sentCode = generateVerificationCode();
        String from = "sanchanam249@gmail.com";
        String password = "mhbc dkwe yrjd qblz";

                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");

                Session session = Session.getInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(from, password);
                            }
                        });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(lblEmail.getText()));
                    message.setSubject("Your BakeTrack Code");
                    message.setText("Your verification code is: " + sentCode);
                    Transport.send(message);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Verification code sent to email.");
                    alert.showAndWait();
                } catch (MessagingException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Failed to send email. Please check your internet or try again later.").showAndWait();
                }
            }

    public void setLoginPageController(LogInPageController controller) {
        this.loginPageController = controller;
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      cmbRole.getItems().addAll("Admin","StoreManager","HRManager");
    }

    private String generateVerificationCode() {
        int code = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }

    public void cmbCLick(ActionEvent actionEvent) {
       String roleSelected=cmbRole.getValue();
        try {
            String emailGot=userBO.getEmailByRole(roleSelected);
            if (emailGot!=null){
                lblEmail.setText(emailGot);
            }else {
                lblEmail.setText("");
                new Alert(Alert.AlertType.WARNING, "No user found for this role!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnChangePassword(ActionEvent actionEvent) {
        if(txtVerificationCode.getText().equals(sentCode)) {
            String role = cmbRole.getValue();
            if (role == null || role.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please select a role!").showAndWait();
                return;
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ChangePassword.fxml"));
                AnchorPane ap = loader.load();

                ChanagePasswordController controller = loader.getController();
                controller.getRole(role);

                apForgotPass.getChildren().clear();
                apForgotPass.getChildren().add(ap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{

        }
    }

    public void btnLogIN(ActionEvent actionEvent) throws SQLException {
        if (txtVerificationCode.getText().equals(sentCode)) {
            if (loginPageController != null) {
                String role = cmbRole.getValue();
                if (role == null || role.isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Please select a role!").showAndWait();
                    return;
                }
                Stage stage = (Stage) txtVerificationCode.getScene().getWindow();
                stage.close();


                String username=userBO.getUserNameByRole(role);
                String password = userBO.getPasswordByRole(role);
                if (username == null || password == null) {
                    new Alert(Alert.AlertType.ERROR, "User not found for the selected role!").showAndWait();
                    return;
                }

                loginPageController.setUserName(username);
                loginPageController.setPassword(password);

                loginPageController.btnLogInloginPage(new ActionEvent());
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Incorrect verification code!").showAndWait();
        }
    }

    public void btnVerify(ActionEvent actionEvent) {
        if (txtVerificationCode.getText().equals(sentCode)) {
            new Alert(Alert.AlertType.INFORMATION, "Code verified! You can now reset your password.").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Incorrect verification code!").showAndWait();
        }
    }
}
