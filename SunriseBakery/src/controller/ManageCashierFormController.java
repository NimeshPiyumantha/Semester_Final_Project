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
import model.Cashier;
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

public class ManageCashierFormController implements Initializable {
    public AnchorPane ManageCashierFormContext;
    public JFXButton btnSave;
    public TextField txtCashierID;
    public TextField txtContactNo;
    public TextField txtCashierIName;
    public TextField txtNIC;
    public TextField txtUserPassWord;
    public TextField txtSalary;
    public TextField txtAddress;
    public TextField txtDescription;
    public TableView<Cashier> tblCashier;
    public TableColumn colCashierID;
    public TableColumn colCashierName;
    public TableColumn colUserPassWord;
    public TableColumn colAddress;
    public TableColumn colContactNumber;
    public TableColumn colNic;
    public TableColumn colSalary;
    public TableColumn colDescription;
    public Label lblCADate;
    public Label lblCATime;
    public TextField txtCashierSearch;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    //--------------------------------CHECK TEXT FILED ----------------------------//
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern CashierID = Pattern.compile("^[CH]{2}(-[0-9]{3,4})$");
    Pattern CashierIName = Pattern.compile("^([A-Z a-z]{5,40})$");
    Pattern UserPassword = Pattern.compile("^([A-Z a-z]{5,15}[0-9]{1,10})$");
    Pattern Address = Pattern.compile("^([A-Za-z]{4,10})$");
    Pattern ContactNo = Pattern.compile("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$");
    Pattern NIC = Pattern.compile("^([0-9]{12}|[0-9V]{10})$");
    Pattern Salary = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");
    Pattern Description = Pattern.compile("^([A-Z a-z]{5,15})$");

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
        loadCDateAndTime();

        //-------------------------------Table Load---------------------------------//

        colCashierID.setCellValueFactory(new PropertyValueFactory<>("CashierID"));
        colCashierName.setCellValueFactory(new PropertyValueFactory<>("CashierName"));
        colUserPassWord.setCellValueFactory(new PropertyValueFactory<>("CashierPsw"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("CashierAddress"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("CashierCNumber"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("CashierNIC"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("CashierSalary"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("CashierDescription"));


        try {
            loadAllCashier();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //-----------------Time Date -------------------//
    private void loadCDateAndTime() {
        //Set Date
        lblCADate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblCATime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    private void loadAllCashier() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Cashier");
        ObservableList<Cashier> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Cashier(
                            result.getString("CashierID"),
                            result.getString("CashierName"),
                            result.getString("CashierPsw"),
                            result.getString("CashierAddress"),
                            result.getString("CashierCNumber"),
                            result.getString("CashierNIC"),
                            result.getDouble("CashierSalary"),
                            result.getString("CashierDescription"),
                            result.getString("CashierAddDate"),
                            result.getString("CashierAddTime")

                    )
            );
        }
        tblCashier.setItems(obList);
    }

    //-----------------Save Cashier-------------------------//
    public void SaveCashierOnAction(ActionEvent actionEvent) {
        Cashier cash = new Cashier(
                txtCashierID.getText(),
                txtCashierIName.getText(),
                txtUserPassWord.getText(),
                txtAddress.getText(),
                txtContactNo.getText(),
                txtNIC.getText(),
                Double.parseDouble(txtSalary.getText()),
                txtDescription.getText(),
                lblCADate.getText(),
                lblCATime.getText()

        );

        try {
            if (CrudUtil.execute("INSERT INTO Cashier VALUES (?,?,?,?,?,?,?,?,?,?)",
                    cash.getCashierID(), cash.getCashierName(), cash.getCashierPsw(),
                    cash.getCashierAddress(), cash.getCashierCNumber(), cash.getCashierNIC(), cash.getCashierSalary(), cash.getCashierDescription(), cash.getCashierAddDate(), cash.getCashierAddTime()
            )) {
                NotificationController.SuccessfulTableNotification("Save", "Cashier");
                tblCashier.refresh();
                loadAllCashier();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            NotificationController.UnSuccessfulTableNotification("Save", "Cashier");
        }
        clearAllTexts();
    }

    //--------------Update Cashier-------------//
    public void UpdateCashierOnAction(ActionEvent actionEvent) {
        Cashier c = new Cashier(
                txtCashierID.getText(),
                txtCashierIName.getText(),
                txtUserPassWord.getText(),
                txtAddress.getText(),
                txtContactNo.getText(),
                txtNIC.getText(),
                Double.parseDouble(txtSalary.getText()),
                txtDescription.getText(),
                lblCADate.getText(),
                lblCATime.getText()
        );
        try {
            boolean isUpdated = CrudUtil.execute(

                    "UPDATE Cashier SET CashierName=? ," +
                            " CashierPsw=? ," +
                            " CashierAddress=? ," +
                            " CashierCNumber=? , CashierNIC=? ," +
                            " CashierSalary=?,CashierDescription=?, CashierAddDate=?,CashierAddTime=? WHERE CashierID=?", c.getCashierName(), c.getCashierPsw(),
                    c.getCashierAddress(), c.getCashierCNumber(), c.getCashierNIC(), c.getCashierSalary(), c.getCashierDescription(), c.getCashierAddDate(), c.getCashierAddTime(), c.getCashierID()
            );
            if (isUpdated) {
                NotificationController.SuccessfulTableNotification("Update", "Cashier");
                tblCashier.refresh();
                loadAllCashier();
            } else {
                NotificationController.UnSuccessfulTableNotification("Update", "Cashier");
            }
        } catch (SQLException | ClassNotFoundException e) {

        }
        clearAllTexts();
    }

    //-------------Delete Cashier------------------------//
    public void DeleteCashierOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Cashier WHERE CashierID=?", txtCashierID.getText())) {
                tblCashier.refresh();
                NotificationController.SuccessfulTableNotification("Delete", "Cashier");
                loadAllCashier();
            } else {
                NotificationController.UnSuccessfulTableNotification("Delete", "Cashier");
            }

        } catch (SQLException | ClassNotFoundException e) {

        }
        clearAllTexts();
    }

    //-----------------Search Cashier-------------//
    public void SearchCashierOnAction() {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Cashier WHERE CashierID=?", txtCashierSearch.getText());
            if (result.next()) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnSave.setDisable(false);
                txtCashierID.setText(result.getString(1));
                txtCashierIName.setText(result.getString(2));
                txtUserPassWord.setText(result.getString(3));
                txtAddress.setText(result.getString(4));
                txtContactNo.setText(result.getString(5));
                txtNIC.setText(result.getString(6));
                txtSalary.setText(result.getString(7));
                txtDescription.setText(result.getString(8));
                lblCADate.setText(result.getString(9));
                lblCATime.setText(result.getString(10));

            } else {
                NotificationController.EmptyDataNotification("Customer");
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnSave.setDisable(true);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //-------------Validation----------------//
    private void storeValidations() {
        map.put(txtCashierID, CashierID);
        map.put(txtCashierIName, CashierIName);
        map.put(txtUserPassWord, UserPassword);
        map.put(txtAddress, Address);
        map.put(txtContactNo, ContactNo);
        map.put(txtNIC, NIC);
        map.put(txtSalary, Salary);
        map.put(txtDescription, Description);
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
        txtCashierSearch.clear();
        txtCashierID.clear();
        txtCashierIName.clear();
        txtUserPassWord.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtNIC.clear();
        txtSalary.clear();
        txtDescription.clear();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        txtCashierID.requestFocus();
        setBorders(txtCashierID, txtCashierIName, txtUserPassWord, txtAddress, txtContactNo, txtNIC, txtSalary, txtDescription);
    }

    //reset border colors to default color
    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }
}
