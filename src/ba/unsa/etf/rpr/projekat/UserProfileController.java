package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserProfileController {
    public Button editBtn, backBtn, closeBtn, okBtn, cancelBtn;
    public TextField fldFirstname, fldLastname, fldAge, fldUsername, fldMail;
    public PasswordField fldPassword;
    public ChoiceBox<Gender> choiceGender;
    public Slider slideAge;

    private User user;

    public UserProfileController(User user) {
        this.user = user;
    }
    private boolean nameTest(String s){
        for(int i = 0; i < s.length(); i++){
            if(!((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'))) return false;
        }return true;
    }

    @FXML
    public void initialize(){
        okBtn.setVisible(false);
        cancelBtn.setVisible(false);
        ObservableList<Gender> temp = FXCollections.observableArrayList();
        temp.add(Gender.MALE);
        temp.add(Gender.FEMALE);
        choiceGender.setItems(temp);
        choiceGender.setValue(user.getGender());

        fldFirstname.setText(user.getFirstname());
        fldLastname.setText(user.getLastname());
        fldMail.setText(user.getEmail());
        fldUsername.setText(user.getUsername());
        fldPassword.setText(user.getPassword());
        fldAge.setText(user.getAge() + "");
        slideAge.setValue(user.getAge());

        editBtn.setOnAction(actionEvent -> {
            editBtn.setVisible(false);
            backBtn.setVisible(false);
            closeBtn.setVisible(false);
            okBtn.setVisible(true);
            cancelBtn.setVisible(true);
            fldFirstname.setEditable(true);
            fldLastname.setEditable(true);
            slideAge.setDisable(false);
            choiceGender.setDisable(false);
            fldUsername.setEditable(true);
            fldMail.setEditable(true);
            fldPassword.setEditable(true);
        });

        fldFirstname.textProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null && !newVal.trim().isEmpty() && nameTest(newVal)){
                fldFirstname.getStyleClass().removeAll("poljeNijeIspravno");
                fldFirstname.getStyleClass().add("poljeIspravno");
            }else{
                fldFirstname.getStyleClass().removeAll("poljeIspravno");
                fldFirstname.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldLastname.textProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null && !newVal.trim().isEmpty() && nameTest(newVal)){
                fldLastname.getStyleClass().removeAll("poljeNijeIspravno");
                fldLastname.getStyleClass().add("poljeIspravno");
            }else{
                fldLastname.getStyleClass().removeAll("poljeIspravno");
                fldLastname.getStyleClass().add("poljeNijeIspravno");
            }
        });

        slideAge.valueProperty().addListener((obs, oldVal, newVal) -> {
            slideAge.setValue(newVal.intValue());
            fldAge.setText(newVal.intValue() + "");
        });

        fldUsername.textProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null && !newVal.trim().isEmpty()){
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            }else{
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldMail.textProperty().addListener((obs, oldVal, newVal) -> {
            //dodati mreznu provjeru validacije maila???
            if(newVal != null && !newVal.trim().isEmpty()){
                fldMail.getStyleClass().removeAll("poljeNijeIspravno");
                fldMail.getStyleClass().add("poljeIspravno");
            }else{
                fldMail.getStyleClass().removeAll("poljeIspravno");
                fldMail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null && newVal.trim().length() >= 8){
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            }else{
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }
}
