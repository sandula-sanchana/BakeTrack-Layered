package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.IngredientBO;
import edu.ijse.BakeTrack.bo.custom.ProductBO;
import edu.ijse.BakeTrack.dto.IngredientDto;
import edu.ijse.BakeTrack.dto.ProductDto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.paint.CycleMethod;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StorekeeperDashboardController implements Initializable {
    public BarChart ingBarChart;
    public Label totalProducts;
    public Label totalIng;
    public Button btnLOgOutid;
    public Button btnESid;
    public Button BTNpIid;
    public Button btnPid;
    public Button btnIngID;
    public AnchorPane sidebarPane;
    public AnchorPane rightpain;
    public Button btnSIid;
    private ProductBO productBO=(ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);
    private IngredientBO ingredientBO=(IngredientBO) BOFactory.getInstance().getBO(BOFactory.BOType.INGREDIENT);

    public PieChart pieChart;
    @FXML
    private AnchorPane apSKDashBoard;

    public StorekeeperDashboardController() throws SQLException, ClassNotFoundException {
    }

    @FXML
    void btnIngredients(ActionEvent event) {
        setPages("/View/IngredientCrudPage.fxml");
    }

    @FXML
    void btnProduct(ActionEvent event) {
        setPages("/View/ProductCrudPage.fxml");
    }

    @FXML
    void btnProductIng(ActionEvent event) {
        setPages("/View/ProductIngredientPage.fxml");
    }

    public void setPages(String pageLocation){
        try {
            apSKDashBoard.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource(pageLocation));
            apSKDashBoard.getChildren().add(ap);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Page Load Error");
            alert.setHeaderText("Could not load page: " + pageLocation);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void btnLogout(ActionEvent actionEvent) {
        setPages("/View/LogInPage.fxml");
    }

    public void loadPieChart() {
        try {
            List<ProductDto> items = productBO.getAllProducts();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            int totalQuantity = 0;

            for (ProductDto item : items) {
                String label = item.getName() + " (" + item.getQuantity() + ")";
                pieChartData.add(new PieChart.Data(label, item.getQuantity()));
                totalQuantity += item.getQuantity();
            }

            pieChart.setData(pieChartData);
            pieChart.setTitle("Product Quantity Overview (" + totalQuantity + ")");

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Loading Product Pie Chart");
            alert.setHeaderText("An error occurred while loading product data.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         applyBackgroundGradients();
         loadPieChart();
         loadToBarChart();
         countProducts();
         countINg();



        applyHoverEffect(btnIngID, "#3498DB", "#2980B9");
        applyHoverEffect(btnPid, "#3498DB", "#2980B9");
        applyHoverEffect(BTNpIid, "#3498DB", "#2980B9");
        applyHoverEffect(btnESid, "#3498DB", "#2980B9");
        applyHoverEffect(btnSIid, "#3498DB", "#2980B9");

        applyHoverEffect(btnLOgOutid, "#3498DB", "#E74C3C");

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
        if (button == btnLOgOutid) {
            return "20";
        }
        return "9";
    }


    private String getButtonFontSize(Button button) {
        if (button == btnLOgOutid) {
            return "20px";
        }
        return "24px";
    }

    private void applyBackgroundGradients() {

        Color sidebarStart = Color.web("#37474F");
        Color sidebarEnd = Color.web("#2A363B");
        LinearGradient sidebarGradient = new LinearGradient(
                0, 0, 0, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, sidebarStart),
                new Stop(1, sidebarEnd)
        );
        if (sidebarPane != null) {
            sidebarPane.setBackground(new Background(new BackgroundFill(sidebarGradient, null, null)));
        } else {
            System.err.println("Error: sidebarPane fx:id not set in FXML for gradient application.");
        }



        Color mainContentStart = Color.web("#F5F5DC");
        Color mainContentEnd = Color.web("#E0E0D4");
        LinearGradient mainContentGradient = new LinearGradient(
                0, 0, 1, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, mainContentStart),
                new Stop(1, mainContentEnd)
        );
        if (rightpain != null) {
            rightpain.setBackground(new Background(new BackgroundFill(mainContentGradient, null, null)));
        } else {
            System.err.println("Error: mainContentPane fx:id not set in FXML for gradient application.");
        }
    }


    public void loadToBarChart(){
        try {

            List<IngredientDto> ingredients = ingredientBO.getAllIngredients();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Ingredient Stocks");

            for (IngredientDto ingredient : ingredients) {
                series.getData().add(new XYChart.Data<>(ingredient.getName(), ingredient.getStock_amount()));
            }

            ingBarChart.getData().clear();
            ingBarChart.getData().add(series);
            ingBarChart.setTitle("Ingredient Stock Levels");

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load ingredient bar chart.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void countProducts(){
        try {
            int total=productBO.countProducts();

            if(total>0){
                totalProducts.setText(String.valueOf(total));
            }else{
                totalProducts.setText("no products");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void countINg(){
        try {
            int total= 0;
            total =ingredientBO.countIng();

            if(total>0){
                totalIng.setText(String.valueOf(total));
            }else{
                totalIng.setText("no ingredients");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSendEmailSup(ActionEvent actionEvent) {
        setPages("/View/sendEmailToSuppliers.fxml");
    }

    public void btnSI(ActionEvent actionEvent) {
        setPages("/View/SupplierIngredientPage.fxml");
    }
}
