package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashScreenFormController {
    public AnchorPane SplashScreenContext;
    public ProgressBar progressBar;
    public ProgressIndicator progressRange;
    public ImageView imageView;


    public void initialize() {
        new ShowSplashScreen().start();
    }

    class ShowSplashScreen extends Thread {
        public void run() {
            try {
                for (int i = 0; i <= 10; i++) {
                    double x = i * (0.1);
                    progressBar.setProgress(x);
                    progressRange.setProgress(x);

                    if (i * 10 == 100) {
                        progressRange.setVisible(true);
                        progressRange.setProgress(1);
                    }

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(SplashScreenFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    assert root != null;
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                    SplashScreenContext.getScene().getWindow().hide();
                });
            } catch (Exception ex) {
                Logger.getLogger(SplashScreenFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
