package util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class NotificationController {

    public static void SuccessfulTableNotification(String option,String option2) {
        Notifications notificationBuilder = Notifications.create()
                .title(option+" Successfully.!")
                .text("Your "+option2+" Details "+option+" is Successfully to the System.")
                .graphic(new ImageView(new Image("/assets/icons/done.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public static void UnSuccessfulTableNotification(String option,String option2) {
        Notifications notificationBuilder = Notifications.create()
                .title(option+" UnSuccessful.!")
                .text("Your "+option2+" Details "+option+" is Unsuccessfully to the System.")
                .graphic(new ImageView(new Image("/assets/icons/error.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }


    public static void LoginSuccessfulNotification(String option) {
        Notifications notificationBuilder = Notifications.create()
                .title( option+" Login Successful.!")
                .text("You have Successfully Login " +option+ " to the System.")
                .graphic(new ImageView(new Image("/assets/icons/done.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public static void LoginUnSuccessfulNotification(String option,int attempts) {
        Notifications notificationBuilder = Notifications.create()
                .title( option+" Login UnSuccessful.!")
                .text( option+" Not Login, You can use  Five  trys.\n This is "+attempts+". Try Again.!")
                .graphic(new ImageView(new Image("/assets/icons/error.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public static void EmptyDataNotification(String option) {
        Notifications notificationBuilder = Notifications.create()
                .title( "Empty DataSet !")
                .text("Your Select "+option+" ID are Empty DataSet .\n Try to another ID.")
                .graphic(new ImageView(new Image("/assets/icons/error.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

}
