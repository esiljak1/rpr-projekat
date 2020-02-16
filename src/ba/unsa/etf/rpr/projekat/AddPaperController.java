package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPaperController {
    public Button addFileBtn;
    public Button tagsBtn;
    public Button addAuthorsBtn;
    public TextField fileTxt;
    public ListView<Author> listAuthors;
    public CheckBox checkAuthor;

    private File selectedFile = null;
    private User user = null;
    private ScientificWork paper = null;
    private ObservableList<Author> authors = FXCollections.observableArrayList();
    private String tags = null;

    public AddPaperController(User user) {
        this.user = user;
    }

    public ScientificWork getPaper() {
        return paper;
    }

    public void setPaper(ScientificWork paper) {
        this.paper = paper;
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
            ResourceBundle bundle = ResourceBundle.getBundle("translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Tags.fxml"), bundle);
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
            stage.setOnHiding(windowEvent -> {
                tags = ctrl.getTags();
            });
        });
        addAuthorsBtn.setOnAction(actionEvent -> {
            AddAuthorController ctrl = new AddAuthorController();
            ResourceBundle bundle = ResourceBundle.getBundle("translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddAuthor.fxml"), bundle);
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
                    authors.add(ctrl.getAuthor());
                    listAuthors.setItems(authors);
                }
            });
        });
        checkAuthor.selectedProperty().addListener((obs, oldVal, newVal) -> {
            Author author = new Author(user.getFirstname(), user.getLastname(), user.getAge(), user.getGender(), "");
            if(newVal){
                TextInputDialog dialog = new TextInputDialog();
                dialog.setHeaderText("Please enter your university");
                Optional<String> optional = dialog.showAndWait();
                if(optional != null && !optional.get().trim().isEmpty()){
                    author.setUniversity(optional.get());
                    authors.add(author);
                    listAuthors.setItems(authors);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("There has been a problem, please try again");
                    alert.show();
                }
            }else{
                authors.removeIf(author1 -> author1.equals(author));
                listAuthors.setItems(authors);
            }
        });
    }

    public void closeAction(ActionEvent actionEvent){
        if(((Button)actionEvent.getSource()).getText().equals("Cancel")){
            paper = null;
        }else{
            if(fileTxt != null && !fileTxt.getText().trim().isEmpty() && listAuthors.getItems().size() != 0 && tags != null){
                String [] arr = tags.split(",");
                try {
                    paper = new ScientificWork(authors, fileTxt.getText(), arr);
                } catch (IllegalUserException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Please fill in all of the fields including tags");
                alert.show();
                return;
            }
        }
        Node node = (Node) actionEvent.getSource();
        Stage st = (Stage) node.getScene().getWindow();
        st.close();
    }

}
