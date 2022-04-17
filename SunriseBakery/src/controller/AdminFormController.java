package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.NotificationController;
import util.UILoader;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminFormController implements Initializable {
    public AnchorPane AdminContext;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Label lblHide;
    public JFXButton btnLogin;
    public ImageView imgLock;
    int attempts = 0;


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
        imgLock.setVisible(false);
    }

    public void LoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        UILoader.LoginOnAction(AdminContext, "AdminDashBoardForm");
        NotificationController.LoginSuccessfulNotification("Admin");

        //--------------------------------UI Login Admin form in Database-------------------------------//
      /*  Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query = "SELECT * FROM Login";
        assert con != null;
        PreparedStatement stm = con.prepareStatement(query);

        String UserName = txtUserName.getText();
        String Password = txtPassword.getText();

        ResultSet rst = stm.executeQuery(query);
        if (rst.next()) {
            attempts++;
            if (attempts <= 3) {
                if (UserName.equals(rst.getString
                        (1)) && Password.equals(rst.getString(2))) {
                    UILoader.LoginOnAction(AdminContext, "AdminDashBoardForm");
                    NotificationController.LoginSuccessfulNotification("Admin");
                } else {
                    //try again
                    NotificationController.LoginUnSuccessfulNotification("Admin", attempts);
                }
            } else {
                txtUserName.setEditable(false);
                txtPassword.setEditable(false);
                imgLock.setVisible(true);
                NotificationController.EmptyDataNotification("Account is Temporarily Disabled or You Did not Sign in Correctly.");
            }
        }*/
    }

    //------Show Password-----
    public void showPasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("assets/icons/show.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtPassword.setPromptText(txtPassword.getText());
        txtPassword.setText("");
        txtPassword.setDisable(true);
        txtPassword.requestFocus();
    }

    //------Hide Password-----
    public void hidePasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("assets/icons/hide.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtPassword.setText(txtPassword.getPromptText());
        txtPassword.setPromptText("");
        txtPassword.setDisable(false);
    }
}
