package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderDetails;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.NotificationController;
import util.ValidationUtil;
import view.tm.CartTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageOrderFormController implements Initializable {

    public AnchorPane ManageOrderFormContext;
    public Label lblId;
    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtName;
    public JFXTextField txtCity;
    public JFXTextField txtAddress;
    public JFXTextField txtUnitPrize;
    public JFXTextField txtQTYOnHand;
    public TextField txtQTY;
    public TextField txtDiscount;
    public TableView<CartTM> tblOrder;

    public TableColumn<? extends Object, ? extends Object> colOrderId;
    public TableColumn<? extends Object, ? extends Object> colItemCode;
    public TableColumn<? extends Object, ? extends Object> colQty;
    public TableColumn<? extends Object, ? extends Object> colUnitPrice;
    public TableColumn<? extends Object, ? extends Object> colDiscount;
    public TableColumn<? extends Object, ? extends Object> colTotal;
    public JFXTextField txtItemName;
    public JFXButton btnClose;
    public JFXButton btnPalaceOrder;
    public JFXButton btnPrintBill;
    public JFXButton btnAddToCart;
    public JFXButton btnRemove;
    public Label lblDate;
    public Label lblTime;
    public Label lblTotal;


    int cartSelectedRowForRemove = -1;
    ObservableList<CartTM> tmList = FXCollections.observableArrayList();
    //--------------------------------CHECK TEXT FILED ----------------------------//
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern QTY = Pattern.compile("^[0-9]{1,5}$");
    Pattern Discount = Pattern.compile("^[0-9]{1,2}.{1,3}$");

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
        btnAddToCart.setDisable(true);
        btnPalaceOrder.setDisable(true);
        btnPrintBill.setDisable(true);
        btnClose.setDisable(true);
        btnRemove.setDisable(true);
        storeValidations();

        //-------------------------------Table Load---------------------------------//
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));

        setCustomerIds();
        setItemCodes();
        setOrderId();
        loadDateAndTime();

        //--------------------Combo Customer set---------------//
        cmbCustomerId.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setCustomerDetails(newValue);
                });

        //--------------------Combo Item set---------------//
        cmbItemCode.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setItemDetails(newValue);
                });

        //--------------------Select Row Delete---------------//
        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
            btnRemove.setDisable(false);
        });
    }

    //-----------------Data Time--------------//
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

    //---------------Set OrderId---------------//
    private void setOrderId() {
        try {
            lblId.setText(new OrderCrudController().getOrderId());
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    //---------------Set Customer Details Combo---------------//
    private void setCustomerDetails(String selectedCustomerId) {
        try {
            Customer c = CustomerCrudController.getCustomer(selectedCustomerId);
            if (c != null) {
                txtName.setText(c.getCustomerName());
                txtAddress.setText(c.getCustomerAddress());
                txtCity.setText(c.getCustomerCity());
            } else {
//                        NotificationController.EmptyDataNotification("Empty Result");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //---------------Set Item Details Combo---------------//
    private void setItemDetails(String selectedItemCode) {
        try {
            Item i = ItemCrudController.getItem(selectedItemCode);
            if (i != null) {
                txtItemName.setText(i.getItemName());
                txtUnitPrize.setText(String.valueOf(i.getItemUnitPrice()));
                txtQTYOnHand.setText(String.valueOf(i.getItemQtyOnHand()));
            } else {
//                    NotificationController.EmptyDataNotification("Empty Result");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //---------------Set Customer Id---------------//
    private void setCustomerIds() {
        try {
            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    CustomerCrudController.getCustomerIds()
            );
            cmbCustomerId.setItems(cIdObList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //---------------Set Item Code---------------//
    private void setItemCodes() {
        try {
            cmbItemCode.setItems(FXCollections.observableArrayList(ItemCrudController.getItemCodes()));

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //---------------Remove Button---------------//
    public void removeBtnOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            NotificationController.EmptyDataNotification("Please Select a row");
        } else {
            tmList.remove(cartSelectedRowForRemove);
            if (tmList.isEmpty()) {
            }
            calculateTotal();
            tblOrder.refresh();
            btnRemove.setDisable(true);
            btnClose.setDisable(true);
            btnPrintBill.setDisable(true);
            btnPalaceOrder.setDisable(true);
        }
    }

    //---------------Add Cart Button---------------//
    public void addToCartOnAction(ActionEvent actionEvent) {
        double unitPrice = Double.parseDouble(txtUnitPrize.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        int qty = Integer.parseInt(txtQTY.getText());
        double Cost = ((unitPrice * qty) * discount / 100);
        double totalCost = (unitPrice * qty) - Cost;

        CartTM isExists = isExists(cmbItemCode.getValue());

        if (isExists != null) {
            for (CartTM temp : tmList
            ) {
                if (temp.equals(isExists)) {
                    temp.setOrderQty((temp.getOrderQty() + qty));
                    temp.setTotal(temp.getTotal() + totalCost);
                }
            }
        } else {

            CartTM tm = new CartTM(
                    lblId.getText(),
                    cmbCustomerId.getValue(),
                    cmbItemCode.getValue(),
                    txtItemName.getText(),
                    qty,
                    unitPrice,
                    discount,
                    totalCost
            );

            tmList.add(tm);
            tblOrder.setItems(tmList);

        }
        clearAllTexts1();
        tblOrder.refresh();
        calculateTotal();
        btnRemove.setDisable(false);
        btnClose.setDisable(false);
        btnPrintBill.setDisable(false);
        btnPalaceOrder.setDisable(false);
        btnAddToCart.setDisable(true);

    }

    //------Calculate Full Total-----------//
    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tmList
        ) {
            total += tm.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    //------------------Cart TM-----------------//
    private CartTM isExists(String itemCode) {
        for (CartTM tm : tmList
        ) {
            if (tm.getItemCode().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }

    //--------------------Place Order Button-----------//
    public void conformOrderBtnOnAction(ActionEvent actionEvent) throws SQLException {
        ArrayList<OrderDetails> details = new ArrayList<>();
        for (CartTM tm : tmList
        ) {
            details.add(
                    new OrderDetails(
                            tm.getOrderId(),
                            tm.getCusId(),
                            tm.getItemCode(),
                            tm.getItemName(),
                            tm.getOrderQty(),
                            tm.getUnitPrice(),
                            tm.getDiscount(),
                            tm.getTotal()
                    )
            );
            tm.getItemCode();

        }
        Order order = new Order(
                lblId.getText(),
                cmbCustomerId.getValue(),
                lblDate.getText(),
                lblTime.getText()


        );

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = new OrderCrudController().saveOrder(order);
            if (isOrderSaved) {
                boolean isDetailsSaved = new OrderCrudController().saveOrderDetails(details);
                if (isDetailsSaved) {
                    connection.commit();
                    clearAllTexts2();
                    NotificationController.SuccessfulTableNotification("Order Place", "Order");
                    btnPrintBill();
                } else {
                    connection.rollback();
                    NotificationController.UnSuccessfulTableNotification("Order Place", "Order");
                }
            } else {
                connection.rollback();
                NotificationController.UnSuccessfulTableNotification("Order Place", "Order");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            assert connection != null;
            connection.setAutoCommit(true);
        }
        setOrderId();
        ClearOnAction();
    }

    //-----------------Print Bill Button------------------------------//
    public void btnPrintBillOnAction(ActionEvent actionEvent) {
        btnPrintBill();
    }

    //-----------------Print Bill Method------------------------------//
    public void btnPrintBill() {
        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/Report.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, new JRBeanCollectionDataSource(tmList));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    //-----------------Validation--------------------------//
    private void storeValidations() {
        map.put(txtQTY, QTY);
        map.put(txtDiscount, Discount);

    }

    //--------------------Text Key Released---------------------------//
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddToCart);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                System.out.println("Work");
            }
        }
    }

    //--------------------Clear Text Add Cart-------------//
    public void clearAllTexts1() {
        cmbItemCode.getSelectionModel().clearSelection();
        txtItemName.clear();
        txtQTYOnHand.clear();
        txtUnitPrize.clear();
        txtQTY.clear();
        txtDiscount.clear();
        cmbItemCode.requestFocus();
        setBorders(txtQTY, txtDiscount);
    }

    //--------------------Clear Text Place Order-------------//
    public void clearAllTexts2() {
        cmbCustomerId.getSelectionModel().clearSelection();
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        cmbItemCode.getSelectionModel().clearSelection();
        txtItemName.clear();
        txtQTYOnHand.clear();
        txtUnitPrize.clear();
        txtQTY.clear();
        txtDiscount.clear();
        cmbCustomerId.requestFocus();
        setBorders(txtQTY, txtDiscount);
        btnRemove.setDisable(true);
        btnClose.setDisable(true);
        btnAddToCart.setDisable(true);
        btnPalaceOrder.setDisable(true);
        btnPrintBill.setDisable(true);
    }

    //reset border colors to default color
    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

    //-----------------------------Clear Table Button--------------------------//
    public void btnClearOnAction(ActionEvent actionEvent) {
        ClearOnAction();
    }

    //--------------Clear Method---------------------------//
    public void ClearOnAction() {
        clearAllTexts2();
        tmList.clear();
        lblTotal.setText("0.00/=");
        tblOrder.refresh();
        btnPalaceOrder.setDisable(true);
    }
}
