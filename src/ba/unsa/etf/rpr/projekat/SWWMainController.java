package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SWWMainController {
    public ImageView imgLogo, imgUser;
    public Label labelUser;
    public ChoiceBox<String> choiceCategory;
    public Button btnAdd, searchBtn;
    public TextField fldSearch;

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

    public SWWMainController(User u, ScientificDAO instance) {
        currentUser = u;
        this.instance = instance;
    }

    @FXML
    public void initialize(){
        imgLogo.setImage(new Image(new File("@/../Resources/images/logo.png").toURI().toString(), 300, 250, false, false));
        if(currentUser == null){
            labelUser.setText("Currently searching as guest");
        }else {
            labelUser.setText("Logged in as " + currentUser.getUsername());
        }
        //razlika izmedju default slike i slike s giphy-a !!
        imgUser.setImage(new Image(new File("@/../Resources/images/default.jpg").toURI().toString(), 150, 150, false, false));
        imgUser.setOnMouseClicked((handle) -> {
            //todo otvoriti user profil gdje se moze izmedju ostalog promijeniti slika
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginScreen.fxml"));
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddPaper.fxml"));
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
                if(choiceCategory.getSelectionModel().getSelectedItem().equals("Name")){
                    //pretraga radova po imenu
                    System.out.println("Pretraga po imenu");
                }else if(choiceCategory.getSelectionModel().getSelectedItem().equals("Author")){
                    //pretraga radova po autoru
                    System.out.println("Pretraga po autoru");
                }else{
                    //pretraga radova po tagovima
                    System.out.println("Pretraga po tagovima");
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter the keywords into the search field");
                alert.show();
            }
        });
    }
}
