package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddAuthorController {
    public TextField fldFirstname, fldLastname, fldUni;
    public DatePicker dateDOB;
    public ChoiceBox<String> choiceGender;

    private Author author = null;

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
    private void setFalse(TextField field){
        field.getStyleClass().add("poljeNijeIspravno");
    }
    private int getAge(LocalDate date){
        int age = LocalDate.now().getYear() - date.getYear();
        if(date.getMonthValue() > LocalDate.now().getMonthValue()){
            return age;
        }else if(date.getMonthValue() == LocalDate.now().getMonthValue()){
            if(date.getDayOfMonth() > LocalDate.now().getDayOfMonth()){
                return age;
            }
        }return age + 1;
    }

    public AddAuthorController() {
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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
    public void actionClose(ActionEvent actionEvent){
        if(((Button)actionEvent.getSource()).getText().equals("Cancel")){
            author = null;
        }else {
            String s = "poljeIspravno";
            if (styleTest(fldFirstname, s) && styleTest(fldLastname, s) && styleTest(fldUni, s) && dateDOB.getValue() != null && choiceGender.getSelectionModel().getSelectedItem() != null) {
                Gender g = choiceGender.getSelectionModel().getSelectedItem().equals("Male") ? Gender.MALE : Gender.FEMALE;
                int age = getAge(dateDOB.getValue());
                author = new Author(fldFirstname.getText(), fldLastname.getText(), age, g, fldUni.getText());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter valid information (password must be at least 8 characters long)");
                alert.show();
            }
        }Node node = (Node) actionEvent.getSource();
        Stage st = (Stage) node.getScene().getWindow();
        st.close();
    }
}
