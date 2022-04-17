package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cashier;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CashierDetailFormController implements Initializable {
    public TableView<Cashier> tblCashierView;
    public TableColumn<? extends Object, ? extends Object> colCashierID;
    public TableColumn<? extends Object, ? extends Object> colCashierName;
    public TableColumn<? extends Object, ? extends Object> colUserPassWord;
    public TableColumn<? extends Object, ? extends Object> colAddress;
    public TableColumn<? extends Object, ? extends Object> colContactNumber;
    public TableColumn<? extends Object, ? extends Object> colNic;
    public TableColumn<? extends Object, ? extends Object> colSalary;
    public TableColumn<? extends Object, ? extends Object> colDescription;
    public TableColumn<? extends Object, ? extends Object> colDate;
    public TableColumn<? extends Object, ? extends Object> colTime;
    public TextField txtCashierID;


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

        colCashierID.setCellValueFactory(new PropertyValueFactory<>("CashierID"));
        colCashierName.setCellValueFactory(new PropertyValueFactory<>("CashierName"));
        colUserPassWord.setCellValueFactory(new PropertyValueFactory<>("CashierPsw"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("CashierAddress"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("CashierCNumber"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("CashierNIC"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("CashierSalary"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("CashierDescription"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("CashierAddDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("CashierAddTime"));

        try {
            loadAllCashier();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    //--------------------------------Load Cashier-------------------------------//
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
        tblCashierView.setItems(obList);
    }

    //--------------------------------Search Cashier-------------------------------//
    public void CashierSearchOnAction() throws SQLException, ClassNotFoundException {
        if (txtCashierID.getText().trim().isEmpty()) {
            loadAllCashier();

        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM Cashier WHERE CashierID=?", txtCashierID.getText());
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
            tblCashierView.setItems(obList);
        }
    }
}

