package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by A on 2015/1/17.
 */
public class HomeController {
    private Scene scene;

    public HomeController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent parent = fxmlLoader.load();
            scene = new Scene(parent, 1050, 630);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void redirectHome(Stage stage){
        stage.setTitle("TaggR");
        stage.setScene(scene);
        stage.hide();
        stage.show();
    }
}
