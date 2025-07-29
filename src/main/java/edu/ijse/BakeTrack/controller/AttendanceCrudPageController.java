package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.AttendanceBO;
import edu.ijse.BakeTrack.bo.custom.EmployeeBO;
import edu.ijse.BakeTrack.dto.AttendanceDto;
import edu.ijse.BakeTrack.dto.EmployeeDto;
import edu.ijse.BakeTrack.tm.AttendanceTM;
import edu.ijse.BakeTrack.tm.EmployeeTM;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AttendanceCrudPageController implements Initializable {

    public TextField txtCoutMIn;
    public DatePicker datePicker;
    private ObservableList<AttendanceTM> attendanceTMObservableArray= FXCollections.observableArrayList();
    private ObservableList<EmployeeTM> employeeTMObservableList=FXCollections.observableArrayList();
    private AttendanceBO attendanceBO=(AttendanceBO) BOFactory.getInstance().getBO(BOFactory.BOType.ATTENDANCE);
    private EmployeeBO employeeBO=(EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
    private EmployeeTM employeeTM;
    private AttendanceTM attendanceTM;

    public AttendanceCrudPageController() throws SQLException, ClassNotFoundException {
    }

    @FXML
    private TableView<AttendanceTM> TableAttendance;

    @FXML
    private AnchorPane apOrderPage;

    @FXML
    private Button btnDelID;

    @FXML
    private Button btnSAveid;

    @FXML
    private Button btnUpid;

    @FXML
    private TableColumn<AttendanceTM, LocalDate> colAttendDate;

    @FXML
    private TableColumn<AttendanceTM, LocalTime> colCheckIN;

    @FXML
    private TableColumn<AttendanceTM, LocalTime> colCheckOut;

    @FXML
    private TableColumn<AttendanceTM, Integer> colEmpID;

    @FXML
    private TableColumn<AttendanceTM, String> colStatus;

    @FXML
    private ComboBox<EmployeeTM> comboBoxEmp;

    @FXML
    private ComboBox<String> comboBoxStatus;

    @FXML
    private TextField txtCinHOur;

    @FXML
    private TextField txtCinMIn;

    @FXML
    private TextField txtCoutHour;

    @FXML
    void btnDelete(ActionEvent event) {

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Confirm Deletion", ButtonType.YES,ButtonType.NO);
        alert.setHeaderText("Are you sure you want to delete this Attendance?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> resp=alert.showAndWait();
        if(resp.isPresent() && resp.get()==ButtonType.YES){
            deleteAttendance();
        }else{
            new Alert(Alert.AlertType.ERROR, "delete Attendance Cancelled.").show();
        }

    }

    @FXML
    void btnGOback(ActionEvent event) {
         apOrderPage.getChildren().clear();
        try {
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/HRManagerDashboard.fxml"));
            apOrderPage.getChildren().add(ap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSave(ActionEvent event) {
        saveAttendance();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
         updateAttendance();
    }

    @FXML
    void tableMouseClick(MouseEvent event) {
       setSelectedOneToFields();
       btnSAveid.setDisable(true);
       btnUpid.setDisable(false);
       btnDelID.setDisable(false);
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {

        colEmpID.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        colAttendDate.setCellValueFactory(new PropertyValueFactory<>("AttendDate"));
        colCheckIN.setCellValueFactory(new PropertyValueFactory<>("Check_in_time"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("Check_out_time"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        TableAttendance.setItems(attendanceTMObservableArray);
        setEmpToCmb();
        loadAllAttendanceToTable();
        comboBoxEmp.setItems(employeeTMObservableList);
        comboBoxStatus.getItems().addAll("present","absent");

        btnSAveid.setDisable(false);
        btnDelID.setDisable(true);
        btnUpid.setDisable(true);

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

    public void loadAllAttendanceToTable() {

        try {
            ArrayList<AttendanceDto> list = attendanceBO.getAllAttendance();
            for (AttendanceDto dto : list) {
                AttendanceTM tm = new AttendanceTM(dto.getEmployee_id(), dto.getAttend_date(), dto.getCheck_in_time(), dto.getCheck_out_time(), dto.getStatus());
                attendanceTMObservableArray.add(tm);
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error loading attendance data").showAndWait();
            e.printStackTrace();
        }
    }

    public void saveAttendance(){

        if (comboBoxEmp.getValue() == null || comboBoxStatus.getValue() == null || datePicker.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields").showAndWait();
            return;
        }

        employeeTM=comboBoxEmp.getValue();
        String status=comboBoxStatus.getValue();

        String check_in_String = String.format("%02d:%02d", Integer.parseInt(txtCinHOur.getText().trim()), Integer.parseInt( txtCinMIn.getText().trim()));
        String check_out_String = String.format("%02d:%02d", Integer.parseInt(txtCoutHour.getText().trim()), Integer.parseInt(txtCoutMIn.getText().trim()));

        System.out.println(check_out_String);
        System.out.println(check_in_String);

        LocalTime cinTime=LocalTime.parse(check_in_String);
        LocalTime coutTime=LocalTime.parse(check_out_String);
        LocalDate attendDAte=datePicker.getValue();

        AttendanceDto attendanceDto=new AttendanceDto(employeeTM.getEmployeeID(),attendDAte,cinTime,coutTime,status);

        try {
            String resp=attendanceBO.saveAttendance(attendanceDto);
            new Alert(Alert.AlertType.INFORMATION,resp).showAndWait();
            TableAttendance.getItems().clear();
            loadAllAttendanceToTable();
            clearTexrs();
        } catch (SQLException e) {
            System.err.println("attendance save fail "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setSelectedOneToFields(){
        attendanceTM = TableAttendance.getSelectionModel().getSelectedItem();
        if (attendanceTM != null) {
            for (EmployeeTM emp : employeeTMObservableList) {
                if (emp.getEmployeeID() == attendanceTM.getEmployeeID()) {
                    comboBoxEmp.setValue(emp);
                    break;
                }
            }

            comboBoxStatus.setValue(attendanceTM.getStatus());

            datePicker.setValue(attendanceTM.getAttendDate());

            txtCinHOur.setText(String.format("%02d", attendanceTM.getCheck_in_time().getHour()));
            txtCinMIn.setText(String.format("%02d", attendanceTM.getCheck_in_time().getMinute()));

            txtCoutHour.setText(String.format("%02d", attendanceTM.getCheck_out_time().getHour()));
            txtCoutMIn.setText(String.format("%02d", attendanceTM.getCheck_out_time().getMinute()));

        }
    }

    public  void clearTexrs(){
        comboBoxEmp.setValue(null);
        comboBoxStatus.setValue(null);
        txtCinHOur.setText("");
        txtCinMIn.setText("");
        txtCoutHour.setText("");
        txtCoutMIn.setText("");
        datePicker.setValue(null);

    }

   public void deleteAttendance(){
       AttendanceTM selected = TableAttendance.getSelectionModel().getSelectedItem();
       if (selected != null) {
           try {
               String resp = attendanceBO.deleteAttendance(selected.getEmployeeID(), selected.getAttendDate());
               TableAttendance.getItems().clear();
               loadAllAttendanceToTable();
               clearTexrs();
               new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       } else {
           new Alert(Alert.AlertType.WARNING, "Please select an attendance record to delete.").showAndWait();
       }
   }

    public void updateAttendance() {
        AttendanceTM selected = TableAttendance.getSelectionModel().getSelectedItem();

        if (selected != null) {
            if (comboBoxEmp.getValue() == null || comboBoxStatus.getValue() == null || datePicker.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please fill all required fields").showAndWait();
                return;
            }

            try {
                String check_in_String = String.format("%02d:%02d",
                        Integer.parseInt(txtCinHOur.getText().trim()),
                        Integer.parseInt(txtCinMIn.getText().trim()));

                String check_out_String = String.format("%02d:%02d",
                        Integer.parseInt(txtCoutHour.getText().trim()),
                        Integer.parseInt(txtCoutMIn.getText().trim()));

                LocalTime cinTime = LocalTime.parse(check_in_String);
                LocalTime coutTime = LocalTime.parse(check_out_String);
                LocalDate attendDate = datePicker.getValue();

                EmployeeTM emp = comboBoxEmp.getValue();
                String status = comboBoxStatus.getValue().trim();

                AttendanceDto dto = new AttendanceDto(
                        emp.getEmployeeID(),
                        attendDate,
                        cinTime,
                        coutTime,
                        status
                );

                String resp = attendanceBO.updateAttendance(dto);
                TableAttendance.getItems().clear();
                loadAllAttendanceToTable();
                clearTexrs();
                new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();

            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid time input. Please enter valid hours and minutes.").showAndWait();
            } catch (SQLException e) {
                System.err.println("Update failed: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an attendance record to update.").showAndWait();
        }
    }

    public void cmbClicked(MouseEvent mouseEvent) {
        btnSAveid.setDisable(false);
        btnUpid.setDisable(false);
        btnDelID.setDisable(true);
    }
}
