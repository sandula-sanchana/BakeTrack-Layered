package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.DeliveryBO;
import edu.ijse.BakeTrack.bo.custom.OrderBO;
import edu.ijse.BakeTrack.bo.custom.VehicleBO;
import edu.ijse.BakeTrack.dto.DeliveryDto;
import edu.ijse.BakeTrack.dto.OrderDto;
import edu.ijse.BakeTrack.dto.VehicleDto;
import edu.ijse.BakeTrack.tm.OrderTM;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class setDeliveryPageController implements Initializable {

    public TableView<OrderTM> tableOrderDetail;
    public TableColumn <OrderTM,Integer> clmnCusID;
    public TableColumn  <OrderTM,Integer>clmnDelID;
    public TableColumn  <OrderTM, LocalDate>clmnOrderDate;
    public TableColumn  <OrderTM,Double>clmnTotalPrice;
    public TableColumn  <OrderTM,String>clmnStatus;
    public TableView<VehicleTM> VehicleDetailTable;
    //public TextField txtDelPageCusID;
    public TableColumn<VehicleTM,Integer> clmnVheicleID;
    public TableColumn<VehicleTM,String>  clmnTYpe;
    public TableColumn<VehicleTM,String>  clmnLplate;
    public TextField txtAreaInput;
    public DatePicker datePicker;
    public TextField txtInputVid;
    public AnchorPane setDelPageAP;
    public ComboBox <OrderTM>cmbOrderID;


    private OrderDto orderDto=new OrderDto();
    private OrderBO orderBO=(OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);
    private  ArrayList<OrderDto> orderDtos;
    private ArrayList<VehicleDto> vehicleDtos;
    private VehicleBO vehicleBO=(VehicleBO) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLE);
    private DeliveryDto deliveryDto;
    private DeliveryBO deliveryBO=(DeliveryBO)BOFactory.getInstance().getBO(BOFactory.BOType.DELIVERY);
    private ObservableList<OrderTM> OrderTmOB=FXCollections.observableArrayList();
    private ObservableList<VehicleTM> vehicleTMS=FXCollections.observableArrayList();
    private VehicleTM vehicleTM;
    private ObservableList<OrderTM> orderOBLforCMB=FXCollections.observableArrayList();
    private  OrderTM orderTM;

    @FXML
    private TableView<?> addOrderPageTable;

    @FXML
    private TableColumn<?, ?> clmnPID;

    @FXML
    private TableColumn<?, ?> clmnPatOrder;

    @FXML
    private TableColumn<?, ?> clmnQty;

    @FXML
    private TextField txtOrderPageCusID;

    public setDeliveryPageController() throws SQLException, ClassNotFoundException {
    }

    @FXML
    void OrderPageGoBackButton(ActionEvent event) {
        try {
            setDelPageAP.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/OwnerDashboard.fxml"));
            setDelPageAP.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnGetVehicleDetails(ActionEvent event) throws SQLException {
      getAvailableVehicles();
    }

    @FXML
    void btnOidSearch(ActionEvent event) throws SQLException {
       setValuesToTable();
    }

    @FXML
    void btnSetArea(ActionEvent event) {

    }

    @FXML
    void btnSetDelivery(ActionEvent event) throws SQLException {
        setDelivery();
        OrderTmOB.clear();
        setValuesToTable();
        txtInputVid.clear();
        orderOBLforCMB.clear();
        cmbOrderID.setItems(null);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clmnCusID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

        clmnDelID.setCellValueFactory(new PropertyValueFactory<>("delivery_id"));

        clmnOrderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));

        clmnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));

        clmnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableOrderDetail.setItems(OrderTmOB);


        clmnVheicleID.setCellValueFactory(new PropertyValueFactory<>("vehicle_id"));

        clmnTYpe.setCellValueFactory(new PropertyValueFactory<>("type"));

        clmnLplate.setCellValueFactory(new PropertyValueFactory<>("license_plate"));

        VehicleDetailTable.setItems(vehicleTMS);

        loadOrdersToCmb();
        cmbOrderID.setItems(orderOBLforCMB);

    }

    public void setValuesToTable() throws SQLException {
        OrderTM selected=cmbOrderID.getValue();
        if(selected!=null) {
            int oID = selected.getOrder_id();
            orderDtos = orderBO.getOrderByID(oID);

            if (orderDtos != null) {
                OrderTM orderTM = new OrderTM(orderDtos.getFirst().getCustomerID(), orderDtos.getFirst().getDeliveryID(), orderDtos.getFirst().getOrderDate(),
                        orderDtos.getFirst().getTotalPrice(), orderDtos.getFirst().getStatus());
                OrderTmOB.add(orderTM);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not found");
                alert.showAndWait();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"select a order first").showAndWait();
        }

    }

    private void getAvailableVehicles() throws SQLException {
         String status="available";
        vehicleDtos=vehicleBO.getAvailableVehicles(status);
        if(vehicleDtos!=null){
            for(VehicleDto vdto :vehicleDtos) {
                vehicleTM = new VehicleTM(vdto.getVehicle_id(), vdto.getType(), vdto.getLicensePlate());
                vehicleTMS.add(vehicleTM);
            }
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All vehicles are out for delivery");
            alert.showAndWait();
        }

    }

    public void loadOrdersToCmb(){
        try {
            ArrayList<OrderDto> orderList=orderBO.getAllOrders();
            for(OrderDto dto :orderList){
               orderOBLforCMB.add(new OrderTM(dto.getOrder_id(),dto.getCustomerID(),dto.getOrderDate(),dto.getTotalPrice()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setDelivery() throws SQLException {
        OrderTM selected=cmbOrderID.getValue();
        if(selected!=null) {
            int oID = selected.getOrder_id();
            deliveryDto = new DeliveryDto(Integer.parseInt(txtInputVid.getText()), datePicker.getValue(), txtAreaInput.getText());
            String resp = deliveryBO.setDelivery(deliveryDto, String.valueOf(oID));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resp);
            alert.showAndWait();
        }



    }

    public void btnGetAllandEdit(ActionEvent actionEvent) {
          setPages("/View/DeliveryCrudPage.fxml");
    }

    public void setPages(String pageLocation){
        try {
            setDelPageAP.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource(pageLocation));
            setDelPageAP.getChildren().add(ap);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Page Load Error");
            alert.setHeaderText("Could not load page: " + pageLocation);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void tableClick(MouseEvent mouseEvent) {
        VehicleTM selected=VehicleDetailTable.getSelectionModel().getSelectedItem();

        if(selected!=null){
            txtInputVid.setText(String.valueOf(selected.getVehicle_id()));
        }
    }
}
