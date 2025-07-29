package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.CustomerBO;
import edu.ijse.BakeTrack.db.DBobject;
import edu.ijse.BakeTrack.dto.CustomersDto;
import edu.ijse.BakeTrack.tm.CustomersTM;

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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class CustomerCrudPageController implements Initializable {

    private final String namePattern = "^[A-Za-z ]+$";
    private final String phonePattern = "^(\\+94\\d{9}|94\\d{9}|0\\d{9})$";
    private final String addressPattern = "^[A-Za-z0-9.,/\\-\\s]+$";
    public AnchorPane apMainSIde;

    private CustomerBO customerBO=(CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    private ArrayList<CustomersDto> customerDtoArrayList = new ArrayList<>();
    private ObservableList<CustomersTM> customerTMObservableList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane apCustomerPage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomersTM, String> colCustomerAddress;

    @FXML
    private TableColumn<CustomersTM, String> colCustomerContact;

    @FXML
    private TableColumn<CustomersTM, Integer> colCustomerId;

    @FXML
    private TableColumn<CustomersTM, String> colCustomerName;

    @FXML
    private TableView<CustomersTM> customerTable;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerContact;

    @FXML
    private TextField txtCustomerName;

    public CustomerCrudPageController() throws SQLException, ClassNotFoundException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        loadAllCustomersToTable();
        customerTable.setItems(customerTMObservableList);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
       saveCustomer();
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
       updateCustomer();
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
       deleteCustomer();
    }

    @FXML
    void onTableClick(MouseEvent event) {
        CustomersTM selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtCustomerName.setText(selected.getName());
            txtCustomerAddress.setText(selected.getAddress());
            txtCustomerContact.setText(selected.getContact());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        }
    }

    private void loadAllCustomersToTable() {
        try {
            customerDtoArrayList = customerBO.getAllCustomers();
            for (CustomersDto dto : customerDtoArrayList) {
                customerTMObservableList.add(new CustomersTM(dto.getCustomerID(), dto.getName(), dto.getAddress(), dto.getContact()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshTable() {
        customerTMObservableList.clear();
        loadAllCustomersToTable();
    }

    private void clearFields() {
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerContact.clear();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnGoBack(ActionEvent event) {
        try {
            apCustomerPage.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/OwnerDashboard.fxml"));
            apCustomerPage.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(){
        CustomersTM selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Deletion", ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText("Are you sure you want to delete this customer?");
            confirm.setContentText("This action cannot be undone.");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    String response = customerBO.deleteCustomer(selected.getId());
                    new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                    refreshTable();
                    clearFields();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a customer to delete.").showAndWait();
        }

    }

    public  void updateCustomer(){
        CustomersTM selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            CustomersDto dto = new CustomersDto(
                    selected.getId(),
                    txtCustomerName.getText(),
                    txtCustomerAddress.getText(),
                    txtCustomerContact.getText()
            );
            try {
                String response =customerBO.updateCustomer(dto);
                new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                refreshTable();
                clearFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a customer to update.").showAndWait();
        }
    }

    public void saveCustomer(){
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String contact = txtCustomerContact.getText().trim();

        boolean validName = name.matches(namePattern);
        boolean validAddress = address.matches(addressPattern);
        boolean validContact = contact.matches(phonePattern);

        if (!validName) txtCustomerName.setStyle("-fx-border-color: red;");
        if (!validAddress) txtCustomerAddress.setStyle("-fx-border-color: red;");
        if (!validContact) txtCustomerContact.setStyle("-fx-border-color: red;");

        if (validName && validAddress && validContact) {
            CustomersDto dto = new CustomersDto(name, address, contact);
            try {
                String response = customerBO.addCustomer(dto);
                new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                refreshTable();
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

    public void btnReport(ActionEvent actionEvent) {

        CustomersTM customersTM=customerTable.getSelectionModel().getSelectedItem();

        if(customersTM==null){
            new Alert(Alert.AlertType.ERROR, "select a customer from the table first!").showAndWait();
            return;
        }
        try {
            JasperReport report = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/order_by_cus.jrxml")
            );

            Connection connection = DBobject.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_date", LocalDate.now().toString());
            parameters.put("p_cusid",String.valueOf(customersTM.getId()));
            parameters.put("p_cus_con",String.valueOf(customersTM.getContact()));
            parameters.put("p_cusName",String.valueOf(customersTM.getName()));


            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    report,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnCusAllRB(ActionEvent actionEvent) {
        try {
            JasperReport report = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/Customer_report.jrxml")
            );

            Connection connection = DBobject.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_date", LocalDate.now().toString());
            parameters.put("p_cusid", null);
            parameters.put("p_cus_con", "");
            parameters.put("p_cusName", "All Customers");


            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    report,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error generating All Orders Report: " + e.getMessage()).showAndWait();
        }
    }
}
