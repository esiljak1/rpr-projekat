package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class SignUpController {
    public ImageView imgAvatar;

    public SignUpController() {
    }
    @FXML

    public void initialize(){
        imgAvatar.setImage(new Image(new File("default.jpg").toURI().toString(), 100, 100, false, false));
    }

    //todo mrežno programiranje, koristiti giphy za postavljanje slika :)
}
