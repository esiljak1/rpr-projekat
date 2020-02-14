package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    public Button addAuthorsBtn;
    public TextField fileTxt;
    public ListView<Author> listAuthors;

    private File selectedFile = null;
    private ScientificDAO instance = null;
    private ObservableList<Author> authors = FXCollections.observableArrayList();

    public AddPaperController(ScientificDAO instance) {
        this.instance = instance;
    }

    @FXML
    public void initialize(){
        addFileBtn.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text files", "*.txt"));
            selectedFile = fileChooser.showOpenDialog(new Stage());
            if(selectedFile == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You haven't chosen any file!");
                alert.show();
                return;
            }
            try {
                Files.copy(selectedFile.toPath(), Paths.get("@/../Resources/files/" + selectedFile.getName()), StandardCopyOption.COPY_ATTRIBUTES);
            } catch (IOException e) {
                System.out.println("There has been an error");
                fileTxt.setText("");
            }fileTxt.setText(selectedFile.getName());
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
        addAuthorsBtn.setOnAction(actionEvent -> {
            AddAuthorController ctrl = new AddAuthorController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddAuthor.fxml"));
            loader.setController(ctrl);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Add author");
            stage.setScene(new Scene(root, 300, 400));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding(windowEvent -> {
                if(ctrl.getAuthor() != null){
                    if(!instance.existsAuthor(ctrl.getAuthor().getFirstname(), ctrl.getAuthor().getLastname(), ctrl.getAuthor().getUniversity())){
                        instance.addAuthor(ctrl.getAuthor()); // ne moze ovako, u bazu se treba tek na kraju dodati inace ako pokupim sve iz baze pokupit cu bukvalno sve iz baze
                    }authors.addAll(instance.getAuthors());
                    listAuthors.setItems(authors);
                }
            });
        });
    }

}
