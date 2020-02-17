package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class ReadDocumentController {
    private String fileName;
    private User user;

    public TextArea txtFile;
    public Label lblRate;
    public ChoiceBox<Integer> choiceGrade;

    private String getTitle(String s){
        String [] arr = s.split(".");
        s = "";
        for(int i = 0; i < arr.length - 1; i++){
            s += arr[i];
            s += ".";
        }return s;
    }

    public ReadDocumentController(String fileName, User user) {
        this.fileName = fileName;
        this.user = user;
    }
    @FXML
    public void initialize(){
        ObservableList<Integer> temp = FXCollections.observableArrayList();
        temp.add(1);
        temp.add(2);
        temp.add(3);
        temp.add(4);
        temp.add(5);
        choiceGrade.setItems(temp);
        if(user == null){
            choiceGrade.setDisable(true);
        }
        if(Locale.getDefault().equals(new Locale("bs", "BA"))) {
            lblRate.setText("Ocijenite rad");
        }else{
            lblRate.setText("Rate this");
        }

        try {
            Scanner s = new Scanner(new File("@/../Resources/files/" + fileName)).useDelimiter("\\s+");
            while(s.hasNext()){
                if(s.hasNextInt()){
                    txtFile.appendText(s.nextInt() + "");
                }else{
                    txtFile.appendText(s.nextLine() + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Integer getGrade(){
        return choiceGrade.getSelectionModel().getSelectedItem();
    }
}
