package edu.ijse.BakeTrack.controller;


import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.AttendanceBO;
import edu.ijse.BakeTrack.bo.custom.EmployeeBO;
import edu.ijse.BakeTrack.bo.custom.PayrollBO;
import edu.ijse.BakeTrack.bo.custom.SystemSettingBO;
import edu.ijse.BakeTrack.dto.EmployeeDto;
import edu.ijse.BakeTrack.dto.PayrollDto;
import edu.ijse.BakeTrack.tm.AttendanceTM;
import edu.ijse.BakeTrack.tm.EmployeeTM;
import edu.ijse.BakeTrack.tm.PayrollTM;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PayrollCrudPageController implements Initializable {

    public Label txtOTrate;
    private EmployeeBO employeeBO=(EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
    private PayrollBO payrollBO=(PayrollBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYROLL);
    private ObservableList<EmployeeTM>  employeeTMObservableList= FXCollections.observableArrayList();
    private ObservableList<PayrollTM> payrollTMObservableList=FXCollections.observableArrayList();
    private EmployeeTM employeeTM;
    private PayrollTM payrollTM;
    private SystemSettingBO systemSettingBO=(SystemSettingBO)BOFactory.getInstance().getBO(BOFactory.BOType.SYSTEM_SETTING);
    private AttendanceBO attendanceBO=(AttendanceBO) BOFactory.getInstance().getBO(BOFactory.BOType.ATTENDANCE);
    private boolean suppressDatePickerEvent = false;
    private int selected_emp_ID;
    private LocalDate selected_date;

    public PayrollCrudPageController() throws SQLException, ClassNotFoundException {

}

    public Button btnUpdateID;
    public Button btnDeleteID;
    public Button btnSaveID;
    @FXML
    private AnchorPane apPayrollPage;

    @FXML
    private TableColumn<AttendanceTM, Double> colBaseSalary;

    @FXML
    private TableColumn<AttendanceTM,Integer> colEmpID;

    @FXML
    private TableColumn<AttendanceTM, Double> colFullSalary;

    @FXML
    private TableColumn<AttendanceTM, Double> colOvertime;

    @FXML
    private TableColumn<AttendanceTM, LocalDate> colPayDate;

    @FXML
    private TableColumn<AttendanceTM, Integer> colPayrollID;

    @FXML
    private TableColumn<AttendanceTM, String> colStatus;

    @FXML
    private ComboBox<EmployeeTM> comboBoxEmp;

    @FXML
    private ComboBox<String> comboBoxStatus;

    @FXML
    private DatePicker payDatePicker;

    @FXML
    private TableView<PayrollTM> tablePayroll;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private TextField txtFullSalary;

    @FXML
    private TextField txtOvertimeHour;

    @FXML
    private TextField txtOvertimeMin;

    @FXML
    void btnDelete(ActionEvent event) {
         deleteAttendance();
    }

    @FXML
    void btnGoBack(ActionEvent event) {
        apPayrollPage.getChildren().clear();
        try {
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/HRManagerDashboard.fxml"));
            apPayrollPage.getChildren().add(ap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSave(ActionEvent event) {
          savePayroll();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        updatePayroll();
    }

    @FXML
    void cmbClicked(MouseEvent event) {
        btnSaveID.setDisable(false);
        btnDeleteID.setDisable(true);
        btnUpdateID.setDisable(true);
    }

    @FXML
    void tableMouseClick(MouseEvent event) {
        setSelectedOneToFields();
        btnSaveID.setDisable(true);
        btnDeleteID.setDisable(false);
        btnUpdateID.setDisable(false);

    }

    public void setEmpToCmb(){
        try {
            ArrayList<EmployeeDto> employeeDtoArrayList=employeeBO.getAllEmployeeNamesAndIds();
            if(employeeDtoArrayList!=null){
                for (EmployeeDto employeeDto :employeeDtoArrayList) {
                    employeeTM = new EmployeeTM(employeeDto.getEmployee_id(),employeeDto.getName());
                    employeeTMObservableList.addAll(employeeTM);
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"not found any Emp").showAndWait();
            }
        } catch (SQLException e) {
            System.err.println("emp detail error ACPC"  +e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPayrollID.setCellValueFactory(new PropertyValueFactory<>("Payroll_id"));
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("Employee_id"));
        colPayDate.setCellValueFactory(new PropertyValueFactory<>("Pay_Date"));
        colOvertime.setCellValueFactory(new PropertyValueFactory<>("Over_time_hours"));
        colBaseSalary.setCellValueFactory(new PropertyValueFactory<>("Base_salary"));
        colFullSalary.setCellValueFactory(new PropertyValueFactory<>("Full_salary"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        loadAllPayrollsToTable();
        tablePayroll.setItems(payrollTMObservableList);
         setEmpToCmb();
        comboBoxEmp.setItems(employeeTMObservableList);
        comboBoxStatus.getItems().addAll("paid","not paid");

        btnSaveID.setDisable(false);
        btnDeleteID.setDisable(true);
        btnUpdateID.setDisable(true);
        getOTRate();

        comboBoxEmp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getTotalOTtime();
            }
        });
    }

    public void loadAllPayrollsToTable() {

        try {
            ArrayList<PayrollDto> list = payrollBO.getAllPayrolls();
            for (PayrollDto dto : list) {
                   payrollTM = new PayrollTM(dto.getPayroll_id(),dto.getEmployee_id(),dto.getPay_Date(),
                           dto.getOver_time_hours(),dto.getBase_salary(),dto.getFull_salary(),dto.getStatus());
                payrollTMObservableList.add(payrollTM);
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error loading attendance data").showAndWait();
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setSelectedOneToFields(){
        payrollTM = tablePayroll.getSelectionModel().getSelectedItem();
        if (payrollTM != null) {
            for (EmployeeTM etm : employeeTMObservableList) {
                if (etm.getEmployeeID() == payrollTM.getEmployee_id()) {
                    comboBoxEmp.setValue(etm);
                    break;
                }
            }

            comboBoxStatus.setValue(payrollTM.getStatus());

            payDatePicker.setValue(payrollTM.getPay_Date());

            txtOvertimeHour.setText(String.valueOf(payrollTM.getOver_time_hours()));

            txtBaseSalary.setText(String.valueOf(payrollTM.getBase_salary()));

            txtFullSalary.setText(String.valueOf(payrollTM.getFull_salary()));

        }
    }

    public void getOTRate(){
        try {
            int rate=systemSettingBO.getOTRate();
            if(rate>0) {
                txtOTrate.setText(String.valueOf(rate));
            }else {
                new Alert(Alert.AlertType.ERROR,"rate not found").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getBaseSalaryOfEmp(){
        employeeTM=comboBoxEmp.getSelectionModel().getSelectedItem();
        int emp_id=employeeTM.getEmployeeID();
        selected_emp_ID=emp_id;
        try {
            Double base_salary=employeeBO.getSalaryById(emp_id);
            if(base_salary!=null){
                txtBaseSalary.setText(String.valueOf(base_salary));
            }else{
                new Alert(Alert.AlertType.ERROR,"baseSalary not found").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

        public void getTotalOTtime(){
            employeeTM = comboBoxEmp.getSelectionModel().getSelectedItem();
            if (employeeTM == null) return;

            LocalDate date = payDatePicker.getValue();
            if (date == null) return;

            selected_date = date;
            int year = date.getYear();
            int month = date.getMonthValue();

            try {
                Double ot_time = attendanceBO.getEmployeeTotalOTHours(employeeTM.getEmployeeID(), month, year);
                txtOvertimeHour.setText(String.valueOf(ot_time));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



    public void empSelected(ActionEvent actionEvent) {
        employeeTM = comboBoxEmp.getSelectionModel().getSelectedItem();
        if (employeeTM == null) return;

        getBaseSalaryOfEmp();
        if (payDatePicker.getValue() != null) {
            getTotalOTtime();
            calculateFullSalary(employeeTM.getEmployeeID());
        }
    }

    public void dateSelected(ActionEvent actionEvent) {
        if (suppressDatePickerEvent) {
            return;
        }
        if(comboBoxEmp.getSelectionModel().getSelectedItem()!=null) {
            getTotalOTtime();
            calculateFullSalary(selected_emp_ID);
        }else {
            new Alert(Alert.AlertType.ERROR,"select a Emp first").showAndWait();
            suppressDatePickerEvent = true;
            payDatePicker.setValue(null);
            suppressDatePickerEvent = false;
        }
    }


        public void savePayroll() {

            if (comboBoxEmp.getValue() == null || comboBoxStatus.getValue() == null || payDatePicker.getValue() == null
                    || txtOvertimeHour.getText().isEmpty() || txtBaseSalary.getText().isEmpty() || txtFullSalary.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill all required fields").showAndWait();
                return;
            }

            employeeTM = comboBoxEmp.getSelectionModel().getSelectedItem();
            if (employeeTM == null) {
                new Alert(Alert.AlertType.WARNING, "Please select an employee").showAndWait();
                return;
            }

            employeeTM = comboBoxEmp.getValue();
            String status = comboBoxStatus.getValue();
            LocalDate payDate = payDatePicker.getValue();

            double otHours;
            double baseSalary;
            double fullSalary;

            try {
                otHours = Double.parseDouble(txtOvertimeHour.getText().trim());
                baseSalary = Double.parseDouble(txtBaseSalary.getText().trim());
                fullSalary = Double.parseDouble(txtFullSalary.getText().trim());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Please enter valid numbers for salary and overtime").showAndWait();
                return;
            }

            PayrollDto payrollDto = new PayrollDto(
                    employeeTM.getEmployeeID(),
                    payDate,
                    otHours,
                    baseSalary,
                    fullSalary,
                    status
            );

            try {
                String resp = payrollBO.addPayroll(payrollDto);
                new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();
                tablePayroll.getItems().clear();
                loadAllPayrollsToTable();
                clearPayrollFields();
            } catch (SQLException e) {
                System.err.println("Payroll save failed: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }




    public void clearPayrollFields() {
        suppressDatePickerEvent = true;
        comboBoxEmp.getSelectionModel().clearSelection();
        comboBoxStatus.getSelectionModel().clearSelection();
        payDatePicker.setValue(null);
        suppressDatePickerEvent = false;
        txtOvertimeHour.clear();
        txtBaseSalary.clear();
        txtFullSalary.clear();
        employeeTM=null;
        payrollTM=null;
    }

    public void calculateFullSalary(int selected_empID){

        try {
            Double TOTAL_OT_time=attendanceBO.getEmployeeTotalOTHours(selected_empID,selected_date.getMonthValue(),selected_date.getYear());
            if(TOTAL_OT_time>=0.0){
                Double base_salary=Double.parseDouble(txtBaseSalary.getText());
                int ot_rate=Integer.parseInt(txtOTrate.getText());
                Double full_salary=base_salary+(ot_rate*TOTAL_OT_time);
                txtFullSalary.setText(String.valueOf(full_salary));
            }else{
                new Alert(Alert.AlertType.ERROR, "not found").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updatePayroll() {
        PayrollTM selected = tablePayroll.getSelectionModel().getSelectedItem();

        if (selected != null) {
            if (comboBoxEmp.getValue() == null ||  payDatePicker.getValue() == null || txtFullSalary.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill all required fields").showAndWait();
                return;
            }

            try {
                int emp = selected.getEmployee_id();
                LocalDate date = payDatePicker.getValue();
                double base_salary = Double.parseDouble(txtBaseSalary.getText().trim());
                double full_salary = Double.parseDouble(txtFullSalary.getText().trim());
                double over_timeHours = Double.parseDouble(txtOvertimeHour.getText().trim());
                String status=comboBoxStatus.getValue();


                PayrollDto dto = new PayrollDto(
                       emp,date,over_timeHours,base_salary,full_salary,status
                );

                String resp = payrollBO.updatePayroll(dto,selected.getPayroll_id());
                tablePayroll.getItems().clear();
                loadAllPayrollsToTable();
                clearPayrollFields();
                new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();

            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid amount. Please enter a valid number.").showAndWait();
            } catch (SQLException e) {
                System.err.println("Update failed: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a payroll record to update.").showAndWait();
        }
    }

    public void deleteAttendance(){
        PayrollTM selected = tablePayroll.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                String resp =payrollBO.deletePayroll(selected.getPayroll_id());
                tablePayroll.getItems().clear();
                loadAllPayrollsToTable();
                clearPayrollFields();
                new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an attendance record to delete.").showAndWait();
        }
    }


}
