package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.DeliveryBO;
import edu.ijse.BakeTrack.dto.DeliveryDto;
import edu.ijse.BakeTrack.tm.DeliveryTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DeliveryCrudController implements Initializable {

    private ObservableList<DeliveryTM> observableList= FXCollections.observableArrayList();
    private ArrayList<DeliveryDto> arrayList;
    private DeliveryBO deliveryBO=(DeliveryBO) BOFactory.getInstance().getBO(BOFactory.BOType.DELIVERY);
    private DeliveryTM deliveryTM;

    public DeliveryCrudController() throws SQLException, ClassNotFoundException {

    }

    @FXML
    private TableView<DeliveryTM> DeliveryTable;

    @FXML
    private AnchorPane apOwnerDB;

    @FXML
    private TableColumn<DeliveryTM, String> clmnDelArea;

    @FXML
    private TableColumn<DeliveryTM, LocalDate> clmnDelDate;

    @FXML
    private TableColumn<DeliveryTM, Integer> clmnDelID;

    @FXML
    private TableColumn<DeliveryTM, Integer> clmnEmpID;

    @FXML
    private TableColumn<DeliveryTM, Integer> clmnVehID;

    @FXML
    private TextField txtArea;

    @FXML
    private TextField txtDelDate;

    @FXML
    private TextField txtDelID;

    @FXML
    private TextField txtEmpID;

    @FXML
    private TextField txtVehID;

    @FXML
    void btnDelete(ActionEvent event) {
          deleteDelivery();
    }

    @FXML
    void btnGOback(ActionEvent event) {

        try {
            apOwnerDB.getChildren().clear();
            AnchorPane ap = FXMLLoader.load(getClass().getResource("/View/setDeliveryPage.fxml"));
            apOwnerDB.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdate(ActionEvent event) {
        updateDelivery();

    }

    public void tableMouseClick(MouseEvent mouseEvent) {
        getSelectedItemToText();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       clmnDelID.setCellValueFactory(new PropertyValueFactory<>("delivery_id"));
       clmnVehID.setCellValueFactory(new PropertyValueFactory<>("vehicle_id"));
       clmnEmpID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
       clmnDelDate.setCellValueFactory(new PropertyValueFactory<>("delivery_date"));
       clmnDelArea.setCellValueFactory(new PropertyValueFactory<>("area"));
       loadTable();
       DeliveryTable.setItems(observableList);
    }

    public  void loadTable(){
        try {
            arrayList=deliveryBO.getAllDelivery();
            if(arrayList !=null){
                for (DeliveryDto deliveryDto : arrayList){
                    deliveryTM=new DeliveryTM(deliveryDto.getDelivery_id(),deliveryDto.getVehicleID(),
                            deliveryDto.getEmployee_id(),deliveryDto.getDeliveryDate(),deliveryDto.getDeliveryArea());
                    observableList.add(deliveryTM);
                }

            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("not found");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void getSelectedItemToText(){
        DeliveryTM seletedOne=DeliveryTable.getSelectionModel().getSelectedItem();

        if(seletedOne!=null){
            txtDelID.setText(String.valueOf(seletedOne.getDelivery_id()));
            txtVehID.setText(String.valueOf(seletedOne.getVehicle_id()));
            txtEmpID.setText(String.valueOf(seletedOne.getEmployee_id()));
            txtDelDate.setText(seletedOne.getDelivery_date().toString());
            txtArea.setText(seletedOne.getArea());
        }
    }

    public void deleteDelivery(){
        Alert alert=new Alert(Alert.AlertType.WARNING);
       DeliveryTM selectedOne=DeliveryTable.getSelectionModel().getSelectedItem();
       if(selectedOne!=null){
           String resp= null;
           try {
               resp = deliveryBO.deleteDelivery(selectedOne.getDelivery_id());
               DeliveryTable.getItems().clear();
               loadTable();
               resetTextFields();
               alert.setTitle(resp);
               alert.showAndWait();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           } catch (Exception e) {
               throw new RuntimeException(e);
           }

           return;
       }
       alert.setTitle("select a Delivery First");
       alert.showAndWait();
    }

    public void updateDelivery(){
        Alert alert=new Alert(Alert.AlertType.WARNING);
        DeliveryTM selectedOne=DeliveryTable.getSelectionModel().getSelectedItem();
        if(selectedOne!=null){
            String resp= null;
            try {
                DeliveryDto deliveryDto=new DeliveryDto(Integer.parseInt(txtDelID.getText()),Integer.parseInt(txtVehID.getText()),Integer.parseInt(txtEmpID.getText()),
                        LocalDate.parse(txtDelDate.getText()),txtArea.getText());
                resp = deliveryBO.updateDelivery(deliveryDto);
                DeliveryTable.getItems().clear();
                loadTable();
                resetTextFields();
                alert.setTitle(resp);
                alert.showAndWait();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return;
        }
        alert.setTitle("select a Delivery First");
        alert.showAndWait();
    }

    public void resetTextFields(){
        txtDelID.setText("");
        txtVehID.setText("");
        txtEmpID.setText("");
        txtDelDate.setText("");
        txtArea.setText("");
    }

}
