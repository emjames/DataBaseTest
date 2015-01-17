package sample;
/*this is the controller for login.fxml
it should have to return to main.java home.fxml
it takes care of opening the login window and then showing the home window
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Scene scene;
    private Stage stage;
    private String usrName = "demo";
    private String usrPass = "demo";
    @FXML
    private TextField txtFieldUsername = new TextField();
    @FXML
    private PasswordField txtFieldPassword = new PasswordField();
    @FXML
    private Label lblDeniedLogin = new Label();
    private HomeController homeController;


    public LoginController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        fxmlLoader.setController(this);
        try{
            Parent parent = fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    //When the login button is pressed
    protected void handleSubmitButtonAction(){
        //System.out.println((usrName.equalsIgnoreCase(txtFieldUsername.getText().trim())) && (usrPass.equalsIgnoreCase(txtFieldPassword.getText().trim())));
        //if( txtFieldUsername.getText().equals(usrName) && txtFieldPassword.equals(usrPass)){
        if((usrName.equalsIgnoreCase(txtFieldUsername.getText().trim())) && (usrPass.equalsIgnoreCase(txtFieldPassword.getText().trim()))){
            homeController = new HomeController();
            homeController.redirectHome(stage);
        }
        else{
            lblDeniedLogin.setText("Not registered.");
            System.out.println("Not registered");
        }

    }

    public void launchLoginController(Stage stage){
        this.stage = stage;
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.hide();
        stage.show();
    }


}
