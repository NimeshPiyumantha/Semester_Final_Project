package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderDetails;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderDetailFormController implements Initializable {
    public TableView<OrderDetails> tblOrderDetail;
    public TableColumn<? extends Object, ? extends Object> colOrderID;
    public TableColumn<? extends Object, ? extends Object> colCusID;
    public TableColumn<? extends Object, ? extends Object> colItemCode;
    public TableColumn<? extends Object, ? extends Object> colItemName;
    public TableColumn<? extends Object, ? extends Object> colPrice;
    public TableColumn<? extends Object, ? extends Object> colQty;
    public TableColumn<? extends Object, ? extends Object> colDiscount;
    public TableColumn<? extends Object, ? extends Object> colTotalPrice;
    public TextField txtOrderIdSearch;
    public TextField txtItemCode;
    public TextField txtCustomerId;


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
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        colCusID.setCellValueFactory(new PropertyValueFactory<>("CusId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("Total"));

        try {
            loadAllOrderDetail();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllOrderDetail() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM `Order Details`");
        ObservableList<OrderDetails> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new OrderDetails(
                            result.getString("OrderID"),
                            result.getString("CustomerID"),
                            result.getString("ItemCode"),
                            result.getString("ItemName"),
                            result.getInt("OrderQty"),
                            result.getDouble("unitPrice"),
                            result.getDouble("Discount"),
                            result.getDouble("Total")
                    )
            );
        }
        tblOrderDetail.setItems(obList);
    }

    //---------------------------Search Order Id-------------------------------//
    public void OrderIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtOrderIdSearch.getText().trim().isEmpty()) {
            loadAllOrderDetail();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `order details` WHERE OrderID=?", txtOrderIdSearch.getText());
            ObservableList<OrderDetails> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new OrderDetails(
                                result.getString("OrderID"),
                                result.getString("CustomerID"),
                                result.getString("ItemCode"),
                                result.getString("ItemName"),
                                result.getInt("OrderQty"),
                                result.getDouble("unitPrice"),
                                result.getDouble("Discount"),
                                result.getDouble("Total")
                        )
                );
            }
            tblOrderDetail.setItems(obList);
        }
    }

    //---------------------------Search Item Code-------------------------------//
    public void ItemCodeSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtItemCode.getText().trim().isEmpty()) {
            loadAllOrderDetail();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `order details` WHERE ItemCode=?", txtItemCode.getText());
            ObservableList<OrderDetails> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new OrderDetails(
                                result.getString("OrderID"),
                                result.getString("CustomerID"),
                                result.getString("ItemCode"),
                                result.getString("ItemName"),
                                result.getInt("OrderQty"),
                                result.getDouble("unitPrice"),
                                result.getDouble("Discount"),
                                result.getDouble("Total")

                        )
                );
            }
            tblOrderDetail.setItems(obList);
        }
    }

    //---------------------------Search Customer Id-------------------------------//
    public void CustomerIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtCustomerId.getText().trim().isEmpty()) {
            loadAllOrderDetail();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `order details` WHERE CustomerID=?", txtCustomerId.getText());
            ObservableList<OrderDetails> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new OrderDetails(
                                result.getString("OrderID"),
                                result.getString("CustomerID"),
                                result.getString("ItemCode"),
                                result.getString("ItemName"),
                                result.getInt("OrderQty"),
                                result.getDouble("unitPrice"),
                                result.getDouble("Discount"),
                                result.getDouble("Total")

                        )
                );
            }
            tblOrderDetail.setItems(obList);
        }
    }
}
