package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class SignUpController {
    public ImageView imgAvatar;
    public TextField fldFirstname, fldLastname, fldMail, fldUsername;
    public PasswordField fldPassword;
    public ChoiceBox<String> choiceGender;
    public DatePicker dateDOB;

    private boolean nameTest(String s){
        for(int i = 0; i < s.length(); i++){
            if(!((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'))) return false;
        }return true;
    }

    public SignUpController() {
    }

    @FXML
    public void initialize(){
        fldFirstname.getStyleClass().add("poljeNijeIspravno");
        fldLastname.getStyleClass().add("poljeNijeIspravno");
        fldMail.getStyleClass().add("poljeNijeIspravno");
        fldUsername.getStyleClass().add("poljeNijeIspravno");
        fldPassword.getStyleClass().add("poljeNijeIspravno");
        ObservableList<String> temp = FXCollections.observableArrayList();
        temp.add("Male");
        temp.add("Female");
        choiceGender.setItems(temp);
        imgAvatar.setImage(new Image(new File("default.jpg").toURI().toString(), 100, 100, false, false));

        fldFirstname.textProperty().addListener((obs, oldVal, newVal) -> {
            if(!newVal.trim().isEmpty() && nameTest(newVal)){
                fldFirstname.getStyleClass().removeAll("poljeNijeIspravno");
                fldFirstname.getStyleClass().add("poljeIspravno");
            }else{
                fldFirstname.getStyleClass().removeAll("poljeIspravno");
                fldFirstname.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldLastname.textProperty().addListener((obs, oldVal, newVal) -> {
            if(!newVal.trim().isEmpty() && nameTest(newVal)){
                fldLastname.getStyleClass().removeAll("poljeNijeIspravno");
                fldLastname.getStyleClass().add("poljeIspravno");
            }else{
                fldLastname.getStyleClass().removeAll("poljeIspravno");
                fldLastname.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldMail.textProperty().addListener((observableValue, oldVal, newVal) -> {
            //testiranje ispravnosti maila
            //sad cemo staviti da je svaki mail ispravan dok ne odradimo ovu funkcionalnost
            if(!newVal.trim().isEmpty()) {
                fldMail.getStyleClass().removeAll("poljeNijeIspravno");
                fldMail.getStyleClass().add("poljeIspravno");
            }else{
                fldMail.getStyleClass().removeAll("poljeIspravno");
                fldMail.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldPassword.textProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal.trim().length() >= 8){
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            }else{
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fldUsername.textProperty().addListener((obs, oldVal, newVal) -> {
            //todo testiranje da li je username vec zauzet u bazi
            if(!newVal.trim().isEmpty()){
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            }else{
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    //todo mrežno programiranje, koristiti giphy za postavljanje slika :)
}
