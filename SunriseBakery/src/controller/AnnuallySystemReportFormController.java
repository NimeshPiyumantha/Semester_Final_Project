package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.IncomeReport;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AnnuallySystemReportFormController implements Initializable {

    public TableView<IncomeReport> tblReport;
    public TableColumn<? extends Object, ? extends Object> colYear;
    public TableColumn<? extends Object, ? extends Object> colOrderCount;
    public TableColumn<? extends Object, ? extends Object> colItemSoldQty;
    public TableColumn<? extends Object, ? extends Object> colCost;
    public AnchorPane AReportTableContext;
    @FXML
    private AreaChart<?, ?> barChart;


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

        //-------------------------- Chart----------------------------------------//
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Sunrise Bakery");

        PreparedStatement stm = null;
        try {
            stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT YEAR(OrderDate),sum(`Order Details`.OrderQty) FROM `Order` INNER JOIN `Order Details` ON `Order`.OrderID = `Order Details`.OrderID GROUP BY extract(YEAR FROM (OrderDate))");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ResultSet rst = null;
        try {
            assert stm != null;
            rst = stm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                assert rst != null;
                if (!rst.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String date = null;
            try {
                date = rst.getString(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int count = 0;
            try {
                count = rst.getInt(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            series1.getData().add(new XYChart.Data<>(date, count));
        }
        barChart.getData().addAll(series1);

        //--------------------------Table Load----------------------------------------//
        colYear.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderCount.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));
        colItemSoldQty.setCellValueFactory(new PropertyValueFactory<>("numberOfSoldItem"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("finalCost"));

        try {
            tblReport.setItems(loadYearlyIncomeReport());
        } catch (
                SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------Observable list--------------------------------//
    private ObservableList<IncomeReport> loadYearlyIncomeReport() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT extract(YEAR FROM (OrderDate)) ,sum(`Order Details`.OrderQty),count(`Order`.OrderID),sum(`Order Details`.Total) FROM `Order` INNER JOIN `Order Details` ON `Order`.OrderID = `Order Details`.OrderID GROUP BY extract(YEAR FROM (OrderDate))");
        ResultSet rst = stm.executeQuery();
        ObservableList<IncomeReport> obList = FXCollections.observableArrayList();

        while (rst.next()) {
            String date = rst.getString(1);
            int countOrderId = rst.getInt(3);
            int numberOfSoldItem = rst.getInt(2);
            double sumOfTotal = rst.getDouble(4);

            obList.add(new IncomeReport(date, countOrderId, numberOfSoldItem, sumOfTotal));
        }
        return obList;
    }
}