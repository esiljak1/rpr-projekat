package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadDocumentController {
    private String fileName;

    public TextArea txtFile;
    public Label lblTitle;

    private String getTitle(String s){
        String [] arr = s.split(".");
        s = "";
        for(int i = 0; i < arr.length - 1; i++){
            s += arr[i];
            s += ".";
        }return s;
    }

    public ReadDocumentController(String fileName) {
        this.fileName = fileName;
    }
    @FXML
    public void initialize(){
        lblTitle.setText(getTitle(fileName));

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
}
