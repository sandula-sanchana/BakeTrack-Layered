package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.OrderBO;
import edu.ijse.BakeTrack.dto.OrderDto;
import edu.ijse.BakeTrack.tm.OrderTM;
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

public class OrderCrudPageController implements Initializable {

    private ObservableList<OrderTM> observableList = FXCollections.observableArrayList();
    private ArrayList<OrderDto> arrayList;
    private OrderBO orderBO=(OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);
    private OrderTM orderTM;

    public OrderCrudPageController() throws SQLException, ClassNotFoundException {

    }

    @FXML
    private TableView<OrderTM> orderTable;

    @FXML
    private TableColumn<OrderTM, Integer> colOrderId;

    @FXML
    private TableColumn<OrderTM, Integer> colCustomerId;

    @FXML
    private TableColumn<OrderTM, Integer> colDeliveryId;

    @FXML
    private TableColumn<OrderTM, LocalDate> colOrderDate;

    @FXML
    private TableColumn<OrderTM, Double> colTotalPrice;

    @FXML
    private TableColumn<OrderTM, String> colStatus;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtDeliveryId;

    @FXML
    private TextField txtOrderDate;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    private TextField txtStatus;

    @FXML
    private AnchorPane apOrderPage;

    @FXML
    void btnDelete(ActionEvent event) {
        deleteOrder();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        updateOrder();
    }

    @FXML
    void tableMouseClick(MouseEvent event) {
        getSelectedItemToText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colDeliveryId.setCellValueFactory(new PropertyValueFactory<>("delivery_id"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadTable();
        orderTable.setItems(observableList);
    }

    public void loadTable() {
        try {
            arrayList = orderBO.getAllOrders();
            if (arrayList != null) {
                for (OrderDto dto : arrayList) {
                    orderTM = new OrderTM(dto.getOrder_id(), dto.getCustomerID(), dto.getDeliveryID(),
                            dto.getOrderDate(), dto.getTotalPrice(), dto.getStatus());
                    observableList.add(orderTM);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Not Found", "No orders found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getSelectedItemToText() {
        OrderTM selected = orderTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtOrderId.setText(String.valueOf(selected.getOrder_id()));
            txtCustomerId.setText(String.valueOf(selected.getCustomer_id()));
            txtDeliveryId.setText(String.valueOf(selected.getDelivery_id()));
            txtOrderDate.setText(selected.getOrder_date().toString());
            txtTotalPrice.setText(String.valueOf(selected.getTotal_price()));
            txtStatus.setText(selected.getStatus());
        }
    }

    public void deleteOrder() {
        OrderTM selected = orderTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                String resp = orderBO.deleteOrder(selected.getOrder_id());
                orderTable.getItems().clear();
                loadTable();
                resetTextFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select an order first.");
        }
    }

    public void updateOrder() {
        OrderTM selected = orderTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                OrderDto dto = new OrderDto(
                        Integer.parseInt(txtOrderId.getText()),
                        Integer.parseInt(txtCustomerId.getText()),
                        Integer.parseInt(txtDeliveryId.getText()),
                        LocalDate.parse(txtOrderDate.getText()),
                        Double.parseDouble(txtTotalPrice.getText()),
                        txtStatus.getText()
                );
                String resp = orderBO.updateOrder(dto);
                orderTable.getItems().clear();
                loadTable();
                resetTextFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select an order first.");
        }
    }

    public void resetTextFields() {
        txtOrderId.clear();
        txtCustomerId.clear();
        txtDeliveryId.clear();
        txtOrderDate.clear();
        txtTotalPrice.clear();
        txtStatus.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void btnGOback(ActionEvent event) {

        try {
            apOrderPage.getChildren().clear();
            AnchorPane ap = FXMLLoader.load(getClass().getResource("/View/AddOrderPage.fxml"));
            apOrderPage.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }
}
