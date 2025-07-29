package edu.ijse.BakeTrack.controller;


import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.VehicleBO;
import edu.ijse.BakeTrack.dto.VehicleDto;

import edu.ijse.BakeTrack.tm.VehicleTM;

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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleCrudPageController implements Initializable {

    private ObservableList<VehicleTM>vehicleTMObservableList=FXCollections.observableArrayList();
    private VehicleBO vehicleBO=(VehicleBO) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLE);


    public VehicleCrudPageController() throws SQLException, ClassNotFoundException {

    }

    public ComboBox<String> cmbStatus;
    @FXML
    private AnchorPane apVehiclePage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<VehicleTM,String> colLicensePlate;

    @FXML
    private TableColumn<VehicleTM,String> colStatus;

    @FXML
    private TableColumn<VehicleTM,String> colType;

    @FXML
    private TableColumn<VehicleTM,Integer> colVehicleId;

    @FXML
    private TextField txtPlate;

    @FXML
    private TextField txtType;

    @FXML
    private TableView<VehicleTM> vehicleTable;

    @FXML
    void btnDeleteAction(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Deletion", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Are you sure you want to delete this vehicle?");
        confirm.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
           deleteVehicle();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION, "Deletion cancelled").showAndWait();
        }
    }

    @FXML
    void btnGoBack(ActionEvent event) {
        try {
            apVehiclePage.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/OwnerDashboard.fxml"));
            apVehiclePage.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
       saveVehicle();
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        updateVehicle();
    }

    @FXML
    void onTableClick(MouseEvent event) {
       clickOnTable();
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicle_id"));
        colLicensePlate.setCellValueFactory(new PropertyValueFactory<>("license_plate"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        cmbStatus.setItems(FXCollections.observableArrayList("available", "not available"));
        loadAllVehiclesToTable();
        vehicleTable.setItems(vehicleTMObservableList);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

    }

    public void loadAllVehiclesToTable(){

        try {
            ArrayList<VehicleDto> vehicleDtoArrayList = vehicleBO.getAllVehicles();
            for (VehicleDto vehicleDto : vehicleDtoArrayList) {
                vehicleTMObservableList.add(new VehicleTM(vehicleDto.getVehicle_id(),vehicleDto.getType(),vehicleDto.getLicensePlate(),vehicleDto.getStatus()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void clickOnTable(){
        VehicleTM selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtType.setText(selected.getType());
            txtPlate.setText(selected.getLicense_plate());
            cmbStatus.setValue(selected.getStatus());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        }
    }

    public void saveVehicle(){
        if (txtType.getText().isBlank() ||
                txtPlate.getText().isBlank() ||
                cmbStatus.getValue() == null) {

            new Alert(Alert.AlertType.ERROR, "Please fill in all fields.").showAndWait();
            return;
        }

        String type = txtType.getText().trim();
        String licensePlate = txtPlate.getText().trim();
        String status = cmbStatus.getValue();

        VehicleDto vehicleDto = new VehicleDto(type, licensePlate, status);

        try {
            String response = vehicleBO.addVehicle(vehicleDto);
            new Alert(Alert.AlertType.INFORMATION, response).showAndWait();

            vehicleTMObservableList.clear();
            loadAllVehiclesToTable();
            clearVehicleForm();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearVehicleForm(){
        txtType.setText("");
        txtPlate.setText("");
        cmbStatus.setValue(null);
    }

    public void deleteVehicle(){
        VehicleTM selected = vehicleTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle to delete.").showAndWait();
            return;
        }

        int vehicleId = selected.getVehicle_id();

        try {
            String response = vehicleBO.deleteVehicle(vehicleId);
            new Alert(Alert.AlertType.INFORMATION, response).showAndWait();

            vehicleTMObservableList.clear();
            loadAllVehiclesToTable();
            clearVehicleForm();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateVehicle(){
        VehicleTM selected = vehicleTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle to update.").showAndWait();
            return;
        }

        int vehicleId = selected.getVehicle_id();

        if (txtType.getText().isBlank() ||
                txtPlate.getText().isBlank() ||
                cmbStatus.getValue() == null) {

            new Alert(Alert.AlertType.ERROR, "Please fill in all fields.").showAndWait();
            return;
        }

        String type = txtType.getText().trim();
        String licensePlate = txtPlate.getText().trim();
        String status = cmbStatus.getValue();

        VehicleDto vehicleDto = new VehicleDto(vehicleId, type, licensePlate, status);

        try {
            String response = vehicleBO.updateVehicle(vehicleDto);
            new Alert(Alert.AlertType.INFORMATION, response).showAndWait();

            vehicleTMObservableList.clear();
            loadAllVehiclesToTable();
            clearVehicleForm();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clickText(MouseEvent mouseEvent) {
        btnSave.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);
    }
}
