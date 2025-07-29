package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.EmployeeBO;
import edu.ijse.BakeTrack.dto.EmployeeDto;
import edu.ijse.BakeTrack.tm.EmployeeTM;
import edu.ijse.BakeTrack.tm.OrderDetailTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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

public class EmployeeCrudPageController implements Initializable {
    private final String namePattern = "^[A-Za-z ]+$";
    private final String phonePattern = "^(\\+94\\d{9}|94\\d{9}|0\\d{9})$";
    private final String addressPattern = "^[A-Za-z0-9.,/\\-\\s]+$";

    public ComboBox<String> comboBox;
    public Button btnUpid;
    public Button btnDelID;
    public Button btnSAveid;


    private EmployeeBO employeeBO=(EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
    private ArrayList<EmployeeDto> employeeDtoArrayList=new ArrayList<>();
    private ArrayList<EmployeeTM> employeeTMArrayList=new ArrayList<>();
    private EmployeeTM employeeTM;
    private EmployeeDto employeeDto;
    private ObservableList<EmployeeTM> employeeTMObservableList = FXCollections.observableArrayList();

    public  EmployeeCrudPageController() throws SQLException, ClassNotFoundException {

    }

    @FXML
    private TableColumn<EmployeeTM, String> ColAddress;

    @FXML
    private TableView<EmployeeTM> EmpTable;

    @FXML
    private AnchorPane apOrderPage;

    @FXML
    private TableColumn<EmployeeTM,Integer> colEmpID;

    @FXML
    private TableColumn<EmployeeTM, String> colName;

    @FXML
    private TableColumn<EmployeeTM, Integer> colNo;

    @FXML
    private TableColumn<EmployeeTM, String> colROle;

    @FXML
    private TableColumn<EmployeeTM, Double> colSalary;

    @FXML
    private TextField txtAdder;

    @FXML
    private TextField txtConNo;

    @FXML
    private TextField txtNAme;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtSalary;

    @FXML
    void btnDelete(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Confirm Deletion", ButtonType.YES,ButtonType.NO);
        alert.setHeaderText("Are you sure you want to delete this order?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> resp=alert.showAndWait();
        if(resp.isPresent() && resp.get()==ButtonType.YES){
            deleteEmployee();
        }else{
            new Alert(Alert.AlertType.ERROR, "delete customer Cancelled.").show();
        }


    }

    @FXML
    void btnGOback(ActionEvent event) {
        try {
            apOrderPage.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/OwnerDashboard.fxml"));
            apOrderPage.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSave(ActionEvent event) {
        saveEMp();

    }

    @FXML
    void btnUpdate(ActionEvent event) {
        updateEmployee();
        EmpTable.getItems().clear();
        loadAllEmpToTable();
        clearText();

    }

    @FXML
    void tableMouseClick(MouseEvent event) {
        btnUpid.setDisable(false);
        btnDelID.setDisable(false);
        btnSAveid.setDisable(true);
        getSelectedEmployeeToText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeTMObservableList=FXCollections.observableArrayList();
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ColAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colNo.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colROle.setCellValueFactory(new PropertyValueFactory<>("Role"));
        loadAllEmpToTable();
        EmpTable.setItems(employeeTMObservableList);
        comboBox.getItems().addAll("Production_emp","Mobile_sellers");
        btnUpid.setDisable(true);
        btnDelID.setDisable(true);

    }

    public void loadAllEmpToTable(){
        try {
            employeeDtoArrayList=employeeBO.getAllEmployee();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(employeeDtoArrayList!=null){
           for(EmployeeDto employeeDto : employeeDtoArrayList){
               employeeTM=new EmployeeTM(employeeDto.getEmployee_id(),employeeDto.getName(),employeeDto.getAddress(),employeeDto.getSalary(),employeeDto.getContact(),employeeDto.getROle());
               employeeTMObservableList.add(employeeTM);
           }

       }else {
            new Alert(Alert.AlertType.INFORMATION,"Employees not found").showAndWait();
        }
    }

    public void saveEMp(){
        String cusName=txtNAme.getText();
        String cusAddress=txtAdder.getText();
        String cusSalary=txtSalary.getText();
        String cusCon=txtConNo.getText();
        String role=comboBox.getValue();

        boolean Vname=cusName.matches(namePattern);
        boolean Vno=cusCon.matches(phonePattern);
        boolean Vaddr=cusAddress.matches(addressPattern);

        if (!Vname) txtNAme.setStyle(txtNAme.getStyle() + ";-fx-border-color: red;");
        if (!Vaddr) txtAdder.setStyle(txtAdder.getStyle() + ";-fx-border-color: red;");
        if (!Vno) txtConNo.setStyle(txtConNo.getStyle() + ";-fx-border-color: red;");

        if(Vname && Vaddr && Vno){
             employeeDto=new EmployeeDto(cusName,cusAddress,Double.valueOf(cusSalary),cusCon,role);
            try {
                String resp=employeeBO.addEmployee(employeeDto);
                new Alert(Alert.AlertType.INFORMATION,resp).showAndWait();
                employeeTMObservableList.clear();
                loadAllEmpToTable();
                clearText();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"enter valid data").showAndWait();
        }


    }

    public void clickOnText(MouseEvent mouseEvent) {
        btnUpid.setDisable(false);
        btnDelID.setDisable(true);
        btnSAveid.setDisable(false);
    }
    public void clearText(){
        txtNAme.setText("");
        txtAdder.setText("");
        txtSalary.setText("");
        txtConNo.setText("");
        comboBox.setValue("");
    }

    public void getSelectedEmployeeToText() {
        EmployeeTM selected = EmpTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtNAme.setText(selected.getName());
            txtAdder.setText(selected.getAddress());
            txtSalary.setText(String.valueOf(selected.getSalary()));
            txtConNo.setText(selected.getContact());
            comboBox.setValue(selected.getRole());
        }
    }

    public void updateEmployee() {
        EmployeeTM selected = EmpTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                EmployeeDto dto = new EmployeeDto(
                        selected.getEmployeeID(),
                        txtNAme.getText(),
                        txtAdder.getText(),
                        Double.parseDouble(txtSalary.getText()),
                        txtConNo.getText(),
                        comboBox.getValue()
                );
                String resp = employeeBO.updateEmployee(dto);
                EmpTable.getItems().clear();
                loadAllEmpToTable();
                clearText();
                new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid Input").showAndWait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an employee first.").showAndWait();
        }
    }

        public void deleteEmployee() {
            EmployeeTM selected = EmpTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    String resp=employeeBO.deleteEmployee(selected.getEmployeeID());
                    EmpTable.getItems().clear();
                    loadAllEmpToTable();
                    clearText();
                    new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please select an employee to delete.").showAndWait();
            }
        }

    }



