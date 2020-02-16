package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UserProfileController {
    public Button editBtn, backBtn, closeBtn, okBtn, cancelBtn;
    public TextField fldFirstname, fldLastname, fldAge, fldUsername, fldMail;
    public PasswordField fldPassword;
    public ChoiceBox<Gender> choiceGender;
    public Slider slideAge;

    private User user;
    private ScientificDAO instance;

    public UserProfileController(User user, ScientificDAO instance) {
        this.user = user;
        this.instance = instance;
    }
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
    private void setTrue(TextField field){
        field.getStyleClass().add("poljeIspravno");
    }
    private void setEditable(boolean editable){
        fldFirstname.setEditable(editable);
        fldLastname.setEditable(editable);
        slideAge.setDisable(!editable);
        choiceGender.setDisable(!editable);
        fldUsername.setEditable(editable);
        fldMail.setEditable(editable);
        fldPassword.setEditable(editable);
    }
    private void setVisible(boolean visible){
        editBtn.setVisible(visible);
        backBtn.setVisible(visible);
        closeBtn.setVisible(visible);
        okBtn.setVisible(!visible);
        cancelBtn.setVisible(!visible);
    }

    @FXML
    public void initialize(){
        setTrue(fldFirstname);
        setTrue(fldLastname);
        setTrue(fldMail);
        setTrue(fldUsername);
        setTrue(fldPassword);
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
            setEditable(true);
            setVisible(false);
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

        okBtn.setOnAction(actionEvent -> {
            String s = "poljeIspravno";
            if(styleTest(fldFirstname, s) && styleTest(fldLastname, s) && styleTest(fldMail, s) && styleTest(fldPassword, s) && styleTest(fldUsername, s) && choiceGender.getSelectionModel().getSelectedItem() != null){
                user.setFirstname(fldFirstname.getText());
                user.setLastname(fldLastname.getText());
                user.setAge(Integer.parseInt(fldAge.getText()));
                user.setEmail(fldMail.getText());
                user.setGender(choiceGender.getSelectionModel().getSelectedItem());
                user.setUsername(fldUsername.getText());
                user.setPassword(fldPassword.getText());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("All fields must be viable");
                alert.show();
            }
            setVisible(true);
            setEditable(false);
        });
        cancelBtn.setOnAction(actionEvent -> {
            setVisible(true);
            setEditable(false);
        });
        closeBtn.setOnAction(actionEvent -> {
            instance.updateUser(user);
            Node node = (Node) actionEvent.getSource();
            Stage st = (Stage) node.getScene().getWindow();
            st.close();
        });
        backBtn.setOnAction(actionEvent -> {
            instance.updateUser(user);
            Node node = (Node) actionEvent.getSource();
            Stage st = (Stage) node.getScene().getWindow();
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
            stage.show();
            st.close();
        });
    }
}
