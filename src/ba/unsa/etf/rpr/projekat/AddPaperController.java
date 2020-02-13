package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddPaperController {
    public Button addFileBtn;

    public AddPaperController() {
    }

    @FXML
    public void initialize(){
        addFileBtn.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text files", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            //todo dodati selectedFile u files resurse
        });
    }

}
