package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.ProductBO;
import edu.ijse.BakeTrack.dto.ProductDto;
import edu.ijse.BakeTrack.tm.ProductTM;

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

public  class ProductCrudPageController implements Initializable {

    public TableColumn<ProductTM, Integer> colQuantity;
    public TextField txtQty;
    @FXML
    private AnchorPane apProductPage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ProductTM, String> colCategory;

    @FXML
    private TableColumn<ProductTM, String> colDescription;

    @FXML
    private TableColumn<ProductTM,Integer> colID;

    @FXML
    private TableColumn<ProductTM, String> colName;

    @FXML
    private TableColumn<ProductTM, Double> colPrice;

    @FXML
    private TableView<ProductTM> tableProduct;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    private ProductBO productBO=(ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);
    private ObservableList<ProductTM> productTMObservableList= FXCollections.observableArrayList();
    private ProductTM productTM;

    public ProductCrudPageController() throws SQLException, ClassNotFoundException {

    }

    @FXML
    void btnDelete(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Confirm Deletion", ButtonType.YES,ButtonType.NO);
        alert.setHeaderText("Are you sure you want to delete this Product?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> resp=alert.showAndWait();
        if(resp.isPresent() && resp.get()==ButtonType.YES){
            deleteProduct();
        }else{
            new Alert(Alert.AlertType.ERROR, "delete Attendance Cancelled.").show();
        }
    }

    @FXML
    void btnGoBack(ActionEvent event) {
        apProductPage.getChildren().clear();
        try {
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/StorekeeperDashboard.fxml"));
            apProductPage.getChildren().add(ap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSave(ActionEvent event) {
       saveProduct();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
       updateProduct();
    }

    @FXML
    void clikedONtext(MouseEvent event) {
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(false);
    }

    @FXML
    void tableMouseClick(MouseEvent event) {
        setSelectedOneToFields();
        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }


    private void saveProduct() {
        if (txtName.getText().isEmpty() || txtCategory.getText().isEmpty() ||
                txtPrice.getText().isEmpty() || txtDescription.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields").showAndWait();
            return;
        }

        String name = txtName.getText().trim();
        String category = txtCategory.getText().trim();
        double price = Double.parseDouble(txtPrice.getText().trim());
        int qty=Integer.parseInt(txtQty.getText());
        String description = txtDescription.getText().trim();

        ProductDto productDto = new ProductDto(name, category, price,qty,description);

        try {
            String resp = productBO.addProduct(productDto);
            new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();
            tableProduct.getItems().clear();
            loadAllProductsToTable();
            clearFields();
        } catch (SQLException e) {
            System.err.println("Product save failed: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  void clearFields(){
        txtName.setText("");
        txtCategory.setText("");
        txtPrice.setText("");
        txtQty.setText("");
        txtDescription.setText("");
    }

    public void loadAllProductsToTable(){
        try {
            ArrayList<ProductDto> productDtoArrayList=productBO.getAllProducts();
               if(productDtoArrayList!=null){
                   for(ProductDto productDto :productDtoArrayList){
                       productTM=new ProductTM(productDto.getProduct_id(),productDto.getName(),productDto.getCategory(),productDto.getPrice(),productDto.getQuantity(),productDto.getDescription());
                       productTMObservableList.add(productTM);
                   }
               }else{
                   new Alert(Alert.AlertType.ERROR,"not found").showAndWait();
               }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("Product_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        loadAllProductsToTable();
        tableProduct.setItems(productTMObservableList);
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void setSelectedOneToFields() {
        productTM = tableProduct.getSelectionModel().getSelectedItem();
        if (productTM != null) {
            txtName.setText(productTM.getName());
            txtCategory.setText(productTM.getCategory());
            txtPrice.setText(String.valueOf(productTM.getPrice()));
            txtQty.setText(String.valueOf(productTM.getQuantity()));
            txtDescription.setText(productTM.getDescription());
        }
    }

    public void deleteProduct() {
        ProductTM selected = tableProduct.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                String resp = productBO.deleteProduct(selected.getProduct_id());
                tableProduct.getItems().clear();
                loadAllProductsToTable();
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a product to delete.").showAndWait();
        }
    }

    public void updateProduct() {
        ProductTM selected = tableProduct.getSelectionModel().getSelectedItem();

        if (selected != null) {
            if (txtName.getText().isEmpty() || txtCategory.getText().isEmpty() ||
                    txtPrice.getText().isEmpty() || txtQty.getText().isEmpty() ||
                    txtDescription.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill all required fields").showAndWait();
                return;
            }

            try {
                int productId = selected.getProduct_id();
                String name = txtName.getText().trim();
                String category = txtCategory.getText().trim();
                double price = Double.parseDouble(txtPrice.getText().trim());
                int qty = Integer.parseInt(txtQty.getText().trim());
                String description = txtDescription.getText().trim();

                ProductDto dto = new ProductDto(productId, name, category, price, qty, description);

                String resp = productBO.updateProduct(dto);
                tableProduct.getItems().clear();
                loadAllProductsToTable();
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();

            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid number input. Please enter valid price and quantity.").showAndWait();
            } catch (SQLException e) {
                System.err.println("Update failed: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a product to update.").showAndWait();
        }
    }



}
