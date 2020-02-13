package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class LogScreenController {
    public TextField fldUsername;
    public PasswordField fldPassword;
    public Button okBtn, cancelBtn, signUpBtn;
    public ImageView imgLogo;

    private ScientificDAO instance;

    public LogScreenController() {
    }

    @FXML
    public void initialize(){
        instance = ScientificDAO.getInstance();
        //instance.napuni();
        //todo dodati funkcionalnost za ok ukoliko username i password nisu prazna polja

        imgLogo.setImage(new Image(new File("logo.png").toURI().toString(), 300, 250, false, false));
        okBtn.setOnAction((actionEvent) -> {
            if(fldUsername == null || fldUsername.getText().trim().isEmpty() || fldPassword == null || fldPassword.getText().trim().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Please write your username and password in the correct fields!");
                alert.setTitle("Error");
                alert.show();
            }
            try {
                instance.getUser(fldUsername.getText(), fldPassword.getText());
            } catch (IllegalUserException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid username or password");
                alert.setTitle("Error");
                alert.show();
                return;
            }
            SWWMainController ctrl = new SWWMainController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SWWMain.fxml"));
            loader.setController(ctrl);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("SWW");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.show();

        });

        cancelBtn.setOnAction(actionEvent -> {
            Node node = (Node) actionEvent.getSource();
            Stage st = (Stage) node.getScene().getWindow();
            MainWindowController ctrl = new MainWindowController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
            loader.setController(ctrl);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            st.close();
            stage.show();
        });

        signUpBtn.setOnAction(actionEvent -> {
            SignUpController ctrl = new SignUpController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignUpScreen.fxml"));
            loader.setController(ctrl);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Sign up");
            stage.setScene(new Scene(root, 400, 400));
            stage.setResizable(false);
            stage.show();
        });
    }
}
