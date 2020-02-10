package ba.unsa.etf.rpr.projekat;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    public MainWindowController() {
    }

    public void loginAction(ActionEvent actionEvent){
        LogScreenController ctrl = new LogScreenController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginScreen.fxml"));
        loader.setController(ctrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 300, 300));
        stage.setResizable(false);
        stage.show();
    }
}
