package controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.UILoader;

import java.io.IOException;

public class AboutFormController {
    public AnchorPane AboutContext;
    //--------------------------------UI close-------------------------------//
    public void CloseOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.CloseStage(AboutContext);
    }
}
