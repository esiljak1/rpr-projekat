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

    @FXML
    public void initialize(){
        okBtn.setVisible(false);
        cancelBtn.setVisible(false);
        ObservableList<Gender> temp = FXCollections.observableArrayList();
        temp.add(Gender.MALE);
        temp.add(Gender.FEMALE);
        choiceGender.setItems(temp);
        choiceGender.setValue(user.getGender());

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
    }
}
