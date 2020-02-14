package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    public Button tagsBtn;

    public AddPaperController() {
    }

    @FXML
    public void initialize(){
        addFileBtn.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text files", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if(selectedFile == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You haven't chosen any file!");
                alert.show();
            }
            try {
                Files.copy(selectedFile.toPath(), Paths.get("@/../Resources/files/" + selectedFile.getName()), StandardCopyOption.COPY_ATTRIBUTES);
            } catch (IOException e) {
                System.out.println("There has been an error");
            }
        });
        tagsBtn.setOnAction(actionEvent -> {
            TagsController ctrl = new TagsController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Tags.fxml"));
            loader.setController(ctrl);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Add tags");
            stage.setScene(new Scene(root, 300, 400));
            stage.setResizable(false);
            stage.show();
        });
    }

}
