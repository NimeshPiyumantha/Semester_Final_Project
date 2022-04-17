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
import model.Customer;
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

public class ManageCustomerFormController implements Initializable {
    public AnchorPane ManageCustomerFormContext;
    public JFXButton btnSave;
    public TextField txtCustomerID;
    public TextField txtAddress;
    public TextField txtICustomerName;
    public TextField txtTitle;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public TableView<Customer> tblCustomer;
    public TableColumn<? extends Object, ? extends Object> colCustomerID;
    public TableColumn<? extends Object, ? extends Object> colCustomerName;
    public TableColumn<? extends Object, ? extends Object> colTitle;
    public TableColumn<? extends Object, ? extends Object> colAddress;
    public TableColumn<? extends Object, ? extends Object> colCity;
    public TableColumn<? extends Object, ? extends Object> colProvince;
    public TableColumn<? extends Object, ? extends Object> colPostalCode;
    public Label lblCustomerAddTime;
    public Label lblCustomerAddDate;
    public TextField txtSearchCustomer;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;


    //--------------------------------CHECK TEXT FILED ----------------------------//
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern CustomerID = Pattern.compile("^[C](-[0-9]{3,4})$");
    Pattern Title = Pattern.compile("^[Mr|Mrs]{2,3}$");
    Pattern CustomerName = Pattern.compile("^([A-Z a-z]{5,40})$");
    Pattern Address = Pattern.compile("^[A-z0-9/, ]{6,30}$");
    Pattern City = Pattern.compile("^([A-Z  a-z]{4,20})$");
    Pattern Province = Pattern.compile("^([A-Z  a-z]{4,20})$");
    Pattern PostalCode = Pattern.compile("^([0-9]{2,10})$");

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
        loadCUDateAndTime();
        //-------------------------------Table Load---------------------------------//

        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("CustomerTitle"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("CustomerAddress"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("CustomerCity"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("CustomerProvince"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("CustomerPostCode"));

        try {
            loadAllCustomers();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer");
        ObservableList<Customer> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Customer(
                            result.getString("CustomerID"),
                            result.getString("CustomerTitle"),
                            result.getString("CustomerName"),
                            result.getString("CustomerAddress"),
                            result.getString("CustomerCity"),
                            result.getString("CustomerProvince"),
                            result.getString("CustomerPostCode"),
                            result.getString("CustomerAddDate"),
                            result.getString("CustomerAddTime")


                    )
            );
        }
        tblCustomer.setItems(obList);
    }

    //-----------------Save Customer-------------------------//
    public void SaveCustomerOnAction(ActionEvent actionEvent) {
        Customer cus = new Customer(
                txtCustomerID.getText(),
                txtTitle.getText(),
                txtICustomerName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText(),
                lblCustomerAddDate.getText(),
                lblCustomerAddTime.getText()
        );

        try {
            if (CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?,?,?,?,?)",
                    cus.getCustomerID(), cus.getCustomerTitle(), cus.getCustomerName(),
                    cus.getCustomerAddress(), cus.getCustomerCity(), cus.getCustomerProvince(), cus.getCustomerPostCode(), cus.getCustomerAddDate(), cus.getCustomerAddTime()
            )) {
                NotificationController.SuccessfulTableNotification("Save", "Customer");
                tblCustomer.refresh();
                loadAllCustomers();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            NotificationController.UnSuccessfulTableNotification("Save", "Customer");
        }
        clearAllTexts();
    }

    //-----------------Update Customer-------------------------//
    public void UpdateCustomerOnAction(ActionEvent actionEvent) {
        Customer c = new Customer(
                txtCustomerID.getText(),
                txtTitle.getText(),
                txtICustomerName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText(),
                lblCustomerAddDate.getText(),
                lblCustomerAddTime.getText()
        );
        try {
            boolean isUpdated = CrudUtil.execute(

                    "UPDATE Customer SET CustomerTitle=? ," +
                            " CustomerName=? ," +
                            " CustomerAddress=? ," +
                            " CustomerCity=? , CustomerProvince=? ," +
                            " CustomerPostCode=?,CustomerAddDate=?,CustomerAddTime=? WHERE CustomerID=?", c.getCustomerTitle(), c.getCustomerName(),
                    c.getCustomerAddress(), c.getCustomerCity(), c.getCustomerProvince(), c.getCustomerPostCode(), c.getCustomerAddDate(), c.getCustomerAddTime(), c.getCustomerID()
            );
            if (isUpdated) {
                NotificationController.SuccessfulTableNotification("Update", "Customer");
                tblCustomer.refresh();
                loadAllCustomers();
            } else {
                NotificationController.UnSuccessfulTableNotification("Update", "Customer");
            }


        } catch (SQLException | ClassNotFoundException e) {

        }
        clearAllTexts();
    }

    //-----------------Delete Customer-------------------------//
    public void DeleteCustomerOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Customer WHERE CustomerID=?", txtCustomerID.getText())) {
                tblCustomer.refresh();
                NotificationController.SuccessfulTableNotification("Delete", "Customer");
                loadAllCustomers();
            } else {
                NotificationController.UnSuccessfulTableNotification("Delete", "Customer");
            }

        } catch (SQLException | ClassNotFoundException e) {

        }
        clearAllTexts();
    }

    //-----------------Search Customer-------------------------//
    public void CustomerSearchOnAction() {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE CustomerID=?", txtSearchCustomer.getText());
            if (result.next()) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnSave.setDisable(false);
                txtCustomerID.setText(result.getString(1));
                txtTitle.setText(result.getString(2));
                txtICustomerName.setText(result.getString(3));
                txtAddress.setText(result.getString(4));
                txtCity.setText(result.getString(5));
                txtProvince.setText(result.getString(6));
                txtPostalCode.setText(result.getString(7));
                lblCustomerAddDate.setText(result.getString(8));
                lblCustomerAddTime.setText(result.getString(9));

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
        map.put(txtCustomerID, CustomerID);
        map.put(txtTitle, Title);
        map.put(txtICustomerName, CustomerName);
        map.put(txtAddress, Address);
        map.put(txtCity, City);
        map.put(txtProvince, Province);
        map.put(txtPostalCode, PostalCode);
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
        txtSearchCustomer.clear();
        txtCustomerID.clear();
        txtTitle.clear();
        txtICustomerName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        txtCustomerID.requestFocus();
        setBorders(txtCustomerID, txtTitle, txtICustomerName, txtAddress, txtCity, txtProvince, txtPostalCode);
    }

    //reset border colors to default color
    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

    //------------Time Date-------//
    private void loadCUDateAndTime() {
        //Set Date
        lblCustomerAddDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblCustomerAddTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }
}
