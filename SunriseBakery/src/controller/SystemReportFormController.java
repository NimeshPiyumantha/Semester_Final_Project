package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.OrderDetails;
import util.CrudUtil;
import util.UILoader;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SystemReportFormController implements Initializable {
    public AnchorPane SystemReportFormContext;
    public AnchorPane SystemReportButtonContext;
    public AnchorPane ReportTableContext;
    public Label lblItem;
    public Label lblCustomer;
    public Label lblOrders;
    public Label lblSoldItems;
    public TableView<OrderDetails> tblMostSoldItem;
    public TableColumn<? extends Object, ? extends Object> colMCode;
    public TableColumn<? extends Object, ? extends Object> colMName;
    public TableColumn<? extends Object, ? extends Object> colMOrderQty;
    public TableColumn<? extends Object, ? extends Object> colMUnitPrice;
    public TableView<OrderDetails> tblLeastSoldItem;
    public TableColumn<? extends Object, ? extends Object> colICode;
    public TableColumn<? extends Object, ? extends Object> colIName;
    public TableColumn<? extends Object, ? extends Object> colIOrderQty;
    public TableColumn<? extends Object, ? extends Object> colIUnitPrice;
    public TableView <OrderDetails> tblAllItem;
    public TableColumn<? extends Object, ? extends Object> colCode;
    public TableColumn<? extends Object, ? extends Object> colName;
    public TableColumn<? extends Object, ? extends Object> colSoldQty;
    public TableColumn<? extends Object, ? extends Object> colUnitPrice;

    //--------------------UI Loader------------------------//
    public void DailyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ReportTableContext, "DailySystemReportForm");
    }

    public void MonthlyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ReportTableContext, "MonthlySystemReportForm");
    }

    public void AnnuallyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ReportTableContext, "AnnuallySystemReportForm");
    }

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

        //--------------------------------Labale----------------------//
        try {
            lblItem.setText(String.valueOf(new OrderCrudController().getItem()));
            lblCustomer.setText(String.valueOf(new OrderCrudController().getCustomer()));
            lblOrders.setText(String.valueOf(new OrderCrudController().getSumSales()));
            lblSoldItems.setText(String.valueOf(new OrderCrudController().getSumSoldItems()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //-------------------------------Most Sold Item----------------------//
        colMCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colMName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colMOrderQty.setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        colMUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try {
            MostSoldItem();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //-----------------------------Least Sold Item-----------------------//
        colICode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colIName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colIOrderQty.setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        colIUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try {
            LeastSoldItem();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //----------------------------- Sold Item-----------------------//
        colCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colSoldQty.setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try {
           SoldItem();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //---------------------Sold Item Sum------------------------//
    private void SoldItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT sum(OrderQty),ItemCode,ItemName,unitPrice FROM `Order Details` GROUP BY  ItemCode ORDER BY sum(OrderQty) DESC;");
        ObservableList<OrderDetails> orderDetailsObservableList = FXCollections.observableArrayList();


        while (rst.next()) {
            orderDetailsObservableList.add(new OrderDetails(
                            rst.getString("ItemCode"),
                            rst.getString("ItemName"),
                            rst.getInt("sum(OrderQty)"),
                            rst.getDouble("unitPrice")
                    )
            );
        }
        tblAllItem.setItems(orderDetailsObservableList);
    }

    //---------------------Least Sold Item Sum------------------------//
    private void LeastSoldItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT sum(OrderQty),ItemCode,ItemName,unitPrice FROM `Order Details` GROUP BY  ItemCode ORDER BY sum(OrderQty) DESC limit 1 ;");
        ObservableList<OrderDetails> orderDetailsObservableList = FXCollections.observableArrayList();


        while (rst.next()) {
            orderDetailsObservableList.add(new OrderDetails(
                            rst.getString("ItemCode"),
                            rst.getString("ItemName"),
                            rst.getInt("sum(OrderQty)"),
                            rst.getDouble("unitPrice")
                    )
            );
        }
        tblMostSoldItem.setItems(orderDetailsObservableList);
    }

    //---------------------Most Sold Item Sum------------------------//
    private void MostSoldItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT sum(OrderQty),ItemCode,ItemName,unitPrice FROM `Order Details` GROUP BY  ItemCode ORDER BY sum(OrderQty) ASC limit 1 ;");
        ObservableList<OrderDetails> orderDetailsObservableList = FXCollections.observableArrayList();


        while (rst.next()) {
            orderDetailsObservableList.add(new OrderDetails(
                            rst.getString("ItemCode"),
                            rst.getString("ItemName"),
                            rst.getInt("sum(OrderQty)"),
                            rst.getDouble("unitPrice")
                    )
            );
        }
        tblLeastSoldItem.setItems(orderDetailsObservableList);
    }
}
