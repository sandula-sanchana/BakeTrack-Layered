package edu.ijse.BakeTrack.controller;

import edu.ijse.BakeTrack.bo.BOFactory;
import edu.ijse.BakeTrack.bo.custom.IngredientBO;
import edu.ijse.BakeTrack.dto.IngredientDto;
import edu.ijse.BakeTrack.tm.IngredientTM;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class IngredientCrudPageController implements Initializable {
    private IngredientBO ingredientBO=(IngredientBO) BOFactory.getInstance().getBO(BOFactory.BOType.INGREDIENT);
    private ObservableList<IngredientTM> ingredientTMObservableList = FXCollections.observableArrayList();
    private IngredientTM ingredientTM;

    public IngredientCrudPageController() throws SQLException, ClassNotFoundException {


    }

    @FXML
    private AnchorPane apIngredientPage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<IngredientTM, Double> colBuyingPrice;

    @FXML
    private TableColumn<IngredientTM, LocalDate> colExpireDate;

    @FXML
    private TableColumn<IngredientTM, Integer> colIngredientId;

    @FXML
    private TableColumn<IngredientTM, String> colIngredientName;

    @FXML
    private TableColumn<IngredientTM, Integer> colStockAmount;

    @FXML
    private TableColumn<IngredientTM, String> colUnit;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<IngredientTM> ingredientTable;

    @FXML
    private TextField txtBuyingPrice;

    @FXML
    private TextField txtIngredientName;

    @FXML
    private TextField txtStockAmount;

    @FXML
    private TextField txtUnit;

    @FXML
    void btnDeleteAction(ActionEvent event) {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Deletion", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Are you sure you want to delete this customer?");
        confirm.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            deleteIngredient();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION, "Deletion cancelled").showAndWait();
        }
    }




    @FXML
    void btnGoBack(ActionEvent event) {
        apIngredientPage.getChildren().clear();
        try {
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/StorekeeperDashboard.fxml"));
            apIngredientPage.getChildren().add(ap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
          saveIngredients();
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {

       updateIngredients();
    }

    @FXML
    void onTableClick(MouseEvent event) {
          loadSelectedToTable();
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnSave.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colIngredientId.setCellValueFactory(new PropertyValueFactory<>("ingredient_id"));
        colIngredientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStockAmount.setCellValueFactory(new PropertyValueFactory<>("stock_amount"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("buying_price"));
        colExpireDate.setCellValueFactory(new PropertyValueFactory<>("expire_date"));

        loadIngredientToTable();
        ingredientTable.setItems(ingredientTMObservableList);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);


    }


    public void loadIngredientToTable(){
        try {
            ArrayList<IngredientDto> ingredientDtoArrayList=ingredientBO.getAllIngredients();
            if(ingredientDtoArrayList!=null){
                 for(IngredientDto ingredientDto :ingredientDtoArrayList){
                     ingredientTM=new IngredientTM(ingredientDto.getIngredient_id(),ingredientDto.getName(),ingredientDto.getStock_amount(),
                             ingredientDto.getUnit(),ingredientDto.getBuying_price(),ingredientDto.getExpire_date());

                     ingredientTMObservableList.add(ingredientTM);
                 }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveIngredients(){
        if(txtIngredientName.getText()==null || txtUnit.getText()==null || txtBuyingPrice.getText()==null || txtStockAmount.getText()==null || datePicker.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"fill fields first").showAndWait();
            return;
        }

        String name=txtIngredientName.getText();
        String unit=txtUnit.getText();
        Double buYPrice=Double.parseDouble(txtBuyingPrice.getText());
        int amount=Integer.parseInt(txtStockAmount.getText());
        LocalDate date=datePicker.getValue();

        IngredientDto ingredientDto=new IngredientDto(name,amount,unit,buYPrice,date);

        try {
            String resp=ingredientBO.addIngredient(ingredientDto);
            new Alert(Alert.AlertType.INFORMATION,resp).showAndWait();
            ingredientTable.getItems().clear();
            loadIngredientToTable();
            clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFields(){
        txtIngredientName.setText("");
        txtStockAmount.setText("");
        txtBuyingPrice.setText("");
        txtUnit.setText("");
        datePicker.setValue(null);

    }

    public void updateIngredients(){

        ingredientTM=ingredientTable.getSelectionModel().getSelectedItem();
        if(ingredientTM==null){
            new Alert(Alert.AlertType.ERROR,"select a row  first").showAndWait();
            return;
        }
        int ingredint_id=ingredientTM.getIngredient_id();

        if(txtIngredientName.getText()==null || txtUnit.getText()==null || txtBuyingPrice.getText()==null || txtStockAmount.getText()==null || datePicker.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"fill fields first").showAndWait();
            return;
        }

        String name=txtIngredientName.getText();
        String unit=txtUnit.getText();
        Double buYPrice=Double.parseDouble(txtBuyingPrice.getText());
        int amount=Integer.parseInt(txtStockAmount.getText());
        LocalDate date=datePicker.getValue();

        IngredientDto ingredientDto=new IngredientDto(ingredint_id,name,amount,unit,buYPrice,date);

        try {
            String resp=ingredientBO.updateIngredient(ingredientDto);
            new Alert(Alert.AlertType.INFORMATION,resp).showAndWait();
            ingredientTable.getItems().clear();
            loadIngredientToTable();
            clearFields();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void loadSelectedToTable(){
        IngredientTM selected = ingredientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtIngredientName.setText(selected.getName());
            txtStockAmount.setText(String.valueOf(selected.getStock_amount()));
            txtUnit.setText(selected.getUnit());
            txtBuyingPrice.setText(String.valueOf(selected.getBuying_price()));
            datePicker.setValue(selected.getExpire_date());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        }
    }

    public void deleteIngredient(){
        ingredientTM=ingredientTable.getSelectionModel().getSelectedItem();
        if(ingredientTM==null){
            new Alert(Alert.AlertType.ERROR,"select a row  first").showAndWait();
            return;
        }
        int ingredint_id=ingredientTM.getIngredient_id();
        try {
            String resp=ingredientBO.deleteIngredient(ingredint_id);
            new Alert(Alert.AlertType.INFORMATION,resp).showAndWait();
            ingredientTable.getItems().clear();
            loadIngredientToTable();
            clearFields();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public void mouseClick(MouseEvent mouseEvent) {
        btnSave.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);
    }

}
