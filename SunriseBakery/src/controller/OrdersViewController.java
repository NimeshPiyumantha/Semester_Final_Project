package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Order;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrdersViewController implements Initializable {

    public AnchorPane OrdersViewContext;
    public TableView<Order> tblOrder;
    public TableColumn<? extends Object, ? extends Object> colOrderId;
    public TableColumn<? extends Object, ? extends Object> colCusId;
    public TableColumn<? extends Object, ? extends Object> colTime;
    public TableColumn<? extends Object, ? extends Object> colDate;
    public TextField txtOrderId;
    public TextField txtCustomer;
    public TextField txtDate;


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
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OId"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("ODate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("OTime"));

        try {
            loadAllOrder();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllOrder() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM `Order`");
        ObservableList<Order> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Order(
                            result.getString("OrderID"),
                            result.getString("CustomerID"),
                            result.getString("OrderDate"),
                            result.getString("OrderTime")
                    )
            );
        }
        tblOrder.setItems(obList);
    }

    //---------------------Order Id Search------------------------//
    public void OrderIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtOrderId.getText().trim().isEmpty()) {
            loadAllOrder();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `order` WHERE OrderID=? " , txtOrderId.getText());
            ObservableList<Order> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new Order(
                                result.getString("OrderID"),
                                result.getString("CustomerID"),
                                result.getString("OrderDate"),
                                result.getString("OrderTime")

                        )
                );
            }
            tblOrder.setItems(obList);
        }
    }

    //---------------------Customer Id Search------------------------//
    public void CustomerIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtCustomer.getText().trim().isEmpty()) {
            loadAllOrder();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `order` WHERE CustomerID=? " , txtCustomer.getText());
            ObservableList<Order> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new Order(
                                result.getString("OrderID"),
                                result.getString("CustomerID"),
                                result.getString("OrderDate"),
                                result.getString("OrderTime")

                        )
                );
            }
            tblOrder.setItems(obList);
        }
    }

    //---------------------Date Id Search------------------------//
    public void DateSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtDate.getText().trim().isEmpty()) {
            loadAllOrder();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `order` WHERE OrderDate=? " , txtDate.getText());
            ObservableList<Order> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new Order(
                                result.getString("OrderID"),
                                result.getString("CustomerID"),
                                result.getString("OrderDate"),
                                result.getString("OrderTime")

                        )
                );
            }
            tblOrder.setItems(obList);
        }
    }
}

