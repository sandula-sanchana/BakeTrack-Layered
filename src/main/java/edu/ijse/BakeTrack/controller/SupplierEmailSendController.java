package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.SupplierBO;
import edu.ijse.BakeTrack.dto.SupplierDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class SupplierEmailSendController implements Initializable {

    public AnchorPane apOrderPage;
    private SupplierBO supplierBO=(SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);
    private ObservableList<SupplierDto> supplierDtoObservableList= FXCollections.observableArrayList();

    public SupplierEmailSendController() throws SQLException, ClassNotFoundException {

    }

    @FXML
    private ComboBox<SupplierDto> cmbSupplier;

    @FXML
    private TextArea messageBody;

    @FXML
    private TextField messageSUb;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblAttachmentName;

    private File attachmentFile = null;

    @FXML
    void attachIconCLick(MouseEvent event) {
        Window window = txtEmail.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Attachment");

        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
            attachmentFile = selectedFile;
            lblAttachmentName.setText("Attached: " + selectedFile.getName());
        } else {
            lblAttachmentName.setText("No file selected");
            attachmentFile = null;
        }
    }

    public void setSuplierToCmb(){
        try {
            ArrayList<SupplierDto> supplierIngredientDtos=supplierBO.getAllSuppliersWIthEmail();
            if(supplierIngredientDtos!=null){

                supplierDtoObservableList.addAll(supplierIngredientDtos);

            }else{
                new Alert(Alert.AlertType.ERROR,"not found any Suppliers").showAndWait();
            }
        } catch (SQLException e) {
            System.err.println("sup error get error SESC"  +e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSend(ActionEvent event) {
        String toMail = txtEmail.getText();
        String subject = messageSUb.getText();
        String message = messageBody.getText();

        if (toMail == null || toMail.isEmpty() ||
                subject == null || subject.isEmpty() ||
                message == null || message.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all the fields!").show();
            return;
        }

        String from = "sanchanam249@gmail.com";
        String password = "mhbc dkwe yrjd qblz";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
            mimeMessage.setSubject(subject);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(message);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (attachmentFile != null) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(attachmentFile);
                multipart.addBodyPart(attachmentPart);
            }

            mimeMessage.setContent(multipart);

            Transport.send(mimeMessage);
            new Alert(Alert.AlertType.INFORMATION, "Mail sent successfully!").show();

            attachmentFile = null;
            lblAttachmentName.setText("No file selected");
            txtEmail.clear();
            messageSUb.clear();
            messageBody.clear();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to send mail!").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnGOback(ActionEvent event) {
        try {
            apOrderPage.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/StorekeeperDashboard.fxml"));
            apOrderPage.getChildren().add(ap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
          setSuplierToCmb();
          cmbSupplier.setItems(supplierDtoObservableList);
    }

    public void cmbSelected(ActionEvent actionEvent) {
        SupplierDto selected=cmbSupplier.getValue();
        if(selected!=null){
            txtEmail.setText(selected.getEmail());
        }
    }
}
