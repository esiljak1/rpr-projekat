package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
            try {
                Files.copy(selectedFile.toPath(), Paths.get("@/../Resources/files/" + selectedFile.getName()), StandardCopyOption.COPY_ATTRIBUTES);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //todo dodati selectedFile u files resurse
        });
    }

}
