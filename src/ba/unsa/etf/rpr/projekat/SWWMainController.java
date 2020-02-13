package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class SWWMainController {
    public ImageView imgLogo;
    public SWWMainController() {
    }

    @FXML
    public void initialize(){
        imgLogo.setImage(new Image(new File("logo.png").toURI().toString(), 300, 250, false, false));
    }
}
