package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TagsController {
    public TextArea tagsTxt;

    private String tags;

    public TagsController() {
    }

    public String getTags(){
        return tags;
    }

    public void closeAction(ActionEvent actionEvent){
        if(((Button) actionEvent.getSource()).getText().equals("Cancel")){
            tags = null;
        }else{
            tags = tagsTxt.getText();
        }
        Node node = (Node) actionEvent.getSource();
        Stage st = (Stage) node.getScene().getWindow();
        st.close();
    }
}
