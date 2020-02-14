package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddAuthorController {
    public TextField fldFirstname, fldLastname, fldUni;
    public DatePicker dateDOB;
    public ChoiceBox<String> choiceGender;

    private boolean nameTest(String s){
        for(int i = 0; i < s.length(); i++){
            if(!((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'))) return false;
        }return true;
    }
    private void setFalse(TextField field){
        field.getStyleClass().add("poljeNijeIspravno");
    }

    public AddAuthorController() {
    }

    @FXML
    public void initialize(){
        setFalse(fldFirstname);
        setFalse(fldLastname);
        setFalse(fldUni);

        ObservableList<String> temp = FXCollections.observableArrayList();
        temp.add("Male");
        temp.add("Female");
        choiceGender.setItems(temp);

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
        fldUni.textProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null && !newVal.trim().isEmpty()){
                fldUni.getStyleClass().removeAll("poljeNijeIspravno");
                fldUni.getStyleClass().add("poljeIspravno");
            }else{
                fldUni.getStyleClass().removeAll("poljeIspravno");
                fldUni.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }
}
