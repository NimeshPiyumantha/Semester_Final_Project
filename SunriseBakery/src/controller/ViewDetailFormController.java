package controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import util.UILoader;

import java.io.IOException;

public class ViewDetailFormController {
    public AnchorPane ViewDetailFormContext;
    public AnchorPane ViewDetailButtonContext;
    public AnchorPane ViewDetailTableContext;
    public ImageView imgCashier;
    public ImageView imgCustomer;
    public ImageView imgItem;
    public Label lblMenu;
    public Label lblDescription;
    public ImageView imgOrders;
    public ImageView imgIOrderDetails;

    //--------------------UI Loader------------------------//
    public void CashierDetailOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ViewDetailTableContext, "CashierDetailForm");
    }

    public void CustomerDetailOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ViewDetailTableContext, "CustomerDetailForm");
    }

    public void ItemDetailOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ViewDetailTableContext, "ItemDetailForm");
    }

    public void OrdersOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ViewDetailTableContext, "OrdersView");
    }

    public void OrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ViewDetailTableContext, "OrderDetailForm");
    }

    //---------------------UI Animation------------------------//
    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgCashier":
                    lblMenu.setText("Cashier Detail ");
                    lblDescription.setText("You can View Cashier ID , Cashier Name , User Name , Address , Contact Number , NIC Number , Salary and Description.");
                    break;
                case "imgCustomer":
                    lblMenu.setText("Customer Detail");
                    lblDescription.setText("You can View Customer ID , Customer Name , NIC , Contact Number , Address , City , Province ,  Postal Code and Date & Time.");
                    break;
                case "imgIOrderDetails":
                    lblMenu.setText("Order Details");
                    lblDescription.setText("You can View Order ID , Item Code , Item Price , Order Qty , Discount and Total Price");
                    break;
                case "imgOrders":
                    lblMenu.setText("Orders");
                    lblDescription.setText("You can View Order ID , Customer ID , Order Date and Order Time.");
                    break;
                case "imgItem":
                    lblMenu.setText("Item Detail");
                    lblDescription.setText("You can View  Item code , Item Name , Description , Pack Weight , Qty on Hand and Unit Price.");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome To View Details Form Dashboard");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }
}
