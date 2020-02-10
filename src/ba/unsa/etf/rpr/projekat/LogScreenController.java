package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogScreenController {
    public TextField fldUsername;
    public PasswordField fldPassword;
    public Button okBtn, cancelBtn, signUpBtn;

    public LogScreenController() {
    }

    @FXML
    public void initialize(){
        //todo dodati funkcionalnost za ok ukoliko username i password nisu prazna polja
        okBtn.setOnAction((actionEvent) -> {
            if(fldUsername == null || fldUsername.getText().trim().isEmpty() || fldPassword == null || fldPassword.getText().trim().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Please write your username and password in the correct fields!");
                alert.setTitle("Error");
                alert.show();
            }
        });

        cancelBtn.setOnAction((actionEvent -> {
            Node node = (Node) actionEvent.getSource();
            Stage st = (Stage) node.getScene().getWindow();
            st.close();
        }));
    }
}
