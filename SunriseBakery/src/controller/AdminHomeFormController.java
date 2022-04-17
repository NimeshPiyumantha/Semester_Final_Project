package controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminHomeFormController implements Initializable {
    public AnchorPane HomeAnchor;
    public Label lblMenu;
    public Label lblDescription;
    public ImageView imgCashier;
    public ImageView imgItems;
    public ImageView imgDetails;
    public ImageView imgReport;
    public Label lblItem;
    public Label lblCustomer;
    public Label lblOrders;
    public Label lblSoldItems;

    //--------------------------------UI Animation-------------------------------//

    static void AdminMousePlayEnterAnimation(MouseEvent mouseEvent, Label lblMenu, Label lblDescription) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgCashier":
                    lblMenu.setText("Manage Cashier ");
                    lblDescription.setText("Add Cashier Details , Update Cashier Details and Delete Cashier Details.");
                    break;
                case "imgItems":
                    lblMenu.setText("Manage Items");
                    lblDescription.setText("Add Items Details , Update Items Details and Delete Items Details.");
                    break;
                case "imgReport":
                    lblMenu.setText("System Report");
                    lblDescription.setText("Daily System Report , Monthly System Report and Annually System Report.");
                    break;
                case "imgDetails":
                    lblMenu.setText("View Details");
                    lblDescription.setText("View Cashier Details , View Item Details , View Customers Details and View Order Details.");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.GOLD);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        AdminMousePlayEnterAnimation(mouseEvent, lblMenu, lblDescription);
    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome To Admin Dashboard");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
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
        //--------------------------------Label----------------------//
        try {
            lblItem.setText(String.valueOf(new OrderCrudController().getItem()));
            lblCustomer.setText(String.valueOf(new OrderCrudController().getCustomer()));
            lblOrders.setText(String.valueOf(new OrderCrudController().getSumSales()));
            lblSoldItems.setText(String.valueOf(new OrderCrudController().getSumSoldItems()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
