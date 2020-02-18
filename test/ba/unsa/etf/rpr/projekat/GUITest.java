package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GUITest {
    ScientificDAO instance;

    @Start
    public void start(Stage stage) throws IOException {
        File dbFile = new File("scientific.db");
        dbFile.delete();

        instance = ScientificDAO.getInstance();
        instance.napuni();

        MainWindowController ctrl = new MainWindowController();
        ResourceBundle resource = ResourceBundle.getBundle("Languages");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/korisnici.fxml"), resource);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Korisnici");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();
    }
    @Test
    public void test1(FxRobot robot){
        assertTrue(true);
    }

}