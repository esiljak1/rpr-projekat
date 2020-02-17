package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;

public class SignUpController {
    public ImageView imgAvatar;
    public TextField fldFirstname, fldLastname, fldMail, fldUsername;
    public PasswordField fldPassword;
    public ChoiceBox<String> choiceGender;
    public DatePicker dateDOB;
    public Button okBtn, cancelBtn;

    private ScientificDAO instance;

    private boolean nameTest(String s){
        for(int i = 0; i < s.length(); i++){
            if(!((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'))) return false;
        }return true;
    }
    private boolean styleTest(TextField fld, String s){
        for(String st : fld.getStyleClass()){
            if(st.equals(s)) return true;
        }return false;
    }
    private int getAge(LocalDate date){
        int age = LocalDate.now().getYear() - date.getYear() - 1;
        if(date.getMonthValue() > LocalDate.now().getMonthValue()){
            return age;
        }else if(date.getMonthValue() == LocalDate.now().getMonthValue()){
            if(date.getDayOfMonth() > LocalDate.now().getDayOfMonth()){
                return age;
            }
        }return age + 1;
    }

    public SignUpController(ScientificDAO instance) {
        this.instance = instance;
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
        imgAvatar.setImage(new Image(new File("@/../Resources/images/default.jpg").toURI().toString(), 100, 100, false, false));

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
            if(!newVal.trim().isEmpty()){
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            }else{
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });
        okBtn.setOnAction(actionEvent -> {
            if(styleTest(fldFirstname, "poljeIspravno") && styleTest(fldLastname, "poljeIspravno") && styleTest(fldMail, "poljeIspravno") &&
                   styleTest(fldUsername, "poljeIspravno") && styleTest(fldPassword, "poljeIspravno") && choiceGender.getSelectionModel().getSelectedItem() != null
              && dateDOB.getValue() != null){
                if(instance.existsUser(fldUsername.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("There is already a user with that username, please pick a different one");
                    alert.setTitle("Error");
                    alert.show();
                    fldUsername.getStyleClass().removeAll("poljeIspravno");
                    fldUsername.getStyleClass().add("poljeNijeIspravno");
                }
                Gender g = choiceGender.getSelectionModel().getSelectedItem().equals("Male") ? Gender.MALE : Gender.FEMALE;
                int age = getAge(dateDOB.getValue());
                User user = new User(0, fldFirstname.getText(), fldLastname.getText(), age, g, fldUsername.getText(), fldPassword.getText(), fldMail.getText(), imgAvatar.getImage().getUrl());
                instance.addUser(user);
                Node node = (Node) actionEvent.getSource();
                Stage st = (Stage) node.getScene().getWindow();
                st.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("All fields must be valid");
                alert.show();
            }
        });
        cancelBtn.setOnAction(actionEvent -> {
            Node node = (Node) actionEvent.getSource();
            Stage st = (Stage) node.getScene().getWindow();
            st.close();
        });
    }

    //todo mrežno programiranje, koristiti giphy za postavljanje slika :)
}
