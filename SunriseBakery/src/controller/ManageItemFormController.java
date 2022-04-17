package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Item;
import util.CrudUtil;
import util.NotificationController;
import util.ValidationUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageItemFormController implements Initializable {
    public AnchorPane ManageItemFormContext;
    public JFXButton btnSave;
    public TextField txtItemCode;
    public TextField txtUnitPrice;
    public TextField txtItemName;
    public TextField txtQtyOnHand;
    public TextField txtDescription;
    public TextField txtPackWeight;
    public TableView<Item> tblItem;
    public TableColumn<? extends Object, ? extends Object> colItemCode;
    public TableColumn<? extends Object, ? extends Object> colItemName;
    public TableColumn<? extends Object, ? extends Object> colDescription;
    public TableColumn<? extends Object, ? extends Object> colPackWeight;
    public TableColumn<? extends Object, ? extends Object> colUnitPrice;
    public TableColumn<? extends Object, ? extends Object> colQtyOnHand;
    public Label lblTime;
    public Label lblDate;
    public TextField txtSearch;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;


    //--------------------------------CHECK TEXT FILED ----------------------------//
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern ItemCode = Pattern.compile("^[I](-[0-9]{3,4})$");
    Pattern ItemName = Pattern.compile("^([A-Z a-z]{5,40})$");
    Pattern Description = Pattern.compile("^([A-z /s]{3,30})$");
    Pattern PackWeight = Pattern.compile("^[1-9][0-9]*( )(|g|kg|ml|l)$");
    Pattern UnitPrice = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");
    Pattern QtyOnHand = Pattern.compile("^[1-9][0-9]*$");

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //--------------------------------CHECK TEXT FILED ----------------------------//
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        storeValidations();
        loadDateAndTime();

        //-------------------------------Table Load---------------------------------//

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("ItemDescription"));
        colPackWeight.setCellValueFactory(new PropertyValueFactory<>("ItemPackSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("ItemUnitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("ItemQtyOnHand"));


        try {
            loadAllItem();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //----------Date Time-------------//
    private void loadDateAndTime() {
        //Set Date
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void loadAllItem() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Item");
        ObservableList<Item> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Item(
                            result.getString("ItemCode"),
                            result.getString("ItemName"),
                            result.getString("ItemDescription"),
                            result.getString("ItemPackSize"),
                            result.getDouble("ItemUnitPrice"),
                            result.getInt("ItemQtyOnHand"),
                            result.getString("ItemAddDate"),
                            result.getString("ItemAddTime")

                    )
            );
        }
        tblItem.setItems(obList);
    }

    //---------------------Save Item-------------------//
    public void SaveItemOnAction(ActionEvent actionEvent) {
        Item it = new Item(
                txtItemCode.getText(),
                txtItemName.getText(),
                txtDescription.getText(),
                txtPackWeight.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText()),
                lblDate.getText(),
                lblTime.getText()
        );

        try {
            if (CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?,?,?,?,?)",
                    it.getItemCode(), it.getItemName(), it.getItemDescription(),
                    it.getItemPackSize(), it.getItemUnitPrice(), it.getItemQtyOnHand(), it.getItemAddDate(), it.getItemAddTime()
            )) {
                NotificationController.SuccessfulTableNotification("Save", "Customer");
                tblItem.refresh();
                loadAllItem();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            NotificationController.UnSuccessfulTableNotification("Save", "Customer");
        }
        clearAllTexts();
    }

    //---------------------Update Item-------------------//
    public void UpdateItemOnAction(ActionEvent actionEvent) {

        Item item = new Item(
                txtItemCode.getText(),
                txtItemName.getText(),
                txtDescription.getText(),
                txtPackWeight.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText()),
                lblDate.getText(),
                lblTime.getText()
        );
        try {
            boolean isUpdated = CrudUtil.execute(

                    "UPDATE Item SET ItemName=? ," +
                            " ItemDescription=? ," +
                            " ItemPackSize=? ," +
                            " ItemUnitPrice=? , ItemQtyOnHand=?, ItemAddDate=?, ItemAddTime=? WHERE ItemCode=?", item.getItemName(),
                    item.getItemDescription(), item.getItemPackSize(), item.getItemUnitPrice(), item.getItemQtyOnHand(), item.getItemAddDate(), item.getItemAddTime(), item.getItemCode()
            );
            if (isUpdated) {
                tblItem.refresh();
                loadAllItem();
                NotificationController.SuccessfulTableNotification("Update", "Item");
            } else {
                NotificationController.UnSuccessfulTableNotification("Update", "Item");
            }

        } catch (SQLException | ClassNotFoundException e) {

        }
        clearAllTexts();
    }

    //---------------------Delete Item-------------------//
    public void DeleteItemOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Item WHERE ItemCode=?", txtItemCode.getText())) {
                tblItem.refresh();
                loadAllItem();
                NotificationController.SuccessfulTableNotification("Delete", "Item");
            } else {
                NotificationController.UnSuccessfulTableNotification("Delete", "Item");
            }

        } catch (SQLException | ClassNotFoundException e) {
        }
        clearAllTexts();
    }

    //---------------------Search Item-------------------//
    public void Search() {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Item WHERE ItemCode=?", txtSearch.getText());
            if (result.next()) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnSave.setDisable(false);
                txtItemCode.setText(result.getString(1));
                txtItemName.setText(result.getString(2));
                txtDescription.setText(result.getString(3));
                txtPackWeight.setText(result.getString(4));
                txtUnitPrice.setText(result.getString(5));
                txtQtyOnHand.setText(result.getString(6));
                lblDate.setText(result.getString(7));
                lblTime.setText(result.getString(8));

            } else {
                NotificationController.EmptyDataNotification("Item");
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnSave.setDisable(true);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //---------------Validation---------------//
    private void storeValidations() {
        map.put(txtItemCode, ItemCode);
        map.put(txtItemName, ItemName);
        map.put(txtDescription, Description);
        map.put(txtPackWeight, PackWeight);
        map.put(txtUnitPrice, UnitPrice);
        map.put(txtQtyOnHand, QtyOnHand);

    }

    //----------------Key Resealed---------------//
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                System.out.println("Work");
            }
        }
    }

    //--------------Clear Text-------------------//
    public void clearAllTexts() {
        txtSearch.clear();
        txtItemCode.clear();
        txtItemName.clear();
        txtDescription.clear();
        txtPackWeight.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        txtItemCode.requestFocus();
        setBorders(txtItemCode, txtItemName, txtDescription, txtPackWeight, txtUnitPrice, txtQtyOnHand, txtItemCode);
    }

    //reset border colors to default color
    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }
}
