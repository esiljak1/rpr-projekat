package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class SWWMainController {
    public ImageView imgLogo, imgUser;
    public Label labelUser;

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
    }
}
