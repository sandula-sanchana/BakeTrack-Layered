package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.custom.AttendanceBO;
import edu.ijse.BakeTrack.bo.custom.EmployeeBO;
import edu.ijse.BakeTrack.bo.custom.PayrollBO;
import edu.ijse.BakeTrack.bo.custom.SystemSettingBO;
import edu.ijse.BakeTrack.dto.AttendanceCount;
import edu.ijse.BakeTrack.bo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class HRManagerDashboardController implements Initializable {
    public LineChart attendanceLIneChart;
    public PieChart totalEmpPie;
    public TextField txtOT;
    public PieChart salaryStatusPie;
    public Button btnUpID;
    public Button btnLogoutID;
    public Button btnSendMailID;
    public Button btnSetMobileSellerId;
    public Button btnAttendanceID;
    public Button btnPayrollID;
    private AttendanceBO attedanceBo=(AttendanceBO) BOFactory.getInstance().getBO(BOFactory.BOType.ATTENDANCE);
    private EmployeeBO employeeBO=(EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
    private PayrollBO payrollBO=(PayrollBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYROLL);
    private SystemSettingBO systemSettingBO=(SystemSettingBO) BOFactory.getInstance().getBO(BOFactory.BOType.SYSTEM_SETTING);

    @FXML
    private AnchorPane HMDap;

    public HRManagerDashboardController() throws SQLException, ClassNotFoundException {
    }

    @FXML
    void btnAttendance(ActionEvent event) {
           setPages("/View/AttendanceCrudPage.fxml");
    }

    @FXML
    void btnPAyroll(ActionEvent event) {
             setPages("/View/PayrollCrudPage.fxml");
    }

    public void setPages(String pageLocation){
        try {
            HMDap.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource(pageLocation));
            HMDap.getChildren().add(ap);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Page Load Error");
            alert.setHeaderText("Could not load page: " + pageLocation);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }


    public void btnSetMobileSeller(ActionEvent actionEvent) {
        setPages("/View/setMobileSellerPage.fxml");
    }

    public void btnLogout(ActionEvent actionEvent) {
        setPages("/View/LogInPage.fxml");
    }

    public void loadLineChart(){
        try {
            XYChart.Series<String,Number> series = new XYChart.Series<>();
            series.setName("Attendance overview");

            List<AttendanceCount> data = attedanceBo.getAttendanceEachDay();
            for (AttendanceCount attendanceCount : data) {
                series.getData().add(new XYChart.Data<>(attendanceCount.getDate().toString(),attendanceCount.getCount()));
            }
             attendanceLIneChart.setTitle("Attendance Over Time");
              attendanceLIneChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         loadLineChart();
         loadEmpPieChart();
         loadStatusPayrollPie();
         loadOtrate();


        applyHoverEffect(btnPayrollID, "#3498DB", "#2980B9");
        applyHoverEffect(btnAttendanceID, "#3498DB", "#2980B9");
        applyHoverEffect(btnSetMobileSellerId, "#3498DB", "#2980B9");
        applyHoverEffect(btnSendMailID, "#3498DB", "#2980B9");

        applyHoverEffect(btnLogoutID, "#3498DB", "#E74C3C");

        applyHoverEffect(btnUpID, "#3498DB", "#27AE60");


    }


    private void applyHoverEffect(Button button, String originalBgColor, String hoverBgColor) {

        String baseStyle = "-fx-background-color: " + originalBgColor + ";" +
                "-fx-background-radius: " + getButtonRadius(button) + ";" +
                "-fx-font-size: " + getButtonFontSize(button) + ";";

        button.setStyle(baseStyle);


        button.setCursor(javafx.scene.Cursor.HAND);


        String hoverStyle = "-fx-background-color: " + hoverBgColor + ";" +
                "-fx-background-radius: " + getButtonRadius(button) + ";" +
                "-fx-font-size: " + getButtonFontSize(button) + ";" +
                "-fx-scale-x: 1.03;" +
                "-fx-scale-y: 1.03;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0.6, 0, 0);";

        button.setOnMouseEntered(event -> {
            button.setStyle(hoverStyle);
        });


        button.setOnMouseExited(event -> {
            button.setStyle(baseStyle);
        });
    }


    private String getButtonRadius(Button button) {
        if (button == btnLogoutID) {
            return "20";
        } else if (button == btnUpID) {
            return "8";
        }
        return "9";
    }


    private String getButtonFontSize(Button button) {
        if (button == btnLogoutID) {
            return "20px";
        }
        return "24px";
    }

    public void loadEmpPieChart() {

        try {

            Map<String, Integer> employeeCountMap = employeeBO.getEmpCount();
            if (employeeCountMap != null) {
                ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
                for (Map.Entry<String, Integer> entry : employeeCountMap.entrySet()) {
                    String label = entry.getKey() + " (" + entry.getValue() + ")";
                    pieData.add(new PieChart.Data(label, entry.getValue()));
                }
                totalEmpPie.setData(pieData);
                totalEmpPie.setTitle("Working Employees ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadStatusPayrollPie(){

        try {
            Map<String,Integer> statusCounts=new HashMap<>();
            ObservableList<PieChart.Data> pieData=FXCollections.observableArrayList();

            statusCounts=payrollBO.getPayrollStatus();

            if (statusCounts!=null){
               for(Map.Entry<String,Integer> entry: statusCounts.entrySet()){
                   String label = entry.getKey() + " (" + entry.getValue() + ")";
                   pieData.add(new PieChart.Data(label,entry.getValue()));
               }
               salaryStatusPie.getData().clear();
               salaryStatusPie.setData(pieData);
               LocalDate date=LocalDate.now();
               int month=date.getMonthValue();
               int year=date.getYear();
               salaryStatusPie.setTitle("Salary Status of : " + year+" - "+month);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(ActionEvent actionEvent) {
        setPages("/View/SendEmailAll.fxml");
    }

    public void btnOTupBtn(ActionEvent actionEvent) {
        if ((txtOT.getText().isEmpty())){
            return;
        }
        try {
            int ot_rate=Integer.parseInt(txtOT.getText());
            String resp=systemSettingBO.setOTRate(ot_rate);
            new Alert(Alert.AlertType.INFORMATION,resp).showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void loadOtrate(){
        try {
            int otrate=systemSettingBO.getOTRate();
            String otrate_st=String.valueOf(otrate);
            txtOT.setText(otrate_st);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

