package controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.UILoader;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;


public class LoginFormController implements Initializable {
    public ImageView imgCashier;
    public Label lblRestart;
    public Label lblDate;
    public Label lblTime;
    public Label lblMenu;
    public Label lblDescription;
    public AnchorPane LoginContext;
    public ImageView imgAdmin;
    public AnchorPane AnchorePanLogin;


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
        loadDateAndTime();
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), LoginContext);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    //--------------Load Time Date-----------------------//
    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();

            String state = null;
            if (currentTime.getHour() >= 12) {
                state = "PM";
            } else {
                state = "AM";
            }
            lblTime.setText("" + String.format("%02d", currentTime.getHour()) + ":" + String.format("%02d", currentTime.getMinute()) + ":" + String.format("%02d", currentTime.getSecond()) + " " + state);

        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    //-------------------UI Animation--------------------------------//
    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgAdmin":
                    lblMenu.setText("Admin Form");
                    lblDescription.setText("Manage Cashier , Manage Items , System Reports and View Details.");
                    break;
                case "imgCashier":
                    lblMenu.setText("Cashier's Form");
                    lblDescription.setText("Manage Customers , Manage Order and View Details.");
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

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome To Sunrise Bakery System");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }

    //--------------------Login Controller--------------------------------//
    public void AdminNavigate(MouseEvent mouseEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(AnchorePanLogin, "AdminForm");
    }

    public void CashierNavigate(MouseEvent mouseEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(AnchorePanLogin, "CashierForm");
    }

    public void AboutOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.aboutOnAction("AboutForm");
    }

    //----------------------UI Controller------------------//
    public void BtnCloseOnAction(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void BtnRestoreDownOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint("");
        stage.setResizable(true);
    }

    public void BtnMinimizeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
