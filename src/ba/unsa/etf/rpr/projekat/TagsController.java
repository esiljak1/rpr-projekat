package ba.unsa.etf.rpr.projekat;

import javafx.scene.control.TextArea;

public class TagsController {
    public TextArea tagsTxt;

    public TagsController() {
    }

    public String getTags(){
        return tagsTxt.getText();
    }
}
