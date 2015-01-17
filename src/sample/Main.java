package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
      LoginController loginController = new LoginController();
        loginController.launchLoginController(primaryStage);

/*
        Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("TaggR");
        primaryStage.setScene(new Scene(login));
        primaryStage.show();


        Parent mainWin = FXMLLoader.load(getClass().getResource("home.fxml")) ;
        primaryStage.setScene(new Scene(mainWin));
        primaryStage.show();
*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
