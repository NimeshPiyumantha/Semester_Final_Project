package controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import util.UILoader;

import java.io.IOException;

public class OrderViewFormController {
    public AnchorPane OrderViewFormContext;
    public AnchorPane ViewDetailButtonContext;
    public AnchorPane ViewDetailTableContext;
    public ImageView imgOrders;
    public ImageView imgIOrderDetails;
    public Label lblMenu;
    public Label lblDescription;

    public void OrdersOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ViewDetailTableContext,"OrdersView");
    }

    public void OrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ViewDetailTableContext,"OrderDetailForm");
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgIOrderDetails":
                    lblMenu.setText("Order Details");
                    lblDescription.setText("You can View Order ID , Item Code , Item Price , Order Qty , Discount and Total Price");
                    break;
                case "imgOrders":
                    lblMenu.setText("Orders");
                    lblDescription.setText("You can View Order ID , Customer ID , Order Date and Order Time.");
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
            lblMenu.setText("Welcome To Order View Form Dashboard");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }
}
