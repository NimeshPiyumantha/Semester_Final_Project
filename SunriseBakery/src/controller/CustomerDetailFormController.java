package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerDetailFormController implements Initializable {
    public TableView<Customer> tblCustomerDetail;
    public TableColumn<? extends Object, ? extends Object> colCustomerID;
    public TableColumn<? extends Object, ? extends Object> colTitle;
    public TableColumn<? extends Object, ? extends Object> colCustomerName;
    public TableColumn<? extends Object, ? extends Object> colAddress;
    public TableColumn<? extends Object, ? extends Object> colCity;
    public TableColumn<? extends Object, ? extends Object> colProvince;
    public TableColumn<? extends Object, ? extends Object> colPostalCode;
    public TableColumn<? extends Object, ? extends Object> colDate;
    public TableColumn<? extends Object, ? extends Object> colTime;
    public TextField txtCustomerID;


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

        //-------------------------------Table Load---------------------------------//

        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("CustomerTitle"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("CustomerAddress"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("CustomerCity"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("CustomerProvince"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("CustomerPostCode"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("CustomerAddDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("CustomerAddTime"));

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
        tblCustomerDetail.setItems(obList);
    }

    //-------------------------------Customer Search--------------------------------//
    public void CustomerSearchOnAction() throws SQLException, ClassNotFoundException {
        if (txtCustomerID.getText().trim().isEmpty()) {
            loadAllCustomers();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE CustomerID=?", txtCustomerID.getText());
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
            tblCustomerDetail.setItems(obList);
        }
    }
}
