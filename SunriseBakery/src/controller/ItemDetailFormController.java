package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemDetailFormController implements Initializable {
    public TableView<Item> tblItemView;
    public TableColumn colItemCode;
    public TableColumn colItemName;
    public TableColumn colDescription;
    public TableColumn colPackWeight;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colDate;
    public TableColumn collTime;
    public TextField txtItemCode;


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

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("ItemDescription"));
        colPackWeight.setCellValueFactory(new PropertyValueFactory<>("ItemPackSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("ItemUnitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("ItemQtyOnHand"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("ItemAddDate"));
        collTime.setCellValueFactory(new PropertyValueFactory<>("ItemAddTime"));

        try {
            loadAllItem();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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
        tblItemView.setItems(obList);
    }

    //-----------------------------Item Search---------------------------------------//
    public void ItemSearchOnAction() throws SQLException, ClassNotFoundException {
        if (txtItemCode.getText().trim().isEmpty()) {
            loadAllItem();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM Item WHERE ItemCode=?", txtItemCode.getText());
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
            tblItemView.setItems(obList);
        }
    }

}
