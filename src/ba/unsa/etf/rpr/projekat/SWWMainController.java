package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;

public class SWWMainController {
    public ImageView imgLogo, imgUser;
    public Label labelUser;
    public ChoiceBox<String> choiceCategory;

    private User currentUser;

    public SWWMainController(User u) {
        currentUser = u;
    }

    @FXML
    public void initialize(){
        imgLogo.setImage(new Image(new File("logo.png").toURI().toString(), 300, 250, false, false));
        if(currentUser == null){
            labelUser.setText("Currently searching as guest");
        }else {
            labelUser.setText("Logged in as " + currentUser.getUsername());
        }
        //razlika izmedju default slike i slike s giphy-a !!
        imgUser.setImage(new Image(new File("default.jpg").toURI().toString(), 150, 150, false, false));
        imgUser.setOnMouseClicked((handle) -> {
            //otvoriti user profil gdje se moze izmedju ostalog promijeniti slika
        });
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Name");
        arr.add("Author");
        arr.add("Keywords");
        ObservableList<String> temp = FXCollections.observableArrayList(arr);
        choiceCategory.setItems(temp);
        choiceCategory.setValue("Name");
    }
}
