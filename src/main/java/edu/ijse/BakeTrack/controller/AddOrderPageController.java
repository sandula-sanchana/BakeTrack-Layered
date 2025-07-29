package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.CustomerBO;
import edu.ijse.BakeTrack.bo.custom.OrderBO;
import edu.ijse.BakeTrack.bo.custom.ProductBO;
import edu.ijse.BakeTrack.dto.CustomersDto;
import edu.ijse.BakeTrack.dto.OrderDetailDto;
import edu.ijse.BakeTrack.dto.OrderDto;
import edu.ijse.BakeTrack.dto.ProductDto;
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
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import edu.ijse.BakeTrack.tm.OrderDetailTM;


public class AddOrderPageController implements Initializable {

    public Label lblPriceAtOrder;
    public TableColumn<OrderDetailTM, Integer> clmnPID;
    public TableColumn<OrderDetailTM, Integer> clmnQty;
    public TableColumn<OrderDetailTM, Double> clmnPatOrder;
    public Label lblTotalPrice;
    public AnchorPane apOrderPage;
    public ComboBox<ProductDto> cmbProduct;
    private CustomerBO customerBO=(CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    private ProductBO productBO=(ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);
    private OrderBO orderBO=(OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);
    private ArrayList<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
    private Double total_price = 0.0;
    private ObservableList<OrderDetailTM> orderDetailTMs = FXCollections.observableArrayList();
    OrderDetailTM newItem;
    private ObservableList<ProductDto> productDtoObservableList = FXCollections.observableArrayList();
    private int cus_id;


    @FXML
    private TableView<OrderDetailTM> addOrderPageTable;

    @FXML
    private Label lblCusData;

    @FXML
    private HBox lblPriceAROrder;

    @FXML
    private Label lblProductData;

    @FXML
    private TextField txtOrderPageCusID;

    @FXML
    private TextField txtOrderPageQty;

    public AddOrderPageController() throws SQLException, ClassNotFoundException {
    }


    @FXML
    void OrderPageGoBackButton(ActionEvent event) {
        try {
            apOrderPage.getChildren().clear();
            AnchorPane ap = FXMLLoader.load(getClass().getResource("/View/OwnerDashboard.fxml"));
            apOrderPage.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSearchCid(ActionEvent event) throws SQLException {
        getCustomerByID();
    }


    public void btnPlaceOrder(ActionEvent actionEvent) throws SQLException {
        placeOrder();
    }

    public void getCustomerByID() throws SQLException {
        int con_input = Integer.parseInt(txtOrderPageCusID.getText());
        CustomersDto customer = customerBO.getCustomerByCOn(con_input);
        if (customer != null) {
            lblCusData.setText(customer.getName() + "-" + customer.getAddress() + "-" + customer.getContact());
            cus_id=customer.getCustomerID();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("customer not found");
            alert.showAndWait();
        }
    }

    public void getAllProducts() throws Exception {
        ArrayList<ProductDto> productDtoArrayList = productBO.getAllProducts();
        if (productDtoArrayList != null) {
            productDtoObservableList.clear();
            productDtoObservableList.addAll(productDtoArrayList);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("product not found");
            alert.showAndWait();
        }
    }

    @FXML
    void btnAddOrderDetail(ActionEvent event) {
        try {
            int pid = cmbProduct.getValue().getProduct_id();
            int qty = Integer.parseInt(txtOrderPageQty.getText());
            double price = Double.parseDouble(lblPriceAtOrder.getText());

            boolean found = false;
            for (OrderDetailTM item : orderDetailTMs) {
                if (item.getProductId() == pid) {
                    item.setQuantity(item.getQuantity() + qty);
                    found = true;
                    break;
                }
            }


            if (!found) {
                newItem = new OrderDetailTM(pid, qty, price);
                orderDetailTMs.add(newItem);
            }

            addOrderPageTable.refresh();
            updateTotalPriceLabel();


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        clmnPID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        clmnQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmnPatOrder.setCellValueFactory(new PropertyValueFactory<>("priceAtOrder"));

        orderDetailTMs = FXCollections.observableArrayList();
        addOrderPageTable.getItems().clear();
        addOrderPageTable.setItems(orderDetailTMs);
        addOrderPageTable.refresh();
        try {
            getAllProducts();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cmbProduct.getItems().addAll(productDtoObservableList);

        addOrderPageTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !addOrderPageTable.getSelectionModel().isEmpty()) {
                OrderDetailTM selectedItem = addOrderPageTable.getSelectionModel().getSelectedItem();
                orderDetailTMs.remove(selectedItem);
                addOrderPageTable.refresh();
                updateTotalPriceLabel();
            }
        });
    }

    public void placeOrder() throws SQLException {

        orderDetailDtoList.clear();
        for (OrderDetailTM tm : orderDetailTMs) {
            orderDetailDtoList.add(new OrderDetailDto(tm.getProductId(), tm.getQuantity(), tm.getPriceAtOrder()));
        }

        LocalDate today_date = LocalDate.now();

            OrderDto orderDto = new OrderDto(cus_id, today_date, total_price, "pending");
            String resp = orderBO.placeOrder(orderDto, orderDetailDtoList);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resp);
            alert.showAndWait();

            orderDetailTMs.clear();



    }

    public void updateTotalPriceLabel() {
        total_price = 0.0;
        for (OrderDetailTM item : orderDetailTMs) {
            total_price += item.getQuantity() * item.getPriceAtOrder();
        }
        lblTotalPrice.setText(String.valueOf(total_price));
    }


    public void lblPriceAtOrder(MouseEvent mouseEvent) {
    }


    public void getAllEdit(ActionEvent actionEvent) {
        setPages("/View/OrderCrudPage.fxml");
    }

    public void setPages(String pageLocation) {
        try {
            apOrderPage.getChildren().clear();
            AnchorPane ap = FXMLLoader.load(getClass().getResource(pageLocation));
            apOrderPage.getChildren().add(ap);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Page Load Error");
            alert.setHeaderText("Could not load page: " + pageLocation);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void clickONcmb(ActionEvent actionEvent) {
        ProductDto productDto=cmbProduct.getValue();
        double price_at_order = productDto.getPrice();
        lblPriceAtOrder.setText(Double.toString(price_at_order));
        lblProductData.setText(productDto.getProduct_id()+"+++"+productDto.getName()+"+++"+productDto.getPrice());
    }
}
