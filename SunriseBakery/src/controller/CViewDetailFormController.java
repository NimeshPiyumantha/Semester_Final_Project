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

public class CViewDetailFormController {
    public AnchorPane CViewDetailFormContext;
    public AnchorPane ViewDetailButtonContext;
    public AnchorPane ViewDetailTableContext;
    public ImageView imgCustomer;
    public ImageView imgItem;
    public Label lblMenu;
    public Label lblDescription;

    //-------------------------------UI Loader---------------------------------//
    public void CustomerDetailOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ViewDetailTableContext,"CustomerDetailForm");
    }

    public void ItemDetailOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ViewDetailTableContext,"ItemDetailForm");
    }

    //-------------------------------UI Animation--------------------------------//
    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgCustomer":
                    lblMenu.setText("Customer Detail");
                    lblDescription.setText("You can View Customer ID , Customer Name , NIC , Contact Number , Address , City , Province ,  Postal Code and Date & Time.");
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
