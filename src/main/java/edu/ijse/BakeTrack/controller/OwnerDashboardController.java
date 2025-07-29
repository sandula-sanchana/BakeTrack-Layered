package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.*;
import edu.ijse.BakeTrack.dto.OrderTrendDto;

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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class OwnerDashboardController implements Initializable {

    public AnchorPane apOwnerDB;
    public LineChart <String, Number>lineChart;
    public PieChart paymentPieChart;
    public PieChart employeePieChart;
    public Button btnMusersID;
    public Button BtnSetPid;
    public Button BTNsdID;
    public Button btnViid;
    public Button btnSupID;
    public Button BTNsPid;
    public Button btneID;
    public Button btnOid;
    public Button btnCusid;
    public Button btnLogout;
    public AnchorPane rightpane;
    public PieChart vehiclePie;
    private ProductBO productBO=(ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);
    private OrderBO orderBO=(OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);
    private PaymentsBO paymentsBO=(PaymentsBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENTS);
    private EmployeeBO employeeBO=(EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
    private VehicleBO vehicleBO=(VehicleBO) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLE);

    public OwnerDashboardController() throws SQLException, ClassNotFoundException {
    }

    @FXML
    void btnCus(ActionEvent event) {
        setPages("/View/CustomerCrudPage.fxml");
    }

    @FXML
    void btnEmp(ActionEvent event) {
        setPages("/View/EmployeeCrudPage.fxml");
    }

    @FXML
    void btnOrder(ActionEvent event) {
        setPages("/View/AddOrderPage.fxml");
    }

    @FXML
    void btnPay(ActionEvent event) {
        setPages("/View/setPayments.fxml");
    }

    @FXML
    public void btnReports(ActionEvent event) {
    }

    @FXML
    public void btnDelivery(ActionEvent event) {
        setPages("/View/setDeliveryPage.fxml");
    }

    @FXML
    void btnSup(ActionEvent event) {
        setPages("/View/SupplierCrudPage.fxml");
    }

    @FXML
    void btnVehicle(ActionEvent event) {
        setPages("/View/VehicleCrudPage.fxml");
    }

    public void setPages(String pageLocation){
        try {
            apOwnerDB.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource(pageLocation));
            apOwnerDB.getChildren().add(ap);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Page Load Error");
            alert.setHeaderText("Could not load page: " + pageLocation);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }


    public void btnUser(ActionEvent actionEvent) {

        setPages("/View/UserCrudPage.fxml");
    }

    public void btnSetProduction(ActionEvent actionEvent) {
        setPages("/View/setProductionCrudPage.fxml");
    }

    public void btnLogout(ActionEvent actionEvent) {
        setPages("/View/LogInPage.fxml");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLineChart();
        loadPaymentPieChart();
        loadEmpPieChart();
        loadVehiclePieChart();

            applyHoverEffect(btnCusid, "#3498DB", "#2980B9");
            applyHoverEffect(btnOid, "#3498DB", "#2980B9");
            applyHoverEffect(btneID, "#3498DB", "#2980B9");
            applyHoverEffect(BTNsPid, "#3498DB", "#2980B9");
            applyHoverEffect(btnSupID, "#3498DB", "#2980B9");
            applyHoverEffect(btnViid, "#3498DB", "#2980B9");
            applyHoverEffect(BTNsdID, "#3498DB", "#2980B9");
            applyHoverEffect(BtnSetPid, "#3498DB", "#2980B9");
            applyHoverEffect(btnMusersID, "#3498DB", "#2980B9");


            applyHoverEffect(btnLogout, "#3498DB", "#E74C3C");


        }

        private void applyHoverEffect(Button button, String originalBgColor, String hoverBgColor) {
            String baseStyle = "-fx-background-color: " + originalBgColor + ";" +
                    "-fx-background-radius: " + getButtonRadius(button) + ";" +
                    "-fx-font-size: " + getButtonFontSize(button) + ";";

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
            if (button == btnLogout) {
                return "20";
            }
            return "7";
        }


        private String getButtonFontSize(Button button) {
            if (button == btnLogout) {
                return "18px";
            }
            return "22px";
        }



    public void loadLineChart(){
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Orders Over Time");

            List<OrderTrendDto> data = orderBO.getOrderTrends();
            for (OrderTrendDto d : data) {
                series.getData().add(new XYChart.Data<>(d.getDate(), d.getCount()));
            }
            lineChart.setTitle("Orders Over Time");
            lineChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadVehiclePieChart() {
        try {
            Map<String, Integer> vehicleStatusMap = vehicleBO.getVehicleStatusCount();
            if (vehicleStatusMap != null) {
                ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
                for (Map.Entry<String, Integer> entry : vehicleStatusMap.entrySet()) {
                    String label = entry.getKey() + " (" + entry.getValue() + ")";
                    pieData.add(new PieChart.Data(label, entry.getValue()));
                }
                vehiclePie.setData(pieData);
                vehiclePie.setTitle("Vehicle Status");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Loading Vehicle Pie Chart");
            alert.setHeaderText("An error occurred while loading vehicle status data.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    public void loadPaymentPieChart() {
        try {
            Map<String, Integer> paymentCountMap = paymentsBO.getPaymentCount();
            if (paymentCountMap != null) {
                ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
                for (Map.Entry<String, Integer> entry : paymentCountMap.entrySet()) {
                    String label = entry.getKey() + " (" + entry.getValue() + ")";
                    pieData.add(new PieChart.Data(label, entry.getValue()));
                }
                paymentPieChart.setData(pieData);
                paymentPieChart.setTitle("Payments");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Loading Payment Pie Chart");
            alert.setHeaderText("An error occurred while loading payment data.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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
                employeePieChart.setData(pieData);
                employeePieChart.setTitle("Working Employees");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Loading Employee Pie Chart");
            alert.setHeaderText("An error occurred while loading employee data.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}