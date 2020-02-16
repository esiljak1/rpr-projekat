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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SWWMainController {
    public ImageView imgLogo, imgUser;
    public Label labelUser;
    public ChoiceBox<String> choiceCategory;
    public Button btnAdd, searchBtn;
    public TextField fldSearch;
    public GridPane gridSWWMain;

    public MenuItem itemPrint, itemProfile, itemAbout, itemInstructions;
    public RadioMenuItem itemEnglish, itemBosanski;

    private User currentUser;
    private ScientificDAO instance = null;

    private void checkAndAdd(List<Author> list, int paperId){
        for(Author p : list){
            int id = instance.existsAuthor(p.getFirstname(), p.getLastname(), p.getUniversity());
            if(id == -1){
                id = instance.addAuthor(p);
            }
            instance.addAuthorForScWork(id, paperId);
        }
    }
    private void profileLoader(){
        UserProfileController ctrl = new UserProfileController(currentUser, instance);
        ResourceBundle bundle = ResourceBundle.getBundle("translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserProfile.fxml"), bundle);
        loader.setController(ctrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("User");
        stage.setScene(new Scene(root, 600, 400));
        stage.setResizable(false);
        stage.show();
    }

    public SWWMainController(User u, ScientificDAO instance) {
        currentUser = u;
        this.instance = instance;
    }

    @FXML
    public void initialize(){
        imgLogo.setImage(new Image(new File("@/../Resources/images/logo.png").toURI().toString(), 300, 250, false, false));
        if(Locale.getDefault().equals(new Locale("bs", "BA"))){
            itemBosanski.setSelected(true);
            itemEnglish.setSelected(false);
        }else{
            itemEnglish.setSelected(true);
            itemBosanski.setSelected(false);
        }
        if(currentUser == null){
            labelUser.setText("Currently searching as guest");
        }else {
            labelUser.setText("Logged in as " + currentUser.getUsername());
        }
        //razlika izmedju default slike i slike s giphy-a !!
        imgUser.setImage(new Image(new File("@/../Resources/images/default.jpg").toURI().toString(), 150, 150, false, false));
        imgUser.setOnMouseClicked((handle) -> {
            //todo otvoriti user profil gdje se moze izmedju ostalog promijeniti slika
            if(currentUser == null) return;
            profileLoader();
            Node node = (Node) handle.getSource();
            Stage st = (Stage) node.getScene().getWindow();
            st.close();
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
                ResourceBundle bundle = ResourceBundle.getBundle("translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginScreen.fxml"), bundle);
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
                AddPaperController ctrl = new AddPaperController(currentUser);
                ResourceBundle bundle = ResourceBundle.getBundle("translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddPaper.fxml"), bundle);
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
                stage.setOnHiding(windowEvent -> {
                    ScientificWork tmp = ctrl.getPaper();
                    if(tmp != null){
                        checkAndAdd(tmp.getAuthors(), instance.addPaperWork(tmp));
                    }
                });
            }
        });
        searchBtn.setOnAction(actionEvent -> {
            if(fldSearch != null && !fldSearch.getText().trim().isEmpty()){
                //todo add search in database by name author and tags
                List<ScientificWork> list = new ArrayList<>();
                if(choiceCategory.getSelectionModel().getSelectedItem().equals("Name")){
                    list = instance.getWorksByName(fldSearch.getText());
                    System.out.println("Pretraga po imenu");
                }else if(choiceCategory.getSelectionModel().getSelectedItem().equals("Author")){
                    list = instance.getWorksByAuthor(fldSearch.getText());
                    System.out.println("Pretraga po autoru");
                }else{
                    list = instance.getWorksByTag(fldSearch.getText());
                    System.out.println("Pretraga po tagovima");
                }
                SearchPaperController ctrl = new SearchPaperController(list);
                ResourceBundle bundle = ResourceBundle.getBundle("translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SearchPaper.fxml"), bundle);
                loader.setController(ctrl);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Search results");
                stage.setScene(new Scene(root, 300, 300));
                stage.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter the keywords into the search field");
                alert.show();
            }
        });
        itemProfile.setOnAction(actionEvent -> {
            if(currentUser == null) return;
            Stage st = (Stage) gridSWWMain.getScene().getWindow();
            st.close();
            profileLoader();
        });
        itemBosanski.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal){
                itemEnglish.setSelected(false);
                Locale.setDefault(new Locale("bs", "BA"));
                Scene scene = gridSWWMain.getScene();
                SWWMainController kontroler = new SWWMainController(currentUser, instance);
                ResourceBundle bundle = ResourceBundle.getBundle("translation");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SWWMain.fxml"), bundle);
                loader.setController(kontroler);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scene.setRoot(root);
            }else if(oldVal && !itemEnglish.isSelected()){
                itemBosanski.setSelected(true);
            }
        });
        itemEnglish.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal){
                itemBosanski.setSelected(false);
                Locale.setDefault(new Locale("en", "US"));
                Scene scene = gridSWWMain.getScene();
                SWWMainController kontroler = new SWWMainController(currentUser, instance);
                ResourceBundle bundle = ResourceBundle.getBundle("translation");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SWWMain.fxml"), bundle);
                loader.setController(kontroler);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scene.setRoot(root);
            }else if(oldVal && !itemBosanski.isSelected()){
                itemEnglish.setSelected(true);
            }
        });
    }
    public void closeAction(ActionEvent actionEvent){
        System.exit(0);
    }
}
