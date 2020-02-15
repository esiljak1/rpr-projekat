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
        //todo dodati funkcionalnost za ok ukoliko username i password nisu prazna polja, skoro pa gotovo!!

        imgLogo.setImage(new Image(new File("@/../Resources/images/logo.png").toURI().toString(), 300, 250, false, false));
        okBtn.setOnAction((actionEvent) -> {
            if(fldUsername == null || fldUsername.getText().trim().isEmpty() || fldPassword == null || fldPassword.getText().trim().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Please write your username and password in the correct fields!");
                alert.setTitle("Error");
                alert.show();
                return;
            }
            User user = null;
            try {
                user = instance.getUser(fldUsername.getText(), fldPassword.getText());
            } catch (IllegalUserException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid username or password");
                alert.setTitle("Error");
                alert.show();
                return;
            }
            SWWMainController ctrl = new SWWMainController(user, instance);
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
            Node node = (Node) actionEvent.getSource();
            Stage st = (Stage) node.getScene().getWindow();
            st.close();
            stage.show();

        });

        cancelBtn.setOnAction(actionEvent -> {
            Node node = (Node) actionEvent.getSource();
            Stage st = (Stage) node.getScene().getWindow();
            st.close();
        });

        signUpBtn.setOnAction(actionEvent -> {
            SignUpController ctrl = new SignUpController(instance);
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
