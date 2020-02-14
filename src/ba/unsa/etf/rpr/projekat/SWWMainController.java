package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SWWMainController {
    public ImageView imgLogo, imgUser;
    public Label labelUser;
    public ChoiceBox<String> choiceCategory;
    public Button btnAdd;

    private User currentUser;
    private ScientificDAO instance = null;

    public SWWMainController(User u, ScientificDAO instance) {
        currentUser = u;
        this.instance = instance;
    }

    @FXML
    public void initialize(){
        imgLogo.setImage(new Image(new File("@/../Resources/images/logo.png").toURI().toString(), 300, 250, false, false));
        if(currentUser == null){
            labelUser.setText("Currently searching as guest");
        }else {
            labelUser.setText("Logged in as " + currentUser.getUsername());
        }
        //razlika izmedju default slike i slike s giphy-a !!
        imgUser.setImage(new Image(new File("@/../Resources/images/default.jpg").toURI().toString(), 150, 150, false, false));
        imgUser.setOnMouseClicked((handle) -> {
            //todo otvoriti user profil gdje se moze izmedju ostalog promijeniti slika
        });
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Name");
        arr.add("Author");
        arr.add("Keywords");
        ObservableList<String> temp = FXCollections.observableArrayList(arr);
        choiceCategory.setItems(temp);
        choiceCategory.setValue("Name");

        btnAdd.setOnAction(actionEvent -> {
            if(!(currentUser instanceof User)){
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
            }else{
                AddPaperController ctrl = new AddPaperController(instance);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddPaper.fxml"));
                loader.setController(ctrl);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("New paper");
                stage.setScene(new Scene(root, 400, 500));
                stage.setResizable(false);
                stage.show();
            }
        });
    }
}
