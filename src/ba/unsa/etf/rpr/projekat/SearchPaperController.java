package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class SearchPaperController {
    private ObservableList<ScientificWork> list = FXCollections.observableArrayList();
    private User user = null;

    public TableView<ScientificWork> tableScWorks;
    public TableColumn<ScientificWork, String> columnName;
    public TableColumn<ScientificWork, List<Author>> columnAuthors;
    public TableColumn<ScientificWork, String> columnTags;
    public TableColumn<ScientificWork, Double> columnRating;

    public SearchPaperController(List<ScientificWork> list, User user) {
        this.user = user;
        this.list.setAll(list);
    }
    @FXML
    public void initialize(){
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        columnTags.setCellValueFactory(new PropertyValueFactory<>("tags"));
        columnRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tableScWorks.setItems(list);

        tableScWorks.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            ReadDocumentController ctrl = new ReadDocumentController(newVal.getName(), user);
            ResourceBundle bundle = ResourceBundle.getBundle("translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReadDocument.fxml"), bundle);
            loader.setController(ctrl);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle(newVal.getName());
            stage.setScene(new Scene(root, 400, 600));
            stage.show();
            stage.setOnHiding(windowEvent -> {
                if(ctrl.getGrade() != null){
                    ScientificDAO.getInstance().addGrade(newVal, user, ctrl.getGrade());
                    newVal.setRating(ScientificDAO.getInstance().getGrade(newVal));
                    tableScWorks.refresh();
                }
            });
        });
    }
}
